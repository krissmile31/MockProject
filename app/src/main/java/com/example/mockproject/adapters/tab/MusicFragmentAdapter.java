package com.example.mockproject.adapters.tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mockproject.fragments.musicFragments.AlbumsFragment;
import com.example.mockproject.fragments.musicFragments.AllSongsFragment;
import com.example.mockproject.fragments.musicFragments.ArtistsFragment;
import com.example.mockproject.fragments.musicFragments.GenresFragment;
import com.example.mockproject.fragments.musicFragments.PlaylistsFragment;

public class MusicFragmentAdapter extends FragmentStateAdapter {
    public MusicFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public MusicFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public MusicFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
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
