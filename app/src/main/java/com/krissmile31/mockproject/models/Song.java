package com.krissmile31.mockproject.models;

import java.io.Serializable;

public class Song implements Serializable {
    private long id;
    private String image;
    private String data;
    private int thumbnail;
    private String song;
    private String singer;
    private int music;
    private int quantityAlbums;
    private int quantitySongs;
    private String albumName;
    private String genre;
    private int year;

    public Song() {
    }

    public Song(String song) {
        this.song = song;
    }

    public Song(int thumbnail, String song, String singer) {
        this.thumbnail = thumbnail;
        this.song = song;
        this.singer = singer;
    }



    public Song(String song, String singer) {
        this.song = song;
        this.singer = singer;
    }

    public Song(int thumbnail, String singer) {
        this.thumbnail = thumbnail;
        this.singer = singer;
    }

    public Song(int thumbnail, String song, String singer, int music) {
        this.thumbnail = thumbnail;
        this.song = song;
        this.singer = singer;
        this.music = music;
    }

    public Song(int thumbnail, String singer, int quantityAlbums, int quantitySongs) {
        this.thumbnail = thumbnail;
        this.singer = singer;
        this.quantityAlbums = quantityAlbums;
        this.quantitySongs = quantitySongs;
    }

    public Song(int thumbnail, String singer, int quantitySongs, String albumName) {
        this.thumbnail = thumbnail;
        this.singer = singer;
        this.quantitySongs = quantitySongs;
        this.albumName = albumName;
    }

    public Song(int thumbnail, int quantitySongs, String genre) {
        this.thumbnail = thumbnail;
        this.quantitySongs = quantitySongs;
        this.genre = genre;
    }

    public Song(int thumbnail, String albumName, int year) {
        this.thumbnail = thumbnail;
        this.albumName = albumName;
        this.year = year;
    }

    public Song(long id, int thumbnail, String song, String singer) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.song = song;
        this.singer = singer;
    }

    public Song(long id, String song, String singer) {
        this.id = id;
        this.image = image;
        this.data = data;
        this.song = song;
        this.singer = singer;
    }

    public Song(long id, String song, String singer, String image, String data) {
        this.id = id;
        this.image = image;
        this.data = data;
        this.song = song;
        this.singer = singer;
    }

    public Song(long id, String song, String singer, String data) {
        this.id = id;
        this.data = data;
        this.song = song;
        this.singer = singer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public int getQuantityAlbums() {
        return quantityAlbums;
    }

    public void setQuantityAlbums(int quantityAlbums) {
        this.quantityAlbums = quantityAlbums;
    }

    public int getQuantitySongs() {
        return quantitySongs;
    }

    public void setQuantitySongs(int quantitySongs) {
        this.quantitySongs = quantitySongs;
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
