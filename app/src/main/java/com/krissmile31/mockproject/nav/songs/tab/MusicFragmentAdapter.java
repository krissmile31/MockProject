package com.krissmile31.mockproject.nav.songs.tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.krissmile31.mockproject.nav.songs.tab.albums.AlbumsFragment;
import com.krissmile31.mockproject.nav.songs.tab.allsongs.AllSongsFragment;
import com.krissmile31.mockproject.nav.songs.tab.artists.ArtistsFragment;
import com.krissmile31.mockproject.nav.songs.tab.genres.GenresFragment;
import com.krissmile31.mockproject.nav.songs.tab.playlists.PlaylistsFragment;

public class MusicFragmentAdapter extends FragmentStateAdapter {

    public MusicFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new PlaylistsFragment();

            case 2:
                return new AlbumsFragment();

            case 3:
                return new ArtistsFragment();

            case 4:
                return new GenresFragment();

            default:
                return new AllSongsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
