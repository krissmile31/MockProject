package com.krissmile31.mockproject.interfaces;

import android.widget.ImageView;

import com.krissmile31.mockproject.models.Song;

public interface OnSongClickListener {
    void onItemClick(Song song);
    void onIconClick(ImageView icon);

}
