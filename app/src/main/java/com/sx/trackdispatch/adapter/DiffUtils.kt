package com.sx.trackdispatch.adapter

import androidx.recyclerview.widget.DiffUtil

class DiffUtils {
    private var hiddenDangerCallback: DiffUtil.ItemCallback<String>? = null
    private var transferOrderCallback: DiffUtil.ItemCallback<String>? = null
    private var videoCallback: DiffUtil.ItemCallback<String>? = null
    private var chatMsgCallback: DiffUtil.ItemCallback<String>? = null
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

    fun getTransferOrderCallback(): DiffUtil.ItemCallback<String>? {
        if (transferOrderCallback == null) {
            transferOrderCallback = object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }
            }
        }
        return transferOrderCallback
    }

    fun getVideoCallback(): DiffUtil.ItemCallback<String>? {
        if (videoCallback == null) {
            videoCallback = object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
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