package com.sx.trackdispatch.adapter

import androidx.recyclerview.widget.DiffUtil
import com.sx.hikvideo.Control.DevManageGuider
import com.sx.trackdispatch.model.TransferOrderBean
import com.sx.trackdispatch.model.VideoDevice

class DiffUtils {
    private var hiddenDangerCallback: DiffUtil.ItemCallback<String>? = null
    private var transferOrderCallback: DiffUtil.ItemCallback<TransferOrderBean>? = null
    private var videoCallback: DiffUtil.ItemCallback<VideoDevice>? = null
    private var chatMsgCallback: DiffUtil.ItemCallback<String>? = null
    private var workOrderCallback: DiffUtil.ItemCallback<String>? = null
    private var imageCallback: DiffUtil.ItemCallback<String>? = null
    private var workOrderProgressCallback: DiffUtil.ItemCallback<String>? = null
    private var chatMsgContentCallback: DiffUtil.ItemCallback<String>? = null
    private var chatUserCallback: DiffUtil.ItemCallback<String>? = null
    private var fileCallback: DiffUtil.ItemCallback<String>? = null

    companion object {
        val instance: DiffUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { DiffUtils() }
    }

    fun getHiddenDangerCallback(): DiffUtil.ItemCallback<String>? {
        if (hiddenDangerCallback == null) {
            hiddenDangerCallback = object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }
            }
        }
        return hiddenDangerCallback
    }

    fun getTransferOrderCallback(): DiffUtil.ItemCallback<TransferOrderBean>? {
        if (transferOrderCallback == null) {
            transferOrderCallback = object : DiffUtil.ItemCallback<TransferOrderBean>() {
                override fun areItemsTheSame(oldItem: TransferOrderBean, newItem: TransferOrderBean): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: TransferOrderBean, newItem: TransferOrderBean): Boolean {
                    return false
                }
            }
        }
        return transferOrderCallback
    }

    fun getVideoCallback(): DiffUtil.ItemCallback<VideoDevice>? {
        if (videoCallback == null) {
            videoCallback = object : DiffUtil.ItemCallback<VideoDevice>() {
                override fun areItemsTheSame(oldItem: VideoDevice, newItem: VideoDevice): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: VideoDevice, newItem: VideoDevice): Boolean {
                    return false
                }
            }
        }
        return videoCallback
    }

    fun getChatMsgCallback(): DiffUtil.ItemCallback<String>? {
        if (chatMsgCallback == null) {
            chatMsgCallback = object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }
            }
        }
        return chatMsgCallback
    }

    fun getWorkOrderCallback(): DiffUtil.ItemCallback<String>? {
        if (workOrderCallback == null) {
            workOrderCallback = object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }
            }
        }
        return workOrderCallback
    }

    fun getImageCallback(): DiffUtil.ItemCallback<String>? {
        if (imageCallback == null) {
            imageCallback = object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }
            }
        }
        return imageCallback
    }

    fun getWorkOrderProgressCallback(): DiffUtil.ItemCallback<String>? {
        if (workOrderProgressCallback == null) {
            workOrderProgressCallback = object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }
            }
        }
        return workOrderProgressCallback
    }

    fun getChatMsgContetCallback(): DiffUtil.ItemCallback<String>? {
        if (chatMsgContentCallback == null) {
            chatMsgContentCallback = object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }
            }
        }
        return chatMsgContentCallback
    }

    fun getChatUserCallback(): DiffUtil.ItemCallback<String>? {
        if (chatUserCallback == null) {
            chatUserCallback = object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }
            }
        }
        return chatUserCallback
    }

    fun getFileCallback(): DiffUtil.ItemCallback<String>? {
        if (fileCallback == null) {
            fileCallback = object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }
            }
        }
        return fileCallback
    }

}