package com.minigee.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Zhou on 2015-10-12.
 */
public class TabFragmentPagerAdapater extends FragmentPagerAdapter {
    private List<Fragment> listf;
    private String title[]={"首页","创业问答","精彩活动","最新融资","最新政策","资讯"};

    public TabFragmentPagerAdapater(FragmentManager fm, List<Fragment> listf) {
        super(fm);
        this.listf = listf;
    }

    @Override
    public Fragment getItem(int position) {
        return listf.get(position);
    }

    @Override
    public int getCount() {
        return listf.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }


}
