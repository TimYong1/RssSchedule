package com.sx.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.common.utils.AppManagerUtil
import com.example.common.utils.LogUtil
import com.sx.base.utils.ClassUtil

abstract class BaseActivity<B : ViewDataBinding, VM : ViewModel> : AppCompatActivity() {
    protected lateinit var binding: B
    private var mApplicationProvider: ViewModelProvider? = null
    val backImgClick = BackImgClick()
    private lateinit var toast: Toast

    protected val mViewModel: VM by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
            .create(ClassUtil.create<VM>(javaClass.genericSuperclass, 1))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManagerUtil.instance().addActivity(this)
        toast = Toast.makeText(this, "", Toast.LENGTH_LONG)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.lifecycleOwner = this
        setTransparentStatusBar()
        init()
    }

    fun showToast(text: String?){
        if(!TextUtils.isEmpty(text)){
            toast.setText(text)
            toast.show()
        }
    }

    abstract fun init()

    abstract fun getLayoutId():Int

    protected fun setTransparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.let {
                val decorView = window.decorView
                it.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN /*or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION*/ or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                it.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                it.statusBarColor = resources.getColor(R.color.no_color)
                it.navigationBarColor = Color.TRANSPARENT
//                setAndroidNativeLightStatusBar(this,true)
            }
        }
    }

    inner class BackImgClick:View.OnClickListener{
        override fun onClick(v: View?) {
          this@BaseActivity.finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d("onDestroy回掉了base")
        AppManagerUtil.instance().removeActivity(this)
    }
}