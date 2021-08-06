package com.sx.trackdispatch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sx.trackdispatch.R
import com.sx.trackdispatch.databinding.ItemDialogBinding
import com.sx.trackdispatch.model.ProjectBean


class DialogListAdapter(private val mContext: Context, private val mList: MutableList<ProjectBean>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemCLick: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            DataBindingUtil.inflate<ItemDialogBinding>(
                LayoutInflater.from(mContext),
                R.layout.item_dialog,
                parent,
                false
            )
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val bean = mList[position]
            holder.itemBinding.imgRight.visibility = View.VISIBLE
            holder.itemBinding.tvContent.text = bean.name
            holder.itemBinding.root.setOnClickListener {
                itemCLick?.itemClick(position,bean)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setOnItemClickListener(itemCLick: OnItemClickListener){
        this.itemCLick = itemCLick
    }

    interface OnItemClickListener {
        fun itemClick(position: Int,bean:ProjectBean)
    }

    inner class ViewHolder(internal var itemBinding: ItemDialogBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}