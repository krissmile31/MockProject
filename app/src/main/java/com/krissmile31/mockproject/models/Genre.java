package com.krissmile31.mockproject.models;

import java.io.Serializable;

public class Genre implements Serializable {
    private long genreId;
    private String genreName;
    private String thumbnailGenre;
    private int noSongsGenre;

    public Genre(long genreId, String genreName, String thumbnailGenre, int noSongsGenre) {
        this.genreId = genreId;
        this.genreName = genreName;
        this.thumbnailGenre = thumbnailGenre;
        this.noSongsGenre = noSongsGenre;
    }

    public Genre(long genreId, String genreName, String thumbnailGenre) {
        this.genreId = genreId;
        this.genreName = genreName;
        this.thumbnailGenre = thumbnailGenre;
    }

    public Genre(long genreId, String thumbnailGenre) {
        this.genreId = genreId;
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

    public int getNoSongsGenre() {
        return noSongsGenre;
    }
}
