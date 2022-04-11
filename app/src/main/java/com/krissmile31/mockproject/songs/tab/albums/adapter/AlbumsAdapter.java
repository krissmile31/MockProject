package com.krissmile31.mockproject.songs.tab.albums.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.models.Album;

import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumAdapter> {
    private List<Album> albumList;

    public AlbumsAdapter(List<Album> albumList) {
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public AlbumAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.albums_item, parent, false);
        return new AlbumAdapter(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumAdapter holder, int position) {
        holder.bind(albumList.get(position));
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class AlbumAdapter extends RecyclerView.ViewHolder {
        private Context context;
        private ImageView thumbnail_albums;
        private TextView text_albums, tv_singer_albums, quantity_songs_albums;

        public AlbumAdapter(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            thumbnail_albums = itemView.findViewById(R.id.thumbnail_albums);
            text_albums = itemView.findViewById(R.id.text_albums);
            tv_singer_albums = itemView.findViewById(R.id.tv_singer_albums);
            quantity_songs_albums = itemView.findViewById(R.id.quantity_songs_albums);

        }

        public void bind(Album album) {
            thumbnail_albums.setImageResource(album.getThumbnail());
            text_albums.setText(album.getAlbumName());
            tv_singer_albums.setText(album.getSinger());
            quantity_songs_albums.setText(String.valueOf(album.getQuantity_songs()));
        }
    }
}
