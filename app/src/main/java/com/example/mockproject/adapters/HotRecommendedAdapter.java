package com.example.mockproject.adapters;

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

public class HotRecommendedAdapter extends RecyclerView.Adapter<HotRecommendedAdapter.AlbumHolder>{
    private List<Album> albumList;

    public HotRecommendedAdapter(List<Album> albumList) {
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.hot_recommended_item, parent, false);
        return new AlbumHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull HotRecommendedAdapter.AlbumHolder holder, int position) {
        holder.bind(albumList.get(position));
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class AlbumHolder extends RecyclerView.ViewHolder {
        private Context context;
        private ImageView thumbnail_hot;
        private TextView tv_song_hot, tv_singer_hot;

        public AlbumHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            thumbnail_hot = itemView.findViewById(R.id.thumbnail_hot);
            tv_song_hot = itemView.findViewById(R.id.tv_song_hot);
            tv_singer_hot = itemView.findViewById(R.id.tv_singer_hot);
        }

        public void bind(Album album) {
            thumbnail_hot.setImageResource(album.getThumbnail());
            tv_song_hot.setText(album.getSong());
            tv_singer_hot.setText(album.getSinger());
        }
    }
}
