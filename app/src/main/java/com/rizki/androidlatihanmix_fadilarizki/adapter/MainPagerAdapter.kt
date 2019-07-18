package com.rizki.androidlatihanmix_fadilarizki.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    private val mFragments = ArrayList<Fragment>()

    override fun getItem(position: Int): Fragment {
        return mFragments.get(position);
    }

    fun addFragment(fragment: Fragment) {
        mFragments.add(fragment)
    }

    override fun getCount(): Int {
        return mFragments.size
    }
}