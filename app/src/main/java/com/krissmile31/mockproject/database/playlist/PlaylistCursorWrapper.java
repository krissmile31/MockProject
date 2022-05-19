package com.krissmile31.mockproject.database.playlist;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.krissmile31.mockproject.database.DbSchema;
import com.krissmile31.mockproject.models.Playlist;

import java.util.ArrayList;
import java.util.List;

public class PlaylistCursorWrapper extends CursorWrapper {
    public PlaylistCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Playlist getPlaylist() {
        long id = getLong(getColumnIndex("id"));
        String name = getString(getColumnIndex(DbSchema.PlaylistsTable.Cols.NAME));

        Playlist playlist = new Playlist(name);

        return playlist;
    }

    public List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();

        moveToFirst();
        while (!isAfterLast()) {
            Playlist playlist = getPlaylist();
            playlists.add(playlist);

            moveToNext();
        }

        return playlists;
    }
}
