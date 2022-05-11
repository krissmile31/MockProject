package com.krissmile31.mockproject.musics.tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.krissmile31.mockproject.musics.tab.albums.AlbumsFragment;
import com.krissmile31.mockproject.musics.tab.allsongs.AllSongsFragment;
import com.krissmile31.mockproject.musics.tab.artists.ArtistsFragment;
import com.krissmile31.mockproject.musics.tab.genres.GenresFragment;
import com.krissmile31.mockproject.musics.tab.playlists.PlaylistsFragment;

public class MusicFragmentAdapter extends FragmentStateAdapter {

    public MusicFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public MusicFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public MusicFragmentAdapter(@NonNull FragmentManager fragmentManager,
                                @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
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
