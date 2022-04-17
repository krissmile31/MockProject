package com.krissmile31.mockproject.songs.tab.allsongs.adapter;

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
import androidx.recyclerview.widget.RecyclerView;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.interfaces.OnItemClickListener;
import com.krissmile31.mockproject.models.Album;
import com.krissmile31.mockproject.services.PlaySongService;
import com.krissmile31.mockproject.songs.tab.allsongs.AllSongsFragment;

import java.util.List;

public class AllSongsAdapter extends RecyclerView.Adapter<AllSongsAdapter.MyViewHolder> {
    private List<Album> albumList;
    private OnItemClickListener listener;

    public AllSongsAdapter(List<Album> albumList) {
        this.albumList = albumList;
    }

    public AllSongsAdapter(List<Album> albumList, OnItemClickListener listener) {
        this.albumList = albumList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.all_songs_item, parent, false);
        return new MyViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(albumList.get(position));
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private ImageView img_all_songs;
        private TextView tv_all_songs, tv_all_singer;
        private ImageView play_song;
        private PlaySongService playSongService;
        private boolean isConnected;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            img_all_songs = itemView.findViewById(R.id.img_all_songs);
            tv_all_songs = itemView.findViewById(R.id.tv_all_songs);
            tv_all_singer = itemView.findViewById(R.id.tv_all_singer);
            play_song = itemView.findViewById(R.id.play_song);
        }

        public void bind(Album album) {
            img_all_songs.setImageResource(album.getThumbnail());
            tv_all_songs.setText(album.getSong());
            tv_all_singer.setText(album.getSinger());

            play_song.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    play_song.setImageResource(R.drawable.ic_pause_gradie);
                    AllSongsFragment.playSongBackground.setVisibility(View.VISIBLE);
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(albumList.get(position));
                    }

                    Intent intent = new Intent(context, PlaySongService.class);
                    intent.putExtra("song_details", album);

                    // started
                    context.startService(intent);

                    // bound service
                    context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

//                    ContextCompat.startForegroundService(context, intent);
                }
            });

            itemView.findViewById(R.id.tv_all_songs).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    play_song.setImageResource(R.drawable.ic_played);

                    if (isConnected) {
                        context.unbindService(serviceConnection);
                        isConnected = false;
                    }

                    context.stopService(new Intent(context, PlaySongService.class));
                }
            });
        }

        public ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                PlaySongService.MySongBinder mySongBinder = (PlaySongService.MySongBinder) iBinder;
                playSongService = mySongBinder.getPlaySongService();
                isConnected = true;

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                isConnected = false;
            }
        };

    }

//    public interface OnPlayClickListener {
//        void onClick (Album album);
//    }
}
