package com.krissmile31.mockproject.songs.tab.artists.adapter;

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

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.AlbumAdapter> {
    private List<Album> albumList;

    public ArtistAdapter(List<Album> albumList) {
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public AlbumAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.artists_item, parent, false);
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
        private ImageView thumbnail_artists;
        private TextView tv_singer_artists, quantity_songs_artists, quantity_albums_artists;

        public AlbumAdapter(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            thumbnail_artists = itemView.findViewById(R.id.thumbnail_artists);
            tv_singer_artists = itemView.findViewById(R.id.tv_singer_artists);
            quantity_albums_artists = itemView.findViewById(R.id.quantity_albums_artists);
            quantity_songs_artists = itemView.findViewById(R.id.quantity_songs_artists);
        }

        public void bind(Album album) {
            thumbnail_artists.setImageResource(album.getThumbnail());
            tv_singer_artists.setText(album.getSinger());
            quantity_songs_artists.setText(String.valueOf(album.getQuantity_songs()));
            quantity_albums_artists.setText(String.valueOf(album.getQuantity_albums()));
        }
    }
}
