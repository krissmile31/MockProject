package com.krissmile31.mockproject.songs.tab.genres.adapter;

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
import com.krissmile31.mockproject.models.Genre;

import java.util.List;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.MyViewHolder> {
    private List<Genre> mGenreList;

    public GenresAdapter(List<Genre> songList) {
        mGenreList = songList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.genres_item,
                parent, false);
        return new MyViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(mGenreList.get(position));
    }

    @Override
    public int getItemCount() {
        return mGenreList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ImageView mThumbnailGenres;
        private TextView mTvGenres, mNoSongsGenres;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            mContext = context;

            mThumbnailGenres = itemView.findViewById(R.id.thumbnail_genres);
            mTvGenres = itemView.findViewById(R.id.text_genres);
            mNoSongsGenres = itemView.findViewById(R.id.quantity_songs_genres);

        }

        public void bind(Genre genre) {
            getThumbnail(genre.getThumbnailGenre(), mThumbnailGenres);
            mTvGenres.setText(genre.getGenreName());
            mNoSongsGenres.setText(String.valueOf(genre.getNoSongsGenre()) + " Songs");
        }
    }
}
