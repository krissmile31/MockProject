package com.krissmile31.mockproject.models;

import java.io.Serializable;

public class Artist implements Serializable {
    private long artistId;
    private String artistName;
    private String thumbnailArtist;
    private int noAlbumsArtist;
    private int noSongsArtist;

    public Artist(long artistId, String artistName, String thumbnail, int noAlbums, int noSongsArtist) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.thumbnailArtist = thumbnail;
        this.noAlbumsArtist = noAlbums;
        this.noSongsArtist = noSongsArtist;
    }

    public Artist(long artistId, String artistName, String thumbnailArtist) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.thumbnailArtist = thumbnailArtist;
    }

    public long getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getThumbnailArtist() {
        return thumbnailArtist;
    }

    public int getNoAlbumsArtist() {
        return noAlbumsArtist;
    }

    public int getNoSongsArtist() {
        return noSongsArtist;
    }
}
