package com.krissmile31.mockproject.view.playlists.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.models.Song;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SongListItemAdapter extends RecyclerView.Adapter<SongListItemAdapter.MyViewHolder> {
    private List<Song> mSongList;

    public SongListItemAdapter(List<Song> songList) {
        mSongList = songList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.playlist_detail_item, parent,
                false);
        return new MyViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(mSongList.get(position));
    }

    @Override
    public int getItemCount() {
        return mSongList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ImageView mThumbnailSong;
        private TextView mTvSong, mTvSinger;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            mContext = context;

            mThumbnailSong = itemView.findViewById(R.id.img_song_playlist);
            mTvSong = itemView.findViewById(R.id.song_playlist);
            mTvSinger = itemView.findViewById(R.id.singer_playlist);
        }

        public void bind(Song song) {
            Picasso.get().load(song.getThumbnail())
                    .placeholder(R.drawable.ic_logo)
                    .error(R.drawable.ic_logo)
                    .fit()
                    .into(mThumbnailSong);
            mTvSong.setText(song.getSongName());
            mTvSinger.setText(song.getSinger());
        }
    }
}
