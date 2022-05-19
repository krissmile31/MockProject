package com.krissmile31.mockproject.nav.home.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.database.recentsong.SongManager;
import com.krissmile31.mockproject.models.Song;

import java.util.List;

public class RecentlyPlayedAdapter extends RecyclerView.Adapter<RecentlyPlayedAdapter.MyViewHolder> {
    private List<Song> mSongList;

    public RecentlyPlayedAdapter(List<Song> songList) {
        mSongList = songList;
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
        holder.bind(mSongList.get(position));
    }

    @Override
    public int getItemCount() {
        return mSongList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private TextView mTvSongPlayed, mTvSingerPlayed;
        private ImageView mFavourite;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            mContext = context;

            mTvSongPlayed = itemView.findViewById(R.id.tv_song_played);
            mTvSingerPlayed = itemView.findViewById(R.id.tv_singer_played);
            mFavourite = itemView.findViewById(R.id.favourites);
        }

        public void bind(Song song) {
            mTvSongPlayed.setText(String.valueOf(song.getSongName()));
            mTvSingerPlayed.setText(song.getSinger());

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new AlertDialog.Builder(mContext)
                            .setTitle("Are you sure to delete all Songs here?")
                            .setNegativeButton("No", null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SongManager songManager = SongManager.getInstance(mContext);
                                    songManager.clear();
                                    notifyDataSetChanged();
                                }
                            })
                            .show();
                    return true;
                }
            });
        }
    }
}
