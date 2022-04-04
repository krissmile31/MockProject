package com.example.mockproject.adapters.tab;

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

public class MyPlaylistsAdapter extends RecyclerView.Adapter<MyPlaylistsAdapter.AlbumHolder> {
    private List<Album> albumList;

    public MyPlaylistsAdapter(List<Album> albumList) {
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.my_playlists_item, parent, false);
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

    public class AlbumHolder extends RecyclerView.ViewHolder {
        private Context context;
        private ImageView img_my_playlists;
        private TextView tv_singer_playlists;

        public AlbumHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            img_my_playlists = itemView.findViewById(R.id.img_my_playlists);
            tv_singer_playlists = itemView.findViewById(R.id.tv_singer_playlists);

        }

        public void bind(Album album) {
            img_my_playlists.setImageResource(album.getThumbnail());
            tv_singer_playlists.setText(album.getSinger());
        }
    }
}
