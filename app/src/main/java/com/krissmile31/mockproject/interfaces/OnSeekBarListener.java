package com.krissmile31.mockproject.interfaces;

import android.media.MediaPlayer;

public interface OnSeekBarListener {
    void checkMedia();
    int currentDuration();
    int totalDuration();
    String getCurrentDuration();
    String getTotalDuration();
}