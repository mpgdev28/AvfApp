package com.mpg.dev.ssfapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AvDisplayActivity extends AppCompatActivity {

    private final String LOG_TAG = AvDisplayActivity.class.getName();

    @BindView(R.id.avdisplay_container)
    ConstraintLayout mParentView;
    @BindView(R.id.avdisplay_poweroff_button)
    ImageButton mPowerOffButton;
    @BindView(R.id.avdisplay_poweron_button)
    ImageButton mPowerOnButton;

    private String mRoomId;
    private Gson gson;
    private CompositeDisposable disposableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avdisplay_controls);
        ButterKnife.bind(this);

        gson = new GsonBuilder().setLenient().create();

        mRoomId = getIntent().getStringExtra("RoomId");
        disposableList = new CompositeDisposable();
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

                if (view == mPowerOnButton) {
                    mPowerOnButton.setVisibility(View.INVISIBLE);
                    mPowerOffButton.setVisibility(View.VISIBLE);
                } else if (view == mPowerOffButton) {
                    mPowerOnButton.setVisibility(View.VISIBLE);
                    mPowerOffButton.setVisibility(View.INVISIBLE);
                } else {

                }

                SsfHomeRequest request = SsfHomeRequest.createInstance("Execute", mRoomId, DeviceInfo.DeviceType.AvDisplay.toString(), command, null);
                Flowable<SsfHomeResponse> requestFlowable = SsfRequestManager.instance().executeCommand(gson.toJson(request, SsfHomeRequest.class));
                disposableList.add(requestFlowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(ssfHomeResponse -> {
                   Log.d(LOG_TAG, "Response from Command request");
                }));
            });
        }
    }
}
