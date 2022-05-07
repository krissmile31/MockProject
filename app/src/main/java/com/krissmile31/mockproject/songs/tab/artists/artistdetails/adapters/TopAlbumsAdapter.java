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
import com.krissmile31.mockproject.models.Song;

import java.util.List;

public class TopAlbumsAdapter extends RecyclerView.Adapter<TopAlbumsAdapter.MyViewHolder> {
    private List<Song> mSongList;

    public TopAlbumsAdapter(List<Song> songList) {
        mSongList = songList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.top_albums_item, parent, false);
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
        private ImageView mThumbnailAlbum;
        private TextView mNameAlbum, mYearAlbum;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            mContext = context;

            mThumbnailAlbum = itemView.findViewById(R.id.thumbnail_top_albums);
            mNameAlbum = itemView.findViewById(R.id.tv_name_top_albums);
            mYearAlbum = itemView.findViewById(R.id.tv_year_top_albums);

        }

        public void bind(Song song) {
            mThumbnailAlbum.setImageResource(song.getThumbnail());
            mNameAlbum.setText(song.getAlbumName());
            mYearAlbum.setText(String.valueOf(song.getYear()));
        }
    }
}
