package com.example.mockproject.models;

public class Album {
    private int thumbnail;
    private String song;
    private String singer;

    public Album(int thumbnail, String song, String singer) {
        this.thumbnail = thumbnail;
        this.song = song;
        this.singer = singer;
    }

    public Album(String song, String singer) {
        this.song = song;
        this.singer = singer;
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
}
