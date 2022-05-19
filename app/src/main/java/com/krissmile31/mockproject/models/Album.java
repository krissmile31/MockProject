package com.krissmile31.mockproject.models;

import java.io.Serializable;
import java.util.List;

public class Album implements Serializable {
    private long albumId;
    private String thumbnailAlbum;
    private String albumName;
    private String singerName;
    private int numSongsAlbum;
    private List<Song> songList;

    public Album(long id, String thumbnail, String albumName, String singerName, int numSongsAlbum) {
        this.albumId = id;
        this.thumbnailAlbum = thumbnail;
        this.albumName = albumName;
        this.singerName = singerName;
        this.numSongsAlbum = numSongsAlbum;
    }

    public long getAlbumId() {
        return albumId;
    }

    public String getThumbnailAlbum() {
        return thumbnailAlbum;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getSingerName() {
        return singerName;
    }

    public int getNumSongsAlbum() {
        return numSongsAlbum;
    }
}
