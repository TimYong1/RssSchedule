package com.sx.trackdispatch.view

import android.view.Gravity
import android.view.View
import android.widget.Chronometer
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import com.example.common.utils.LogUtil
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

    override fun init() {
        binding.vm = mViewModel
        binding.click = ClickProxy()
        initListener()
        initFloatView()
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
        binding.pager.adapter = MainViewPagerAdapter(this.supportFragmentManager,0)
        mViewModel.position.observe(this, Observer {
            binding.pager.setCurrentItem(it)
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
            EasyFloat.setMarginLeft(binding.llNav.measuredWidth)
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
}