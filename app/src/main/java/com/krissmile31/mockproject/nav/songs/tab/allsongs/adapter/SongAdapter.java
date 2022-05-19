package com.krissmile31.mockproject.nav.songs.tab.allsongs.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.interfaces.OnSongClickListener;
import com.krissmile31.mockproject.models.Song;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.MyViewHolder> implements Filterable {
    private List<Song> mSongList;
    private List<Song> mSongFilter;
    private OnSongClickListener mListener;
    private int currentPosition = -1;

    public SongAdapter(List<Song> songList) {
        mSongList = songList;
    }

    public SongAdapter(List<Song> songList, OnSongClickListener listener) {
        mSongList = new ArrayList<>();
        mSongList = songList;
        mSongFilter = songList;
        mListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.all_songs_item,
                parent, false);
        return new MyViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(mSongList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mSongList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filter = constraint.toString();
                if (filter.isEmpty()) {
                    mSongList = mSongFilter;
                }
                else {
                    List<Song> searchList = new ArrayList<>();
                    for (Song song: mSongFilter){
                        if (song.getSongName().toLowerCase().contains(filter.toLowerCase())) {
                            searchList.add(song);
                        }
                    }
                    mSongList = searchList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mSongList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mSongList = (List<Song>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ImageView mThumbnailSong;
        private TextView mTvSong;
        private TextView mTvSinger;
        private ImageView mBtnPlaySong;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            mContext = context;

            mThumbnailSong = itemView.findViewById(R.id.img_all_songs);
            mTvSong = itemView.findViewById(R.id.tv_all_songs);
            mTvSinger = itemView.findViewById(R.id.tv_all_singer);
            mBtnPlaySong = itemView.findViewById(R.id.play_song);
        }

        public void bind(Song song, int position) {
            Picasso.get().load(song.getThumbnail())
                    .placeholder(R.drawable.ic_logo)
                    .error(R.drawable.ic_logo)
                    .fit()
                    .into(mThumbnailSong);
            mTvSong.setText(song.getSongName());
            mTvSinger.setText(song.getSinger());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int oldPosition = currentPosition;
                    currentPosition = position;
                    int pos = getAdapterPosition();
                    if (mListener != null && pos != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(mSongList.get(pos), mBtnPlaySong);
                    }
                    notifyItemChanged(oldPosition);
//                    notifyItemChanged(currentPosition);
                }
            });

            mBtnPlaySong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (mListener != null && pos != RecyclerView.NO_POSITION) {
                        mListener.onIconClick(mBtnPlaySong);
                    }
                }
            });

            if (position == currentPosition) {
                mBtnPlaySong.setImageResource(R.drawable.ic_pause_gradie);
            } else {
                mBtnPlaySong.setImageResource(R.drawable.ic_played);
            }
        }
    }
}
