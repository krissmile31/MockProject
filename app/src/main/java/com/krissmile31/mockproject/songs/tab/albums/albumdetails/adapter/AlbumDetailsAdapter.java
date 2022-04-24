package com.krissmile31.mockproject.songs.tab.albums.albumdetails.adapter;

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

public class AlbumDetailsAdapter extends RecyclerView.Adapter<AlbumDetailsAdapter.MyViewHolder> {
    private List<Album> albumList;

    public AlbumDetailsAdapter(List<Album> albumList) {
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public AlbumDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.album_details_item, parent, false);
        return new MyViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumDetailsAdapter.MyViewHolder holder, int position) {
        holder.bind(albumList.get(position));
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private TextView song_album_details, time_album_details;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            song_album_details = itemView.findViewById(R.id.song_album_details);
            time_album_details = itemView.findViewById(R.id.time_album_details);
        }

        public void bind(Album album) {
            song_album_details.setText(album.getSong());
//            time_album_details.setText(album.getTime());
        }
    }
}
