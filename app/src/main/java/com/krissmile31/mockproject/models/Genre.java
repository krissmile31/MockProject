package com.krissmile31.mockproject.models;

import java.io.Serializable;

public class Genre implements Serializable {
    private long genreId;
    private String genreName;
    private String thumbnailGenre;
    private int numSongsGenre;

    public Genre(long genreId, String genreName, String thumbnailGenre) {
        this.genreId = genreId;
        this.genreName = genreName;
        this.thumbnailGenre = thumbnailGenre;
    }

    public long getGenreId() {
        return genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public String getThumbnailGenre() {
        return thumbnailGenre;
    }

    public int getNumSongsGenre() {
        return numSongsGenre;
    }
}
