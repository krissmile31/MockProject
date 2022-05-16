package com.krissmile31.mockproject.models;

import java.io.Serializable;
import java.util.List;

public class Album implements Serializable {
    private long albumId;
    private String thumbnailAlbum;
    private String albumName;
    private String singerName;
    private int noSongsAlbum;
    private List<Song> songList;

    public Album(long id, String thumbnail, String albumName, String singerName, int noSongsAlbum) {
        this.albumId = id;
        this.thumbnailAlbum = thumbnail;
        this.albumName = albumName;
        this.singerName = singerName;
        this.noSongsAlbum = noSongsAlbum;
    }

    public Album(long albumId, String thumbnailAlbum, String albumName, String singerName, int noSongsAlbum, List<Song> songList) {
        this.albumId = albumId;
        this.thumbnailAlbum = thumbnailAlbum;
        this.albumName = albumName;
        this.singerName = singerName;
        this.noSongsAlbum = noSongsAlbum;
        this.songList = songList;
    }

    public Album(long albumId, String albumName, String singerName, int noSongsAlbum) {
        this.albumId = albumId;
        this.albumName = albumName;
        this.singerName = singerName;
        this.noSongsAlbum = noSongsAlbum;
    }

    public Album(long albumId, String thumbnailAlbum, String albumName, String singerName) {
        this.albumId = albumId;
        this.thumbnailAlbum = thumbnailAlbum;
        this.albumName = albumName;
        this.singerName = singerName;
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

    public int getNoSongsAlbum() {
        return noSongsAlbum;
    }
}
