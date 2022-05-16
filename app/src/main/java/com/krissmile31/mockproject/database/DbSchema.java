package com.krissmile31.mockproject.database;

import com.krissmile31.mockproject.models.Song;

import java.util.List;

public class DbSchema {
    public final class RecentSongsTable {
        public static final String NAME = "recentSongs";

        public final class Cols {
            public static final String THUMBNAIL = "thumbnail";
            public static final String NAME = "name";
            public static final String SINGER = "singer";

        }
    }

    public final class PlaylistsTable {
        public static final String NAME = "playlists";

        public final class Cols {
//            public static final String THUMBNAIL = "thumbnail";
            public static final String NAME = "name";
//            public static final String SINGER = "singer";

        }
    }

    public final class SongPlaylistTable {
        public static final String NAME = "songPlaylist";

        public final class Cols {
            public static final String THUMBNAIL = "thumbnail";
            public static final String SONG = "song";
            public static final String SINGER = "singer";

        }
    }

    // other tables here
}
