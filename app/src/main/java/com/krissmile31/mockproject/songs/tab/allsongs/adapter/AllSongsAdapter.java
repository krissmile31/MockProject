package com.krissmile31.mockproject.songs.tab.allsongs.adapter;


import static com.krissmile31.mockproject.utils.SongUtils.*;
import static com.krissmile31.mockproject.utils.Constants.*;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.interfaces.OnSongClickListener;
import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.services.PlaySongService;

import java.util.List;

public class AllSongsAdapter extends RecyclerView.Adapter<AllSongsAdapter.MyViewHolder> {
    private List<Song> mSongList;
    private OnSongClickListener mListener;

    public AllSongsAdapter(List<Song> songList) {
        mSongList = songList;
    }

    public AllSongsAdapter(List<Song> songList, OnSongClickListener listener) {
        mSongList = songList;
        mListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.all_songs_item,
                parent, false);
        return new MyViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(mSongList.get(position));
    }

    @Override
    public int getItemCount() {
        return mSongList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ImageView mThumbnailSong;
        private TextView mTvSong;
        private TextView mTvSinger;
        private ImageView mBtnPlaySong;
        private PlaySongService mPlaySongService;
        private boolean mIsConnected;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            mContext = context;

            mThumbnailSong = itemView.findViewById(R.id.img_all_songs);
            mTvSong = itemView.findViewById(R.id.tv_all_songs);
            mTvSinger = itemView.findViewById(R.id.tv_all_singer);
            mBtnPlaySong = itemView.findViewById(R.id.play_song);
        }

        public void bind(Song song) {
            getThumbnail(song.getImage(), mThumbnailSong);
            mTvSong.setText(song.getSong());
            mTvSinger.setText(song.getSinger());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (mListener != null && position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(mSongList.get(position));
                    }

                    mBtnPlaySong.setImageResource(R.drawable.ic_pause_gradie);

                    Intent intent = new Intent(mContext, PlaySongService.class);
                    intent.putExtra(SONG_DETAIL, song);

                    // started
                    mContext.startService(intent);

                    // bound service
                    mContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
                    ContextCompat.startForegroundService(mContext, intent);
                }
            });
            mBtnPlaySong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setIconPlaying(mBtnPlaySong, R.drawable.ic_played, R.drawable.ic_pause_gradie);
                }
            });

//            itemView.findViewById(R.id.tv_all_songs).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mBtnPlaySong.setImageResource(R.drawable.ic_played);
//
//                    if (mIsConnected) {
//                        mContext.unbindService(serviceConnection);
//                        mIsConnected = false;
//                    }
//
//                    mContext.stopService(new Intent(mContext, PlaySongService.class));
//                }
//            });
        }

        public ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                PlaySongService.MySongBinder mySongBinder = (PlaySongService.MySongBinder) iBinder;
                mPlaySongService = mySongBinder.getPlaySongService();
                mIsConnected = true;

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mIsConnected = false;
            }
        };

    }
}
