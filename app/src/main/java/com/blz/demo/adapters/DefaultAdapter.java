package com.blz.demo.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.blz.demo.fragments.DefaultFragment;
import com.blz.demo.fragments.TextFragment;

import java.util.List;

/**
 * Created by 卜令壮
 * on 2015/12/28
 * E-mail q137617549@qq.com
 */
public class DefaultAdapter extends FragmentStatePagerAdapter {
    private List<String> list;

    public DefaultAdapter(FragmentManager fm, List<String> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        String fragmentName = list.get(position);
        return TextFragment.newInstance(fragmentName);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }
}
