package com.example.mockproject.adapters.tab;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mockproject.R;
import com.example.mockproject.models.Album;
import com.example.mockproject.services.PlaySongService;

import java.io.Serializable;
import java.util.List;

public class AllSongsAdapter extends RecyclerView.Adapter<AllSongsAdapter.AlbumAdapter> {
    private List<Album> albumList;

    public AllSongsAdapter(List<Album> albumList) {
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public AlbumAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.all_songs_item, parent, false);
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
        private ImageView img_all_songs;
        private TextView tv_all_songs, tv_all_singer;

        public AlbumAdapter(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            img_all_songs = itemView.findViewById(R.id.img_all_songs);
            tv_all_songs = itemView.findViewById(R.id.tv_all_songs);
            tv_all_singer = itemView.findViewById(R.id.tv_all_singer);
        }

        public void bind(Album album) {
            img_all_songs.setImageResource(album.getThumbnail());
            tv_all_songs.setText(album.getSong());
            tv_all_singer.setText(album.getSinger());

            itemView.findViewById(R.id.play_song).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PlaySongService.class);
                    intent.putExtra("song_details", album);
                    context.startService(intent);
                }
            });

            itemView.findViewById(R.id.tv_all_songs).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.stopService(new Intent(context, PlaySongService.class));
                }
            });
        }
    }
}
