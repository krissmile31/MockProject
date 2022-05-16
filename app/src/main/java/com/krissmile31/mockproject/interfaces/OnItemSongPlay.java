package com.krissmile31.mockproject.interfaces;

import com.krissmile31.mockproject.models.Song;

import java.util.List;

public interface OnItemSongPlay {
    void onSongPlay(int position);
    void updateSongList(List<Song> songList);
}
