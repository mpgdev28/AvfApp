package com.mpg.dev.ssfapp;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mpg.dev.avfapp.R;
import com.mpg.dev.ssfapp.data.DataManager;
import com.mpg.dev.ssfapp.data.DeviceInfo;
import com.mpg.dev.ssfapp.data.RoomInfo;
import com.mpg.dev.ssfapp.rest.SsfHomeRequest;
import com.mpg.dev.ssfapp.rest.SsfHomeResponse;
import com.mpg.dev.ssfapp.rest.SsfRequestManager;
import com.mpg.dev.ssfapp.view.RoomListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SsfCompanionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String LOG_TAG = SsfCompanionActivity.class.getName();

    @BindView(R.id.room_list)
    RecyclerView mRoomListRecView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private List<RoomInfo> mRoomList;
    private RoomListAdapter mRoomListAdapter;
    private CompositeDisposable disposableList;
    private Gson gson;
    private DataManager mDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssf_companion);
        ButterKnife.bind(this);

        gson = new GsonBuilder().setLenient().create();

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        disposableList = new CompositeDisposable();

        //check preferences and db for data

        //get rooms
        getRooms();
    }

    /**
     * TODO - call SSF Rest service to get available rooms
     */
    private void getRooms() {

        SsfHomeRequest homeRequest = new SsfHomeRequest();
        homeRequest.request = "GetRooms";

        Flowable<SsfHomeResponse> flowable = SsfRequestManager.instance().getRooms(gson.toJson(homeRequest, SsfHomeRequest.class));
        disposableList.add(flowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(ssfHomeResponse -> {

            mRoomList = ssfHomeResponse.getResults().getRooms();

            mRoomListAdapter = new RoomListAdapter(this, mRoomList);
            mRoomListRecView.setLayoutManager(new LinearLayoutManager(this));
            mRoomListRecView.setAdapter(mRoomListAdapter);

            for (RoomInfo roomInfo : mRoomList){
                getDevices(roomInfo.getId());
            }
            Log.d(LOG_TAG, "Getting SSF Rooms");
        }));
    }

    private void getDevices(String roomId){

        SsfHomeRequest homeRequest = new SsfHomeRequest();
        homeRequest.request = "GetDevices";
        homeRequest.roomId = roomId;

        Flowable<SsfHomeResponse> flowable2 = SsfRequestManager.instance().getDevices(gson.toJson(homeRequest, SsfHomeRequest.class));
        disposableList.add(flowable2.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(ssfHomeResponse -> {
            Log.d(LOG_TAG, "GetDevices : add to device map");

            List<DeviceInfo> devices = ssfHomeResponse.getResults().getDevices();
            if(devices != null) {
                DataManager.instance(this).setDevicesForRoom(roomId, devices);
            }
        }));
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ssf_companion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
