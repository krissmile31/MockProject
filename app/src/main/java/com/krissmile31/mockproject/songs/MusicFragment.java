package com.krissmile31.mockproject.songs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.songs.tab.MusicFragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MusicFragment extends Fragment {
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    private MusicFragmentAdapter mMusicFragmentAdapter;

    public MusicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        mTabLayout = view.findViewById(R.id.tabLayout);
        mViewPager = view.findViewById(R.id.viewPager);

        mMusicFragmentAdapter = new MusicFragmentAdapter(this);
        mViewPager.setAdapter(mMusicFragmentAdapter);

        new TabLayoutMediator(mTabLayout, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText(R.string.all_songs);
                        break;

                    case 1:
                        tab.setText(R.string.playlists);
                        break;

                    case 2:
                        tab.setText(R.string.albums);
                        break;

                    case 3:
                        tab.setText(R.string.artists);
                        break;

                    case 4:
                        tab.setText(R.string.genres);
                        break;
                }
            }
        }).attach();

        return view;
    }
}