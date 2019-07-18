package com.rizki.androidlatihanmix_fadilarizki.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.rizki.androidlatihanmix_fadilarizki.R
import com.rizki.androidlatihanmix_fadilarizki.adapter.MainPagerAdapter
import kotlinx.android.synthetic.main.fragment_main.*

class FragmentMain : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainPagerAdapter = MainPagerAdapter(childFragmentManager)
        mainPagerAdapter.addFragment(FragmentContent())
        mainPagerAdapter.addFragment(FragmentUpload())
        viewPager.adapter = mainPagerAdapter

        tabLayout.setupWithViewPager(viewPager)
    }


}