package com.krissmile31.mockproject.models;

import java.io.Serializable;

public class Album implements Serializable {
    private int thumbnail;
    private String song;
    private String singer;
    private int music;
    private int quantity_albums;
    private int quantity_songs;
    private String albumName;
    private String genre;
    private int year;

    public Album(String song) {
        this.song = song;
    }

    public Album(int thumbnail, String song, String singer) {
        this.thumbnail = thumbnail;
        this.song = song;
        this.singer = singer;
    }

    public Album(String song, String singer) {
        this.song = song;
        this.singer = singer;
    }

    public Album(int thumbnail, String singer) {
        this.thumbnail = thumbnail;
        this.singer = singer;
    }

    public Album(int thumbnail, String song, String singer, int music) {
        this.thumbnail = thumbnail;
        this.song = song;
        this.singer = singer;
        this.music = music;
    }

    public Album(int thumbnail, String singer, int quantity_albums, int quantity_songs) {
        this.thumbnail = thumbnail;
        this.singer = singer;
        this.quantity_albums = quantity_albums;
        this.quantity_songs = quantity_songs;
    }

    public Album(int thumbnail, String singer, int quantity_songs, String albumName) {
        this.thumbnail = thumbnail;
        this.singer = singer;
        this.quantity_songs = quantity_songs;
        this.albumName = albumName;
    }

    public Album(int thumbnail, int quantity_songs, String genre) {
        this.thumbnail = thumbnail;
        this.quantity_songs = quantity_songs;
        this.genre = genre;
    }

    public Album(int thumbnail, String albumName, int year) {
        this.thumbnail = thumbnail;
        this.albumName = albumName;
        this.year = year;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public int getMusic() {
        return music;
    }

    public void setMusic(int music) {
        this.music = music;
    }

    public int getQuantity_albums() {
        return quantity_albums;
    }

    public void setQuantity_albums(int quantity_albums) {
        this.quantity_albums = quantity_albums;
    }

    public int getQuantity_songs() {
        return quantity_songs;
    }

    public void setQuantity_songs(int quantity_songs) {
        this.quantity_songs = quantity_songs;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
