package com.sx.trackdispatch.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sx.trackdispatch.view.*

class MainViewPagerAdapter(fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {
    private val fragments = mutableListOf(TrackMapFragment(), BlueprintFragment(),GroupsFragment(), HiddenDangerFragment(), TransferOrderFragment(),VideoSurveillanceFragment())

    override fun getItem(position: Int): Fragment {
        return fragments.get(position)
    }

    override fun getCount(): Int {
        return fragments.size
    }
}