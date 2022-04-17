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

public class TopSongsAdapter extends RecyclerView.Adapter<TopSongsAdapter.MyViewHolder> {
    private List<Album> albumList;

    public TopSongsAdapter(List<Album> albumList) {
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.top_songs_item, parent, false);
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
        private ImageView play_top_songs, more_top_songs;
        private TextView tv_name_top_songs, time_top_songs;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            play_top_songs = itemView.findViewById(R.id.play_top_songs);
            more_top_songs = itemView.findViewById(R.id.more_top_songs);
            tv_name_top_songs = itemView.findViewById(R.id.tv_name_top_songs);
            time_top_songs = itemView.findViewById(R.id.time_top_songs);
        }

        public void bind(Album album) {
            tv_name_top_songs.setText(album.getSong());
        }
    }
}
