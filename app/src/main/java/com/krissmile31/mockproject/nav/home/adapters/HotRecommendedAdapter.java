package com.krissmile31.mockproject.nav.home.adapters;

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

import java.util.List;

public class HotRecommendedAdapter extends RecyclerView.Adapter<HotRecommendedAdapter.MyViewHolder>{
    private List<Song> mSongList;

    public HotRecommendedAdapter(List<Song> songList) {
        mSongList = songList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.hot_recommended_item, parent, false);
        return new MyViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull HotRecommendedAdapter.MyViewHolder holder, int position) {
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
            this.mContext = context;

            mThumbnailSong = itemView.findViewById(R.id.thumbnail_hot);
            mTvSong = itemView.findViewById(R.id.tv_song_hot);
            mTvSinger = itemView.findViewById(R.id.tv_singer_hot);
        }

        public void bind(Song song) {
            mThumbnailSong.setImageResource(song.getThumbnails());
            mTvSong.setText(song.getSongName());
            mTvSinger.setText(song.getSinger());
        }
    }
}
