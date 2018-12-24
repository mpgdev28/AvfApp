package com.mpg.dev.ssfapp;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mpg.dev.avfapp.R;
import com.mpg.dev.ssfapp.data.DataManager;
import com.mpg.dev.ssfapp.data.DeviceInfo;
import com.mpg.dev.ssfapp.rest.SsfHomeRequest;
import com.mpg.dev.ssfapp.rest.SsfHomeResponse;
import com.mpg.dev.ssfapp.rest.SsfRequestManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CableBoxActivity extends AppCompatActivity {
    private final String LOG_TAG = AvDisplayActivity.class.getName();

    @BindView(R.id.cable_container)
    ConstraintLayout mParentView;

    private String mRoomId;
    private Gson gson;
    private CompositeDisposable disposableList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cablebox_controls);
        ButterKnife.bind(this);
        gson = new GsonBuilder().setLenient().create();
        disposableList = new CompositeDisposable();
        mRoomId = getIntent().getStringExtra("RoomId");

        setupButtonActions();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposableList.dispose();
    }

    private void setupButtonActions() {
        for (int i = 0; i < mParentView.getChildCount(); i++) {
            View childView = mParentView.getChildAt(i);

            childView.setOnClickListener(view -> {

                String command = DataManager.instance(null).getCommandForButton(getResources().getResourceEntryName(view.getId()));
                Log.d(LOG_TAG, "Button Clicked - Command = " + command);

                SsfHomeRequest request = SsfHomeRequest.createInstance("Execute", mRoomId, DeviceInfo.DeviceType.CableBox.toString(), command, null);
                Flowable<SsfHomeResponse> requestFlowable = SsfRequestManager.instance().executeCommand(gson.toJson(request, SsfHomeRequest.class));
                disposableList.add(requestFlowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(ssfHomeResponse -> {
                    Log.d(LOG_TAG, "Response from Command request");
                }));
            });
        }
    }


}
