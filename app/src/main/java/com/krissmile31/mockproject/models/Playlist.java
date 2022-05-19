package com.krissmile31.mockproject.models;

import java.io.Serializable;
import java.util.List;

public class Playlist implements Serializable {
    private long playlistId;
    private String playlistName;
    private String thumbnailPlaylist;
    private List<Song> songList;

    public Playlist() {
    }

    public Playlist(String playlistName) {
        this.playlistName = playlistName;
    }

    public Playlist(long playlistId, String playlistName, String thumbnailPlaylist) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.thumbnailPlaylist = thumbnailPlaylist;
    }

    public List<Song> getSongList() {
        return songList;
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

    public void setPlaylistId(long playlistId) {
        this.playlistId = playlistId;
    }

    public void setSongList(List<Song> songList, Song song) {
        songList.add(song);
    }
}
