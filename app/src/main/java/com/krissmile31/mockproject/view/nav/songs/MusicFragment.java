package com.krissmile31.mockproject.view.nav.songs;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.krissmile31.mockproject.view.nav.songs.tab.MusicFragmentAdapter;
import com.krissmile31.mockproject.view.nav.songs.tab.allsongs.AllSongsFragment;

public class MusicFragment extends Fragment {
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    private MusicFragmentAdapter mMusicFragmentAdapter;
    private TextView mTvSongs;
    private EditText mSongSearcher;
    private ImageView mOpenSearcher;

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
        mTvSongs = view.findViewById(R.id.tv_songs_hide_search);
        mSongSearcher = view.findViewById(R.id.edt_search_songs);
        mOpenSearcher = view.findViewById(R.id.img_search_open);

        setViewPager();

        mOpenSearcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearcher();
            }
        });

        searching();

        return view;
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

    private void searching() {
        mSongSearcher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                new AllSongsFragment().allSongsAdapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}