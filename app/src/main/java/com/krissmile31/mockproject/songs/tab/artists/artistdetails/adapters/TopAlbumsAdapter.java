package com.krissmile31.mockproject.songs.tab.artists.artistdetails.adapters;

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

public class TopAlbumsAdapter extends RecyclerView.Adapter<TopAlbumsAdapter.MyViewHolder> {
    private List<Album> albumList;

    public TopAlbumsAdapter(List<Album> albumList) {
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.top_albums_item, parent, false);
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
        private ImageView thumbnail_top_albums;
        private TextView tv_name_top_albums, tv_year_top_albums;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            thumbnail_top_albums = itemView.findViewById(R.id.thumbnail_top_albums);
            tv_name_top_albums = itemView.findViewById(R.id.tv_name_top_albums);
            tv_year_top_albums = itemView.findViewById(R.id.tv_year_top_albums);

        }

        public void bind(Album album) {
            thumbnail_top_albums.setImageResource(album.getThumbnail());
            tv_name_top_albums.setText(album.getAlbumName());
            tv_year_top_albums.setText(String.valueOf(album.getYear()));
        }
    }
}
