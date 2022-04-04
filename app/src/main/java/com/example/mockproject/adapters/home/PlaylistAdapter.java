package com.example.mockproject.adapters.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mockproject.R;
import com.example.mockproject.models.Album;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.AlbumHolder> {
    private List<Album> albumList;

    public PlaylistAdapter(List<Album> albumList) {
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.playlist_item, parent, false);
        return new AlbumHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {
        holder.bind(albumList.get(position));
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class AlbumHolder extends RecyclerView.ViewHolder{
        private Context context;
        private ImageView thumbnail_playlist;
        private TextView tv_song_playlist, tv_singer_playlist;

        public AlbumHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            thumbnail_playlist = itemView.findViewById(R.id.thumbnail_playlist);
            tv_song_playlist = itemView.findViewById(R.id.tv_song_playlist);
            tv_singer_playlist = itemView.findViewById(R.id.tv_singer_playlist);
        }

        public void bind(Album album) {
            thumbnail_playlist.setImageResource(album.getThumbnail());
            tv_song_playlist.setText(album.getSong());
            tv_singer_playlist.setText(album.getSinger());
        }
    }
}
