package com.sx.trackdispatch.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.example.common.utils.LogUtil
import com.sx.trackdispatch.R
import com.sx.trackdispatch.databinding.DialogAnswerBinding
import com.sx.trackdispatch.databinding.DialogIosDefaultBinding

class CallDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {
    private var binding: DialogAnswerBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_answer,null,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    fun setAnswerListener(listener: View.OnClickListener){
        binding.tvAnswer.setOnClickListener(listener)
    }

    fun setHangUpListener(listener: View.OnClickListener){
        binding.tvHungUp.setOnClickListener(listener)
    }

    fun changeStatus(state:State){
        LogUtil.d(state.value)
        if(state==State.CONNECTED){
            binding.tvState.visibility = View.GONE
            binding.chronometer.visibility = View.VISIBLE
            binding.chronometer.base = SystemClock.elapsedRealtime()
            binding.chronometer.start()
            binding.tvAnswer.visibility = View.GONE
        }else if(state==State.CONNECTING){
            binding.tvState.visibility = View.VISIBLE
            binding.chronometer.visibility = View.GONE
            binding.chronometer.stop()
            binding.tvAnswer.visibility = View.VISIBLE
        }
    }

    class Builder private constructor(){
        companion object{
            fun create(context: Context):CallDialog{
                val dialog = CallDialog(context,R.style.iosdialog)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                return dialog
            }
        }
    }

    enum class State constructor(val value:String){
        CONNECTED("connected"),CONNECTING("connecting")
    }
}