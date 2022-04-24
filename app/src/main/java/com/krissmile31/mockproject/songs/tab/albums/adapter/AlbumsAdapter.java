package com.krissmile31.mockproject.songs.tab.albums.adapter;

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
import com.krissmile31.mockproject.interfaces.OnItemClickListener;
import com.krissmile31.mockproject.model.Album;

import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {
    private List<Album> albumList;
    private OnItemClickListener listener;

    public AlbumsAdapter(List<Album> albumList) {
        this.albumList = albumList;
    }

    public AlbumsAdapter(List<Album> albumList, OnItemClickListener listener) {
        this.albumList = albumList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.albums_item, parent, false);
        return new MyViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(albumList.get(position));
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private ImageView thumbnail_albums;
        private TextView text_albums, tv_singer_albums, quantity_songs_albums;
        private ImageView popup_menu_albums;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            thumbnail_albums = itemView.findViewById(R.id.thumbnail_albums);
            text_albums = itemView.findViewById(R.id.text_albums);
            tv_singer_albums = itemView.findViewById(R.id.tv_singer_albums);
            quantity_songs_albums = itemView.findViewById(R.id.quantity_songs_albums);
            popup_menu_albums = itemView.findViewById(R.id.popup_menu_albums);

        }

        public void bind(Album album) {
            thumbnail_albums.setImageResource(album.getThumbnail());
            text_albums.setText(album.getAlbumName());
            tv_singer_albums.setText(album.getSinger());
            quantity_songs_albums.setText(String.valueOf(album.getQuantity_songs()));

            // pop up menu
            popup_menu_albums.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(itemView.getContext(), itemView);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                    popupMenu.show();
                }
            });

            thumbnail_albums.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(albumList.get(position));

//                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fa, new AlbumDetailsFragment()).addToBackStack(null).commit();
                }
            });
        }
    }

//    public interface OnDetailsClickListener {
//        void onDetailClick(Album album);
//    }
}
