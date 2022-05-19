package com.krissmile31.mockproject.database.recentsong;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.krissmile31.mockproject.database.DbSchema;
import com.krissmile31.mockproject.models.Song;

import java.util.ArrayList;
import java.util.List;

public class SongCursorWrapper extends CursorWrapper {
    public SongCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Song getSong() {
        long id = getLong(getColumnIndex("id"));
        String thumbnail = getString(getColumnIndex(DbSchema.RecentSongsTable.Cols.THUMBNAIL));
        String name = getString(getColumnIndex(DbSchema.RecentSongsTable.Cols.NAME));
        String singer = getString(getColumnIndex(DbSchema.RecentSongsTable.Cols.SINGER));

        Song song = new Song(id, thumbnail, name, singer);

        return song;
    }

    public List<Song> getSongs() {
        List<Song> songs = new ArrayList<>();

        moveToFirst();
        while (!isAfterLast()) {
            Song song = getSong();
            songs.add(song);

            moveToNext();
        }

        return songs;
    }
}
