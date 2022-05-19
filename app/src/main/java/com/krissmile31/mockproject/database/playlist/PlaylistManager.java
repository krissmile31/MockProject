package com.krissmile31.mockproject.database.playlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.krissmile31.mockproject.database.DbHelper;
import com.krissmile31.mockproject.database.DbSchema;
import com.krissmile31.mockproject.models.Playlist;
import com.krissmile31.mockproject.models.Song;

import java.util.ArrayList;
import java.util.List;

public class PlaylistManager {
    // singleton
    private static PlaylistManager instance;

    private static final String INSERT_STMT =
            "INSERT INTO " 
                    + DbSchema.PlaylistsTable.NAME
                    + "(name) VALUES (?)";

    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public static PlaylistManager getInstance(Context context) {
        if (instance == null) {
            instance = new PlaylistManager(context);
        }

        return instance;
    }

    private PlaylistManager(Context context) {
        dbHelper = new DbHelper(context, DbHelper.DATABASE_PLAYLIST);
        db = dbHelper.getWritableDatabase();
    }


    public List<Playlist> all() {
        String sql = "SELECT * FROM "
                + DbSchema.PlaylistsTable.NAME
                + " GROUP BY "
                + DbSchema.PlaylistsTable.Cols.NAME;

        Cursor cursor = db.rawQuery(sql, null);
        PlaylistCursorWrapper cursorWrapper = new PlaylistCursorWrapper(cursor);

        return cursorWrapper.getPlaylists();
    }

    /**
     * @modifies friend
     */
    public boolean add(Playlist playlist) {
        SQLiteStatement statement = db.compileStatement(INSERT_STMT);

        statement.bindString(1, playlist.getPlaylistName());

        long id = statement.executeInsert();
        // a
        if (id > 0) {
            playlist.setPlaylistId(id);
            return true;
        }

        return false;
    }

    public boolean update (Playlist Playlist) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put(DbSchema.PlaylistsTable.Cols.QUANTITY, Playlist.getNoSongsPlaylist());
        long result = db.update
                (DbSchema.PlaylistsTable.NAME,
                        contentValues,
                        "id=?",
                        new String[]{Playlist.getPlaylistId() + ""});
        return result > 0;

    }

    public boolean delete(long id) {
        int result = db.delete(DbSchema.PlaylistsTable.NAME, "id = ?", new String[]{ id+"" });

        return result > 0;
    }

    public void clear() {
        db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + DbSchema.PlaylistsTable.NAME);
    }
}