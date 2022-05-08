package com.krissmile31.mockproject.songs.tab.albums.adapter;

import static com.krissmile31.mockproject.utils.SongUtils.*;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.interfaces.OnAlbumClickListener;
import com.krissmile31.mockproject.models.Album;

import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {
    private List<Album> mAlbumList;
    private OnAlbumClickListener mListener;

    public AlbumsAdapter(List<Album> AlbumList) {
        mAlbumList = AlbumList;
    }

    public AlbumsAdapter(List<Album> AlbumList, OnAlbumClickListener listener) {
        mAlbumList = AlbumList;
        mListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.albums_item,
                parent, false);
        return new MyViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(mAlbumList.get(position));
    }

    @Override
    public int getItemCount() {
        return mAlbumList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ImageView mThumbnailAlbum;
        private TextView mTvAlbum, mSingerAlbum, mNoAlbumsAlbum;
        private ImageView mPopupMenuAlbums;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            mContext = context;

            mThumbnailAlbum = itemView.findViewById(R.id.thumbnail_albums);
            mTvAlbum = itemView.findViewById(R.id.text_albums);
            mSingerAlbum = itemView.findViewById(R.id.tv_singer_albums);
            mNoAlbumsAlbum = itemView.findViewById(R.id.quantity_songs_albums);
            mPopupMenuAlbums = itemView.findViewById(R.id.popup_menu_albums);

        }

        public void bind(Album album) {
            getThumbnail(album.getThumbnailAlbum(), mThumbnailAlbum);
            mTvAlbum.setText(album.getAlbumName());
            mSingerAlbum.setText(album.getSingerName());
            mNoAlbumsAlbum.setText(String.valueOf(album.getNoSongsAlbum()));

            // pop up menu
            mPopupMenuAlbums.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(itemView.getContext(), itemView);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                    popupMenu.show();
                }
            });

            mThumbnailAlbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (mListener != null && position != RecyclerView.NO_POSITION)
                        mListener.onItemClick(mAlbumList.get(position));
                }
            });
        }
    }
}
