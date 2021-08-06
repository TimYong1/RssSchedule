package com.sx.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sx.base.utils.ClassUtil
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<B : ViewDataBinding, VM : ViewModel>: Fragment() {
    protected lateinit var binding: B
    private var mFragmentProvider:ViewModelProvider?= null
    private lateinit var toast:Toast

    protected val mViewModel: VM by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(this.activity?.application!!)
            .create(ClassUtil.create<VM>(javaClass.genericSuperclass, 1))
    }

    //TODO tip 1: DataBinding 严格模式（详见 DataBindingFragment - - - - - ）：
    // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
    // 通过这样的方式，来彻底解决 视图调用的一致性问题，
    // 如此，视图调用的安全性将和基于函数式编程思想的 Jetpack Compose 持平。
    // 如果这样说还不理解的话，详见 https://xiaozhuanlan.com/topic/9816742350 和 https://xiaozhuanlan.com/topic/2356748910
    //TODO tip 2: Jetpack 通过 "工厂模式" 来实现 ViewModel 的作用域可控，
    //目前我们在项目中提供了 Application、Activity、Fragment 三个级别的作用域，
    //值得注意的是，通过不同作用域的 Provider 获得的 ViewModel 实例不是同一个，
    //所以如果 ViewModel 对状态信息的保留不符合预期，可以从这个角度出发去排查 是否眼前的 ViewModel 实例不是目标实例所致。
    //如果这样说还不理解的话，详见 https://xiaozhuanlan.com/topic/6257931840
    protected open fun <T : ViewModel?> getFragmentScopeViewModel(modelClass: Class<T>): T {
        if (mFragmentProvider == null) {
            mFragmentProvider = ViewModelProvider(this)
        }
        return mFragmentProvider?.get(modelClass)!!
    }

    abstract fun getLayoutId(): Int

    abstract fun init(savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        toast = Toast.makeText(this.context,"",Toast.LENGTH_LONG)
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), null, false)
        binding.lifecycleOwner = this
        init(savedInstanceState)
        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    private fun getViewModelClass(): Class<VM> {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        return type as Class<VM>
    }

    fun showToast(text:String){
        toast.setText(text)
        toast.show()
    }
}