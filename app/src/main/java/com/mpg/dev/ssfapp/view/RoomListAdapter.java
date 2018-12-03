package com.mpg.dev.ssfapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpg.dev.avfapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.RoomViewHolder> {

    private List<String> mRoomNames;
    private Context mContext;

    public RoomListAdapter(Context context, List<String> roomNames){
        mRoomNames = roomNames;
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
        holder.setRoomName(mRoomNames.get(position));
    }

    @Override
    public int getItemCount() {
        return mRoomNames.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.room_card_title)
        TextView mRoomNameLabel;
        @BindView(R.id.room_card_bg)
        ImageView mRoomBackgroundImage;

        private Context mContext;

        public RoomViewHolder(View itemView, Context context) {
            super(itemView);
            mContext = context;
            ButterKnife.bind(this, itemView);
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

        public void setRoomName(String roomName){
            mRoomNameLabel.setText(roomName);
        }

    }
}
