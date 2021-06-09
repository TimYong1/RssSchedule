package com.sx.trackdispatch.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.sx.trackdispatch.R
import com.sx.trackdispatch.databinding.DialogIosDefaultBinding

class TipDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {
    private var binding: DialogIosDefaultBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_ios_default,null,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initListener()
    }

    private fun initListener() {
        binding.tvCancel.setOnClickListener {
            dismiss()
        }
    }

    fun setEnsureListener(listener: View.OnClickListener){
        binding.tvEnsure.setOnClickListener(listener)
    }

    fun showDialog(title:String){
        binding.tvMessage.setText(title)
        show()
    }

    class Builder private constructor(){
        companion object{
            fun create(context: Context):TipDialog{
                val dialog = TipDialog(context,R.style.iosdialog)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                return dialog
            }
        }
    }
}