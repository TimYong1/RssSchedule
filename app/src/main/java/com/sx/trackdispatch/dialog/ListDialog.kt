package com.sx.trackdispatch.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sx.trackdispatch.R
import com.sx.trackdispatch.adapter.DialogListAdapter
import com.sx.trackdispatch.databinding.DialogListBinding
import java.util.*

class ListDialog {
    private var binding: DialogListBinding? = null
    private var dialog: Dialog? = null
    private var context: Context? = null
    private var adapter: DialogListAdapter? = null
    private var list: MutableList<String> = mutableListOf()

    constructor(context: Context?){
        this.context = context
        if (dialog == null) {
            initDialog()
        }
    }

    private fun initDialog(): ListDialog? {
        if (dialog == null) {
            list = ArrayList()
            binding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.dialog_list,
                null,
                false
            )
            dialog = Dialog(context!!, R.style.iosdialog)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.setContentView(binding?.getRoot()!!)
            adapter = DialogListAdapter(context!!, list)
        }
        return this
    }

    fun setItemListener(listener: DialogListAdapter.OnItemClickListener?) {
        adapter!!.setOnItemClickListener(listener!!)
    }

    fun setDialogListener(listener:DialogInterface.OnDismissListener){
        dialog?.setOnDismissListener(listener)
    }

    fun setBackListener(listener: View.OnClickListener){
        binding?.imgBack?.setOnClickListener(listener)
    }

    fun isShowing(): Boolean {
        return dialog!!.isShowing
    }

    fun showDialog(list: MutableList<String>) {
        if (dialog!!.isShowing) {
            return
        }
        if(binding?.recyclerview?.adapter==null){
            binding?.recyclerview?.setLayoutManager(LinearLayoutManager(context))
            binding?.recyclerview?.setAdapter(adapter)
        }
        refreshData(list)
        dialog?.show()
    }

    fun setList(list: MutableList<String>){
        this.list = list
    }

    private fun refreshData(list: MutableList<String>) {
        this.list.clear()
        this.list.addAll(list)
        adapter!!.notifyDataSetChanged()
    }

    fun dismiss() {
        if (dialog!!.isShowing) {
            dialog?.dismiss()
        }
    }
}