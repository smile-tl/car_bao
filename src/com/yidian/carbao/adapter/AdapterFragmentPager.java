package com.yidian.carbao.adapter;

import java.util.ArrayList;
import java.util.List;

import com.yidian.carbao.fragment.FragmentBase;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AdapterFragmentPager extends FragmentPagerAdapter {

	 private List<FragmentBase> list = new ArrayList<FragmentBase>();

     public AdapterFragmentPager(FragmentManager fm) {
             super(fm);
     }
     
     public AdapterFragmentPager(FragmentManager fm, List<FragmentBase> list) {
             super(fm);
             this.list = list;
     }

     @Override
     public Fragment getItem(int i) {
             return list.get(i);
     }

     @Override
     public int getCount() {
             return list.size();
     }


}
