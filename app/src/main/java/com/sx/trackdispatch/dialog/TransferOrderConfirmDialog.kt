package com.sx.trackdispatch.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.sx.trackdispatch.R
import com.sx.trackdispatch.adapter.FileAdapter
import com.sx.trackdispatch.databinding.DialogTransferOrderConfirmBinding
import com.sx.trackdispatch.model.TransferOrderBean
import com.sx.trackdispatch.viewmodel.DialogTransferOrderConfirmViewModel
import com.util.toast.ToastUtils
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.models.sort.SortingTypes

class TransferOrderConfirmDialog(val activity: Activity, val lifecycle: LifecycleOwner, themeResId: Int) : Dialog(activity, themeResId) {
    private var binding: DialogTransferOrderConfirmBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_transfer_order_confirm,null,false)
    private lateinit var vm:DialogTransferOrderConfirmViewModel
    private lateinit var bean:TransferOrderBean
    private lateinit var dialogClick:DialogClick

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.lifecycleOwner = lifecycle
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(activity.application).create(DialogTransferOrderConfirmViewModel::class.java)
        binding.vm = vm
        binding.adapter = FileAdapter(activity,itemClick)
        binding.layoutManage = GridLayoutManager(activity,2)
        binding.click = ClickProxy()
        initListener()
    }

    private fun initListener() {
        binding.tvCancel.setOnClickListener { dismiss() }
        binding.tvConfirm.setOnClickListener {
            if(binding.cbConfirm.isChecked){
                bean.status  = "confirmed"
            }else{
                bean.status  = "abolished"
            }
            bean.remark = binding.edRemark.text.toString()
            dismiss()
            dialogClick.click(bean)
        }
    }

    fun showDialog(bean:TransferOrderBean){
        binding.tvSn.setText(bean.sn)
        binding.edRemark.setText(bean.remark)
        this.bean = bean
        show()
    }

    private val itemClick = object:FileAdapter.ItemClickListener{
        override fun add() {
            pickerFile()
        }

        override fun delete(bean: String) {
            val fileList = mutableListOf<String>()
            fileList.addAll(vm.fileList.value!!)
            fileList.remove(bean)
            vm.fileList.value = fileList
        }
    }

    fun getViewModel():DialogTransferOrderConfirmViewModel{
        return vm
    }

    fun setDialogClick(click:DialogClick){
        this.dialogClick = click
    }

    private fun pickerFile(){
        if(vm.fileList.value!!.size>vm.MAX_FILE_SIZE.value!!){
            ToastUtils.show("文件数量不能超过${vm.MAX_FILE_SIZE}份")
            return
        }
        FilePickerBuilder.instance
            .setMaxCount(1)
            .setSelectedFiles(vm.fileUrls.value!!)
            .setActivityTheme(R.style.FilePickerTheme)
            .setActivityTitle("Please select doc")
            .setImageSizeLimit(5) //Provide Size in MB
            .setVideoSizeLimit(20)
//                    .addFileSupport("ZIP", zips)
//                    .addFileSupport("AAC", pdfs, R.drawable.pdf_blue)
            .enableDocSupport(true)
            .enableSelectAll(true)
            .sortDocumentsBy(SortingTypes.NAME)
            .withOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
            .pickFile(activity)
    }

    inner class ClickProxy{
        fun checkBox(checked: Boolean){
            vm.confirmeState.value = checked
        }
    }

    interface DialogClick{
        fun click(bean:TransferOrderBean)
    }

    class Builder private constructor(){
        companion object{
            fun create(activity: Activity, lifecycle: LifecycleOwner):TransferOrderConfirmDialog{
                val dialog = TransferOrderConfirmDialog(activity,lifecycle, R.style.iosdialog)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                return dialog
            }
        }
    }
}