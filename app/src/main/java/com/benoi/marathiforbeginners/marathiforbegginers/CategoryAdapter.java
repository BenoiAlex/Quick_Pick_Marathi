package com.benoi.marathiforbeginners.marathiforbegginers;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
public class CategoryAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[] { "Numbers", "Colors", "Family", "Phrases"};

    public CategoryAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        if (position == 0) {
            return new NumbersFragment();
        } else if (position == 1){
            return new ColorsFragment();
        } else if (position == 2) {
            return new FamilyFragment();
        } else {
            return new PhrasesFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}