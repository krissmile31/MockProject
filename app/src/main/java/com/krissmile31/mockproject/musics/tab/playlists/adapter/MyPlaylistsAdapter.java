package com.krissmile31.mockproject.musics.tab.playlists.adapter;

import static com.krissmile31.mockproject.utils.SongUtils.*;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.models.Playlist;

import java.util.List;

public class MyPlaylistsAdapter extends RecyclerView.Adapter<MyPlaylistsAdapter.MyViewHolder> {
    private List<Playlist> mPlayList;

    public MyPlaylistsAdapter(List<Playlist> songList) {
        mPlayList = songList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.my_playlists_item,
                parent, false);
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ImageView mThumbnailPlaylists;
        private TextView mTvPlaylistName;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            mContext = context;

            mThumbnailPlaylists = itemView.findViewById(R.id.img_my_playlists);
            mTvPlaylistName = itemView.findViewById(R.id.tv_playlist_name);

        }

        public void bind(Playlist playlist) {
            getThumbnail(playlist.getThumbnailPlaylist(), mThumbnailPlaylists);
            mTvPlaylistName.setText(playlist.getPlaylistName());
        }
    }
}
