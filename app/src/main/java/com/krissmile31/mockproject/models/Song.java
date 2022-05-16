package com.krissmile31.mockproject.models;

import java.io.Serializable;

public class Song implements Serializable {
    private long id;
    private String thumbnail;
    private String data;
    private String songName;
    private String singer;
    private int music;
    private int quantityAlbums;
    private int quantitySongs;
    private String albumName;
    private String genre;
    private int year;
    private int duration;
    private int thumbnails;

    public Song() {
    }

    public Song(String songName) {
        this.songName = songName;
    }

    public Song(int thumbnails, String songName, String singer) {
        this.thumbnails = thumbnails;
        this.songName = songName;
        this.singer = singer;
    }




    public Song(String songName, String singer) {
        this.songName = songName;
        this.singer = singer;
    }

    public Song(int thumbnails, String singer) {
        this.thumbnails = thumbnails;
        this.singer = singer;
    }

    public Song(int thumbnails, String songName, String singer, int music) {
        this.thumbnails = thumbnails;
        this.songName = songName;
        this.singer = singer;
        this.music = music;
    }

    public Song(int thumbnails, String singer, int quantityAlbums, int quantitySongs) {
        this.thumbnails = thumbnails;
        this.singer = singer;
        this.quantityAlbums = quantityAlbums;
        this.quantitySongs = quantitySongs;
    }

    public Song(int thumbnails, String singer, int quantitySongs, String albumName) {
        this.thumbnails = thumbnails;
        this.singer = singer;
        this.quantitySongs = quantitySongs;
        this.albumName = albumName;
    }

    public Song(int thumbnails, int quantitySongs, String genre) {
        this.thumbnails = thumbnails;
        this.quantitySongs = quantitySongs;
        this.genre = genre;
    }

    public Song(int thumbnails, String albumName, int year) {
        this.thumbnails = thumbnails;
        this.albumName = albumName;
        this.year = year;
    }

    public Song(long id, int thumbnails, String songName, String singer) {
        this.id = id;
        this.thumbnails = thumbnails;
        this.songName = songName;
        this.singer = singer;
    }

    public Song(long id, String songName, String singer) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.data = data;
        this.songName = songName;
        this.singer = singer;
    }

    public Song(long id, String songName, String singer, String thumbnail, String data) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.data = data;
        this.songName = songName;
        this.singer = singer;
    }

    public Song(long id, String songName, String singer, String data) {
        this.id = id;
        this.data = data;
        this.songName = songName;
        this.singer = singer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(int thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
