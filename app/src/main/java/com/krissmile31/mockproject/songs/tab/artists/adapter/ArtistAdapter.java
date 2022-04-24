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
import com.krissmile31.mockproject.interfaces.OnItemClickListener;
import com.krissmile31.mockproject.model.Album;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.MyViewHolder> {
    private List<Album> albumList;
    private OnItemClickListener listener;

    public ArtistAdapter(List<Album> albumList) {
        this.albumList = albumList;
    }

    public ArtistAdapter(List<Album> albumList, OnItemClickListener listener) {
        this.albumList = albumList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.artists_item, parent, false);
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
        private ImageView thumbnail_artists;
        private TextView tv_singer_artists, quantity_songs_artists, quantity_albums_artists;

        public MyViewHolder(@NonNull View itemView, Context context) {
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(albumList.get(position));
                }
            });
        }
    }
}
