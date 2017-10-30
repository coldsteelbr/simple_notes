package ru.romanbrazhnikov.simplenotes.notelist.view;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by roman on 30.10.17.
 */

public class NoteListPagerAdapter extends FragmentStatePagerAdapter {

    private int mCount = 1;
    private final NoteListFragment.RANGES mRange;

    public NoteListPagerAdapter(FragmentManager fm, NoteListFragment.RANGES range) {
        super(fm);

        mRange = range;
        // TODO: count
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = NoteListFragment.getInstance(i, mRange);

        return fragment;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}

