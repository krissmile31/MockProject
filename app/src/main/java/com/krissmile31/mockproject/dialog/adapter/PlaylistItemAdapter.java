package com.krissmile31.mockproject.dialog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.interfaces.OnPlaylistItemClickListener;
import com.krissmile31.mockproject.models.Playlist;

import java.util.List;

public class PlaylistItemAdapter extends RecyclerView.Adapter<PlaylistItemAdapter.MyViewHolder> {
    private List<Playlist> mPlaylists;
    private OnPlaylistItemClickListener mListener;

    public PlaylistItemAdapter(List<Playlist> playlists) {
        mPlaylists = playlists;
    }

    public PlaylistItemAdapter(List<Playlist> playlists, OnPlaylistItemClickListener listener) {
        mPlaylists = playlists;
        mListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.playlist_dialog_item, parent,
                false);
        return new MyViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(mPlaylists.get(position));
    }

    @Override
    public int getItemCount() {
        return mPlaylists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ImageView mThumbnailItem;
        private TextView mTvNameItem, mTvUserItem;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            mContext = context;

            mThumbnailItem = itemView.findViewById(R.id.thumbnail_playlist_item);
            mTvNameItem = itemView.findViewById(R.id.tv_playlist_item_name);
            mTvUserItem = itemView.findViewById(R.id.tv_playlist_item_user);
        }

        public void bind(Playlist playlist) {
            mThumbnailItem.setImageResource(R.drawable.muzic);
            mTvNameItem.setText(playlist.getPlaylistName());
            mTvUserItem.setText("Rain");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (mListener != null && position != RecyclerView.NO_POSITION)
                        mListener.OnItemClick(mPlaylists.get(position));
                }
            });
        }
    }
}
