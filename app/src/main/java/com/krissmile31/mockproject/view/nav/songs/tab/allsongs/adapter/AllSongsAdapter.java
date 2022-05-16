package com.krissmile31.mockproject.view.nav.songs.tab.allsongs.adapter;

import static com.krissmile31.mockproject.utils.Constants.SONG_DETAIL;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.interfaces.OnSongClickListener;
import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.services.PlayService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AllSongsAdapter extends RecyclerView.Adapter<AllSongsAdapter.MyViewHolder> implements Filterable {
    private List<Song> mSongList;
    private List<Song> mSongFilter;
    private OnSongClickListener mListener;

    public AllSongsAdapter(List<Song> songList) {
        mSongList = songList;
    }

    public AllSongsAdapter(List<Song> songList, OnSongClickListener listener) {
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
        holder.bind(mSongList.get(position));
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
//                    sEmptySearch.setVisibility(View.GONE);
                }
                else {
                    List<Song> searchList = new ArrayList<>();
                    for (Song song: mSongFilter){
                        if (song.getSongName().toLowerCase().contains(filter.toLowerCase())) {
                            searchList.add(song);
//                            sEmptySearch.setVisibility(View.GONE);
                        }
                        else {
//                            sEmptySearch.setVisibility(View.VISIBLE);
//                            emptyResult.setText(filter);
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
//                if (mSongList.isEmpty()) {
//                    sEmptySearch.setVisibility(View.VISIBLE);
//                }
//                else {
//                    sEmptySearch.setVisibility(View.GONE);
//                }

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

        public void bind(Song song) {
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
                    int position = getAdapterPosition();
                    if (mListener != null && position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(mSongList.get(position));
                    }

//                    mBtnPlaySong.setImageResource(R.drawable.ic_pause_gradie);

//                    serviceUtils.startMusicPlayerService(mContext);

//                    Intent intent = new Intent(mContext, PlayService.class);
//                    intent.putExtra(SONG_DETAIL, song);
//
//                    // started
//                    mContext.startService(intent);

                    // bound service
//                    mContext.bindService(intent, boundService.serviceConnection, Context.BIND_AUTO_CREATE);
                   // ContextCompat.startForegroundService(mContext, intent);
                }
            });
//            sBtnPlaySong.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    setIconPlaying(sBtnPlaySong, R.drawable.ic_played, R.drawable.ic_pause_gradie);
////                    setIconStatusAll();
//                }
//            });

//            itemView.findViewById(R.id.tv_all_songs).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mBtnPlaySong.setImageResource(R.drawable.ic_played);
//
//                    if (mIsConnected) {
//                        mContext.unbindService(serviceConnection);
//                        mIsConnected = false;
//                    }
//
//                    mContext.stopService(new Intent(mContext, PlaySongService.class));
//                }
//            });
        }

    }
}
