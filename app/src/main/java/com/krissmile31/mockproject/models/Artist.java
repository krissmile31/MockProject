package com.krissmile31.mockproject.models;

import java.io.Serializable;

public class Artist implements Serializable {
    private long artistId;
    private String artistName;
    private String thumbnailArtist;
    private int numAlbumsArtist;
    private int numSongsArtist;

    public Artist(long artistId, String artistName, String thumbnail, int noAlbums, int numSongsArtist) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.thumbnailArtist = thumbnail;
        this.numAlbumsArtist = noAlbums;
        this.numSongsArtist = numSongsArtist;
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

    public int getNumAlbumsArtist() {
        return numAlbumsArtist;
    }

    public int getNumSongsArtist() {
        return numSongsArtist;
    }
}
