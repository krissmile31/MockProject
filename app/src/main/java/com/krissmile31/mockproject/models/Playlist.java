package com.krissmile31.mockproject.models;

import java.io.Serializable;

public class Playlist implements Serializable {
    private long playlistId;
    private String playlistName;
    private String thumbnailPlaylist;
    private int noSongsPlaylist;

    public Playlist(long playlistId, String playlistName, String thumbnailPlaylist) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.thumbnailPlaylist = thumbnailPlaylist;
    }

    public Playlist(long playlistId, String thumbnailPlaylist) {
        this.playlistId = playlistId;
        this.thumbnailPlaylist = thumbnailPlaylist;
    }

    public Playlist(long playlistId, String playlistName, String thumbnailPlaylist, int noSongsPlaylist) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.thumbnailPlaylist = thumbnailPlaylist;
        this.noSongsPlaylist = noSongsPlaylist;
    }

    public long getPlaylistId() {
        return playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public String getThumbnailPlaylist() {
        return thumbnailPlaylist;
    }

    public int getNoSongsPlaylist() {
        return noSongsPlaylist;
    }
}
