package com.krissmile31.mockproject.nav.songs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.nav.songs.tab.MusicFragmentAdapter;

public class MusicFragment extends Fragment implements View.OnClickListener {
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    private MusicFragmentAdapter mMusicFragmentAdapter;
    private TextView mTvSongs;
    public EditText mSongSearcher;
    private ImageView mOpenSearcher;

    public MusicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        init(view);
        setViewPager();

        mOpenSearcher.setOnClickListener(this);

        return view;
    }

    private void init(View view) {
        mTabLayout = view.findViewById(R.id.tabLayout);
        mViewPager = view.findViewById(R.id.viewPager);
        mTvSongs = view.findViewById(R.id.tv_songs_hide_search);
        mSongSearcher = view.findViewById(R.id.edt_search_songs);
        mOpenSearcher = view.findViewById(R.id.img_search_open);

    }

    private void setViewPager() {
        mMusicFragmentAdapter = new MusicFragmentAdapter(this);
        mViewPager.setAdapter(mMusicFragmentAdapter);

        new TabLayoutMediator(mTabLayout, mViewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
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
    }

    private void openSearcher() {
        mTvSongs.setVisibility(View.GONE);
        mSongSearcher.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_search_open:
                openSearcher();
        }
    }
}