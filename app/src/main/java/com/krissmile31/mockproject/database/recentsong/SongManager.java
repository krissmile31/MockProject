package com.krissmile31.mockproject.database.recentsong;

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

public class SongManager {
    // singleton
    private static SongManager instance;

    private static final String INSERT_STMT =
            "INSERT INTO " 
                    + DbSchema.RecentSongsTable.NAME
                    + "(thumbnail, name, singer) VALUES (?, ?, ?)";

//    private static final String UPDATE_STMT =
//            "UPDATE "
//                    + DbSchema.PlaylistsTable.NAME
//                    + " SET "
//                    + DbSchema.PlaylistsTable.Cols.QUANTITY + "= ? WHERE id = ?";
//
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public static SongManager getInstance(Context context) {
        if (instance == null) {
            instance = new SongManager(context);
        }

        return instance;
    }

    private SongManager(Context context) {
        dbHelper = new DbHelper(context, DbHelper.DATABASE_RECENT_SONG);
        db = dbHelper.getWritableDatabase();
    }


    public List<Song> all() {
        String sql = "SELECT * FROM "
                + DbSchema.RecentSongsTable.NAME;
//                + " GROUP BY "
//                + DbSchema.SongsTable.Cols.SINGER;

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
//        statement.bindDouble(3, Playlist.getUnitPrice());
//        statement.bindLong(4, ((long) (Playlist.getNoSongsPlaylist())));

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
//        contentValues.put(DbSchema.PlaylistsTable.Cols.QUANTITY, Playlist.getNoSongsPlaylist());
        long result = db.update
                (DbSchema.RecentSongsTable.NAME,
                        contentValues,
                        "id=?",
                        new String[]{Playlist.getPlaylistId() + ""});
        return result > 0;

    }

    public boolean delete(long id) {
        int result = db.delete(DbSchema.RecentSongsTable.NAME, "id = ?", new String[]{ id+"" });

        return result > 0;
    }

    public void clear() {
        db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + DbSchema.RecentSongsTable.NAME);
    }

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


}