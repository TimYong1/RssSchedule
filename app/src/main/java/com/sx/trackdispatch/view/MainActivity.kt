package com.sx.trackdispatch.view

import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Chronometer
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.sx.trackdispatch.R
import com.sx.trackdispatch.databinding.ActivityMainBinding

import com.sx.trackdispatch.viewmodel.MainViewModel
import com.sx.base.BaseActivity
import com.sx.trackdispatch.adapter.MainViewPagerAdapter
import com.sx.trackdispatch.dialog.CallDialog
import com.zj.easyfloat.EasyFloat

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    private lateinit var chronometer:Chronometer
    private var callDialog:CallDialog?=null
    private lateinit var pagerAdapter: MainViewPagerAdapter
    private lateinit var navController: NavController

    override fun init() {
        binding.vm = mViewModel
        binding.click = ClickProxy()
        initListener()
        navController = findNavController(R.id.nav_host_fragment)
//        initFloatView()
    }

    private fun showCallDialog(){
        if(callDialog==null){
            callDialog = CallDialog.Builder.create(this)
        }
        callDialog!!.apply {
            setAnswerListener(answerListener)
            setHangUpListener(hungUpListener)
            show()
        }
    }

    private fun initListener() {
        pagerAdapter = MainViewPagerAdapter(this.supportFragmentManager,0)
//        binding.pager.adapter = pagerAdapter
//        binding.pager.offscreenPageLimit = 0
        mViewModel.position.observe(this, Observer {
//            binding.pager.setCurrentItem(it)
            when(it){
                0->navController.navigate(R.id.navigation_home)
                1->navController.navigate(R.id.navigation_blueprint)
                2->navController.navigate(R.id.navigation_groups)
                3->navController.navigate(R.id.navigation_hidden_danger)
                4->navController.navigate(R.id.navigation_transfer_order)
                5->navController.navigate(R.id.navigation_video_surveillance)
            }
        })
    }

    private fun initFloatListener(view:View){
        view.findViewById<View>(R.id.ll_float_call).setOnClickListener {
            showCallDialog()
        }
    }

    private fun initFloatView(){
        EasyFloat.layout(R.layout.float_call)
            .blackList(mutableListOf(SettingActivity::class.java,TransferOrderDetailActivity::class.java))
            .layoutParams(initLayoutParams())
            .listener {
                initFloatListener(it!!)
            }
            .show(this)
        chronometer = EasyFloat.getView<Chronometer>(R.id.chronometer)
        chronometer.start()
    }

    private fun initLayoutParams(): FrameLayout.LayoutParams {
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.BOTTOM or Gravity.END
        params.setMargins(0, params.topMargin, params.rightMargin, 500)
        return params
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus){
//            EasyFloat.setMarginLeft(binding.llNav.measuredWidth)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    inner class ClickProxy {
        fun tabClick(postion:Int){
            mViewModel.position.value = postion
        }
    }

    private val hungUpListener = View.OnClickListener {
        if(callDialog==null){
            return@OnClickListener
        }
        callDialog!!.dismiss()
        callDialog = null
    }

    private val answerListener = View.OnClickListener {
        if(callDialog==null){
            return@OnClickListener
        }

        callDialog!!.apply {
            changeStatus(CallDialog.State.CONNECTED)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        pagerAdapter.fragments[binding.pager.currentItem].onActivityResult(requestCode, resultCode, data)
    }
}