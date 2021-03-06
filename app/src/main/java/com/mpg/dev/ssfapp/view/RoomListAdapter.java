package com.mpg.dev.ssfapp.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mpg.dev.avfapp.R;
import com.mpg.dev.ssfapp.AvDisplayActivity;
import com.mpg.dev.ssfapp.CableBoxActivity;
import com.mpg.dev.ssfapp.SsfCompanionActivity;
import com.mpg.dev.ssfapp.data.RoomInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.RoomViewHolder> {

    private List<RoomInfo> mRoomInfoList;
    private Context mContext;

    public RoomListAdapter(Context context, List<RoomInfo> roomNames){
        mRoomInfoList = roomNames;
        mContext = context;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new RoomViewHolder(LayoutInflater.from(mContext).inflate(R.layout.room_list_item, parent, false), mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        holder.getBackgroundForIndex(position);
        holder.setRoomName(mRoomInfoList.get(position).getName(), mRoomInfoList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return mRoomInfoList.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.room_card_title)
        TextView mRoomNameLabel;
        @BindView(R.id.room_card_bg)
        ImageView mRoomBackgroundImage;
        @BindView(R.id.room_card_toolbar)
        LinearLayout mRoomToolbar;
        @BindView(R.id.room_card_tv)
        ImageButton mRoomCardTv;
        @BindView(R.id.room_card_cable_box)
        ImageButton mRoomCardCable;

        private Context mContext;
        private String mRoomId;

        public RoomViewHolder(View itemView, Context context) {
            super(itemView);
            mContext = context;
            ButterKnife.bind(this, itemView);
            setupButtonActions();
        }

        private void setupButtonActions() {
            mRoomCardTv.setOnClickListener(view -> {
                Intent intent = new Intent(mContext, AvDisplayActivity.class);
                intent.putExtra("RoomId", mRoomId);
                mContext.startActivity(intent);
            });

            mRoomCardCable.setOnClickListener(view -> {
                Intent intent = new Intent(mContext, CableBoxActivity.class);
                intent.putExtra("RoomId", mRoomId);
                mContext.startActivity(intent);
            });
        }

        public void getBackgroundForIndex(int index){

            int roomNameImgResId = mContext.getResources().getIdentifier("room_" + (index+1), "string", mContext.getPackageName());
            int roomBgDrawableResId = mContext.getResources().getIdentifier(mContext.getResources().getString(roomNameImgResId), "drawable", mContext.getPackageName());
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), roomBgDrawableResId);

            int imageWidth = bitmap.getWidth();
            int imageHeight = bitmap.getHeight();
            Point screenDimen = new Point();
            ((WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(screenDimen); //this method should return the width of device screen.
            int newWidth = screenDimen.y;
            float scaleFactor = (float)newWidth/(float)imageWidth;
            int newHeight = (int)(imageHeight * scaleFactor);

            bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
            mRoomBackgroundImage.setImageBitmap(bitmap);
        }

        public void setRoomName(String roomName, String roomId){
            mRoomNameLabel.setText(roomName);
            mRoomId = roomId;
        }

    }
}
