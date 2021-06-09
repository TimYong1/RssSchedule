package com.sx.trackdispatch.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sx.trackdispatch.MyApplication
import com.sx.trackdispatch.R
import com.sx.trackdispatch.model.TabMenuBean

class MainViewModel: ViewModel() {
    var position:MutableLiveData<Int> = MutableLiveData()
    var context:Context
    var menuList:MutableList<TabMenuBean>

    init {
        context = MyApplication.application
        position.value = 0
        menuList = mutableListOf(
            TabMenuBean(context.getDrawable(R.mipmap.track_map_select)!!, context.getDrawable(R.mipmap.track_map)!!,"列车运行图"),
            TabMenuBean(context.getDrawable(R.mipmap.blueprint_select)!!, context.getDrawable(R.mipmap.blueprint)!!,"列车计划图"),
            TabMenuBean(context.getDrawable(R.mipmap.groups_select)!!, context.getDrawable(R.mipmap.groups)!!,"车皮编组"),
            TabMenuBean(context.getDrawable(R.mipmap.hidden_danger_select)!!, context.getDrawable(R.mipmap.hidden_danger)!!,"隐患上报"),
            TabMenuBean(context.getDrawable(R.mipmap.transfer_order_select)!!, context.getDrawable(R.mipmap.transfer_order)!!,"调令"),
            TabMenuBean(context.getDrawable(R.mipmap.video_surveillance_select)!!, context.getDrawable(R.mipmap.video_surveillance)!!,"视频监控")
        )
    }
}