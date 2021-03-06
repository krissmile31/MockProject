package com.krissmile31.mockproject.nav.songs.tab.artists.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.interfaces.OnArtistClickListener;
import com.krissmile31.mockproject.models.Artist;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.MyViewHolder> {
    private List<Artist> mArtistList;
    private OnArtistClickListener mListener;

    public ArtistAdapter(List<Artist> artistList) {
        mArtistList = artistList;
    }

    public ArtistAdapter(List<Artist> artistList, OnArtistClickListener listener) {
        mArtistList = artistList;
        mListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.artists_item,
                parent, false);
        return new MyViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(mArtistList.get(position));
    }

    @Override
    public int getItemCount() {
        return mArtistList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ImageView mThumbnailArtist;
        private TextView mSingerArtists, mNumSongsArtists, mNumAlbumsArtists;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.mContext = context;

            mThumbnailArtist = itemView.findViewById(R.id.thumbnail_artists);
            mSingerArtists = itemView.findViewById(R.id.tv_singer_artists);
            mNumAlbumsArtists = itemView.findViewById(R.id.quantity_albums_artists);
            mNumSongsArtists = itemView.findViewById(R.id.quantity_songs_artists);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Artist artist) {
            Picasso.get().load(artist.getThumbnailArtist())
                    .placeholder(R.drawable.ic_logo)
                    .error(R.drawable.ic_logo)
                    .fit()
                    .into(mThumbnailArtist);
            mSingerArtists.setText(artist.getArtistName());
            mNumSongsArtists.setText(artist.getNumSongsArtist() + " ");
            mNumAlbumsArtists.setText(artist.getNumAlbumsArtist() + " ");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (mListener != null && position != RecyclerView.NO_POSITION)
                        mListener.onItemClick(mArtistList.get(position));
                }
            });
        }
    }
}
