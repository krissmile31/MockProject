package com.krissmile31.mockproject.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.model.Album;

import java.util.List;

public class RecentlyPlayedAdapter extends RecyclerView.Adapter<RecentlyPlayedAdapter.MyViewHolder> {
    private List<Album> albumList;

    public RecentlyPlayedAdapter(List<Album> albumList) {
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.recently_played_item, parent, false);
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
        private TextView tv_song_played, tv_singer_played;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            tv_song_played = itemView.findViewById(R.id.tv_song_played);
            tv_singer_played = itemView.findViewById(R.id.tv_singer_played);
        }

        public void bind(Album album) {
            tv_song_played.setText(album.getSong());
            tv_singer_played.setText(album.getSinger());
        }
    }
}
