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
import com.sx.trackdispatch.databinding.DialogSynDeviceinfoBinding

class DeviceInfoSynDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {
    private var binding: DialogSynDeviceinfoBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_syn_deviceinfo,null,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    class Builder constructor(){
        companion object{
            fun create(context: Context):DeviceInfoSynDialog{
                val dialog = DeviceInfoSynDialog(context,R.style.iosdialog)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                return dialog
            }
        }
    }

//    class Builder{
//        fun create(context: Context):DeviceInfoSynDialog{
//            val dialog = DeviceInfoSynDialog(context,R.style.iosdialog)
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//            return dialog
//        }
//    }

}