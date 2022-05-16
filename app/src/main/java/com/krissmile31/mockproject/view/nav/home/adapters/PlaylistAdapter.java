package com.krissmile31.mockproject.view.nav.home.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.database.playlist.PlaylistManager;
import com.krissmile31.mockproject.interfaces.OnPlaylistClickListener;
import com.krissmile31.mockproject.models.Playlist;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.MyViewHolder> {
    private List<Playlist> mPlayList;
//    private List<Song> mSongList;
    private OnPlaylistClickListener mListener;

    public PlaylistAdapter(List<Playlist> playList) {
        mPlayList = playList;
    }

    public PlaylistAdapter(List<Playlist> mPlayList, OnPlaylistClickListener mListener) {
        this.mPlayList = mPlayList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.playlist_item, parent, false);
        return new MyViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(mPlayList.get(position));
    }

    @Override
    public int getItemCount() {
        return mPlayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private Context mContext;
        private ImageView mThumbnailPlaylist;
        private TextView mTvSongPlaylist, mTvSingerPlaylist;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            mContext = context;

            mThumbnailPlaylist = itemView.findViewById(R.id.thumbnail_playlist);
            mTvSongPlaylist = itemView.findViewById(R.id.tv_song_playlist);
            mTvSingerPlaylist = itemView.findViewById(R.id.tv_singer_playlist);
        }

        public void bind(Playlist playlist) {
            mThumbnailPlaylist.setImageResource(R.drawable.muzic);
            mTvSongPlaylist.setText(playlist.getPlaylistName());
            mTvSingerPlaylist.setText("Rain");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (mListener != null && position != RecyclerView.NO_POSITION)
                        mListener.onItemClick(mPlayList.get(position));
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new AlertDialog.Builder(mContext)
                            .setTitle("Are you sure to delete all playlists here?")
                            .setNegativeButton("No", null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    Log.e("TAG", "onClick: " + );
//                                    SongUtils.sPlaylist.clear();
                                    PlaylistManager playlistManager = PlaylistManager.getInstance(mContext);
                                    playlistManager.clear();
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
