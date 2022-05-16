package com.krissmile31.mockproject.database.playlist.songplaylist;

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

public class SongPlaylistManager {
    // singleton
    private static SongPlaylistManager instance;

    private static final String INSERT_STMT =
            "INSERT INTO " 
                    + DbSchema.SongPlaylistTable.NAME
                    + "(thumbnail, song, singer) VALUES (?, ?, ?)";

//    private static final String UPDATE_STMT =
//            "UPDATE "
//                    + DbSchema.PlaylistsTable.NAME
//                    + " SET "
//                    + DbSchema.PlaylistsTable.Cols.QUANTITY + "= ? WHERE id = ?";
//
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public static SongPlaylistManager getInstance(Context context) {
        if (instance == null) {
            instance = new SongPlaylistManager(context);
        }

        return instance;
    }

    private SongPlaylistManager(Context context) {
        dbHelper = new DbHelper(context, DbHelper.DATABASE_SONG_PLAYLIST);
        db = dbHelper.getWritableDatabase();
    }


    public List<Song> all() {
        String sql = "SELECT * FROM "
                + DbSchema.SongPlaylistTable.NAME;
//                + " GROUP BY "
//                + DbSchema.SongPlaylistTable.Cols.SONG;

        Cursor cursor = db.rawQuery(sql, null);
        SongCursorWrapper cursorWrapper = new SongCursorWrapper(cursor);

        return cursorWrapper.getSongs();
    }

    /**
     * @modifies friend
     */
    public boolean add(Song song) {
        SQLiteStatement statement = db.compileStatement(INSERT_STMT);

        statement.bindString(3, song.getThumbnail());
        statement.bindString(1, song.getSongName());
        statement.bindString(2, song.getSinger());

        long id = statement.executeInsert();
        // a
        if (id > 0) {
            song.setId(id);
            return true;
        }

        return false;
    }

    public boolean update (Playlist Playlist) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        long result = db.update
                (DbSchema.SongPlaylistTable.NAME,
                        contentValues,
                        "id=?",
                        new String[]{Playlist.getPlaylistId() + ""});
        return result > 0;

    }

    public boolean delete(long id) {
        int result = db.delete(DbSchema.SongPlaylistTable.NAME, "id = ?",
                new String[]{ id+"" });

        return result > 0;
    }

    public void clear() {
        db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + DbSchema.SongPlaylistTable.NAME);
    }

    public class SongCursorWrapper extends CursorWrapper {
        public SongCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public Song getSong() {
            long id = getLong(getColumnIndex("id"));
            String thumbnail = getString(getColumnIndex(DbSchema.SongPlaylistTable.Cols.THUMBNAIL));
            String name = getString(getColumnIndex(DbSchema.SongPlaylistTable.Cols.SONG));
            String singer = getString(getColumnIndex(DbSchema.SongPlaylistTable.Cols.SINGER));

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


}