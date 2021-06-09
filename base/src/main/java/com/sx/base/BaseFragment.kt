package com.sx.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sx.base.utils.ClassUtil
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<B : ViewDataBinding, VM : ViewModel>: Fragment() {
    protected lateinit var binding: B

    protected val mViewModel: VM by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(this.activity?.application!!)
            .create(ClassUtil.create<VM>(javaClass.genericSuperclass, 1))
    }

    abstract fun getLayoutId(): Int

    abstract fun init(savedInstanceState: Bundle?)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,getLayoutId(),null,false)
        binding.lifecycleOwner = this
        init(savedInstanceState)
        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    private fun getViewModelClass(): Class<VM> {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        return type as Class<VM>
    }
}