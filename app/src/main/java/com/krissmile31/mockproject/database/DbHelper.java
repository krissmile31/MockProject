package com.krissmile31.mockproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
    private Context mContext;
    public static final String DATABASE_RECENT_SONG = "recentSongs.db";
    public static final String DATABASE_PLAYLIST = "playlist.db";
    public static final String DATABASE_SONG_PLAYLIST = "song_playlist.db";

    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context, String database) {
        super(context, database, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // recent songs
        db.execSQL("CREATE TABLE " + DbSchema.RecentSongsTable.NAME + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbSchema.RecentSongsTable.Cols.THUMBNAIL + " TEXT, " +
                DbSchema.RecentSongsTable.Cols.NAME + " TEXT, " +
                DbSchema.RecentSongsTable.Cols.SINGER + " TEXT"  + ")");

        // playlists
        db.execSQL("CREATE TABLE " + DbSchema.PlaylistsTable.NAME + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbSchema.PlaylistsTable.Cols.NAME + " TEXT"  + ")");

        // songs in playlists
        db.execSQL("CREATE TABLE " + DbSchema.SongPlaylistTable.NAME + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbSchema.SongPlaylistTable.Cols.THUMBNAIL + " TEXT, " +
                DbSchema.SongPlaylistTable.Cols.SONG + " TEXT, " +
                DbSchema.SongPlaylistTable.Cols.SINGER + " TEXT"  + ")");

        // other tables here
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("My Songs", "My Songs: upgrading DB; dropping/recreating tables.");
        db.execSQL("DROP TABLE IF EXISTS " + DbSchema.RecentSongsTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DbSchema.PlaylistsTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DbSchema.SongPlaylistTable.NAME);

        // other tables here

        onCreate(db);
    }
}
