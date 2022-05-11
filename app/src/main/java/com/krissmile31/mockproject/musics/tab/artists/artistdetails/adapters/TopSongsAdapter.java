package com.krissmile31.mockproject.musics.tab.artists.artistdetails.adapters;

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

public class TopSongsAdapter extends RecyclerView.Adapter<TopSongsAdapter.MyViewHolder> {
    private List<Song> mSongList;

    public TopSongsAdapter(List<Song> songList) {
        mSongList = songList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.top_songs_item,
                parent, false);
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
        private ImageView mBtnPlaySong, mMoreTopSongs;
        private TextView mNameTopSong, mTimeTopSong;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            mContext = context;

            mBtnPlaySong = itemView.findViewById(R.id.play_top_songs);
            mMoreTopSongs = itemView.findViewById(R.id.more_top_songs);
            mNameTopSong = itemView.findViewById(R.id.tv_name_top_songs);
            mTimeTopSong = itemView.findViewById(R.id.time_top_songs);
        }

        public void bind(Song song) {
            mNameTopSong.setText(song.getSong());
        }
    }
}
