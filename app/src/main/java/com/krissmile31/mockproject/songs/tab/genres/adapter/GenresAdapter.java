package com.krissmile31.mockproject.songs.tab.genres.adapter;

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

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.MyViewHolder> {
    private List<Album> albumList;

    public GenresAdapter(List<Album> albumList) {
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.genres_item, parent, false);
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
        private ImageView thumbnail_genres;
        private TextView text_genres, quantity_songs_genres;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            thumbnail_genres = itemView.findViewById(R.id.thumbnail_genres);
            text_genres = itemView.findViewById(R.id.text_genres);
            quantity_songs_genres = itemView.findViewById(R.id.quantity_songs_genres);

        }

        public void bind(Album album) {
            thumbnail_genres.setImageResource(album.getThumbnail());
            text_genres.setText(album.getGenre());
            quantity_songs_genres.setText(String.valueOf(album.getQuantity_songs() + " Songs"));
        }
    }
}
