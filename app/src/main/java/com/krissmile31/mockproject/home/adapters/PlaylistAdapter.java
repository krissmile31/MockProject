package com.krissmile31.mockproject.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.models.Song;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.MyViewHolder> {
    private List<Song> songList;

    public PlaylistAdapter(List<Song> songList) {
        this.songList = songList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.playlist_item, parent, false);
        return new MyViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(songList.get(position));
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private Context context;
        private ImageView thumbnail_playlist;
        private TextView tv_song_playlist, tv_singer_playlist;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            thumbnail_playlist = itemView.findViewById(R.id.thumbnail_playlist);
            tv_song_playlist = itemView.findViewById(R.id.tv_song_playlist);
            tv_singer_playlist = itemView.findViewById(R.id.tv_singer_playlist);
        }

        public void bind(Song song) {
            thumbnail_playlist.setImageResource(song.getThumbnail());
            tv_song_playlist.setText(song.getSong());
            tv_singer_playlist.setText(song.getSinger());
        }
    }
}
