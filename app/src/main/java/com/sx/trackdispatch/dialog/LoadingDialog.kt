package com.sx.trackdispatch.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.databinding.DataBindingUtil
import com.sx.trackdispatch.R
import com.sx.trackdispatch.databinding.DialogLoadingBinding

class LoadingDialog (context: Context, themeResId: Int) : Dialog(context, themeResId) {
    private var binding: DialogLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_loading,null,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setOnDismissListener {
            stopAnimation()
        }
    }

    override fun show() {
        super.show()
        startAnimation()
    }

    private fun startAnimation() {
        val animation = AnimationUtils.loadAnimation(context, R.anim.rotate_loding)
        animation.interpolator = LinearInterpolator()
        binding.imgLoading.startAnimation(animation)
    }

    private fun stopAnimation() {
        binding.imgLoading.clearAnimation()
    }

    class Builder{
        fun create(context: Context):LoadingDialog{
            return LoadingDialog(context, R.style.iosdialog)
        }
    }
}