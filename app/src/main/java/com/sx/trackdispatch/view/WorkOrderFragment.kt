package com.sx.trackdispatch.view

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sx.base.BaseFragment
import com.sx.trackdispatch.R
import com.sx.trackdispatch.adapter.ImageViewAdapter
import com.sx.trackdispatch.adapter.WorkOrderAdapter
import com.sx.trackdispatch.adapter.WorkOrderProgressAdapter
import com.sx.trackdispatch.databinding.FragmentWorkOrderBinding
import com.sx.trackdispatch.viewmodel.WorkOrderViewModel

class WorkOrderFragment : BaseFragment<FragmentWorkOrderBinding, WorkOrderViewModel>()  {

    override fun getLayoutId(): Int {
        return R.layout.fragment_work_order
    }

    override fun init(savedInstanceState: Bundle?) {
        binding.vm = mViewModel
        binding.adapter = WorkOrderAdapter(this.context)
        binding.imgAdapter = ImageViewAdapter(this.context)
        binding.imgLayoutManager = GridLayoutManager(this.context,3)
        binding.progressLayoutManager = LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
        binding.workProgressAdapter = WorkOrderProgressAdapter(this.context)
    }

    inner class ClickProxy{
        fun examine(state:Int){
            mViewModel.examineState.value = state
        }
    }
}