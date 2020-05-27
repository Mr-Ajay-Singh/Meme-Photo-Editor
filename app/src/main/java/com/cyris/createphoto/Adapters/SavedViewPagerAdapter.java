package com.cyris.createphoto.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.cyris.createphoto.fragments.Saved;

public class SavedViewPagerAdapter extends FragmentStatePagerAdapter {
    public SavedViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:return new Saved();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 1;
    }
}
