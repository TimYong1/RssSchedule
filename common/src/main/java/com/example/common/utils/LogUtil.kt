package com.example.common.utils

import android.util.Log


class LogUtil{
    companion object{
        private val TAG = "测试"
        fun d(content:String){
            Log.d(TAG,content)
        }

        fun e(content:String){
            Log.e(TAG,"-----------------------------"+content+"-----------------------------")
        }

        fun v(content:String){
            Log.v(TAG,content)
        }

        fun w(content:String){
            Log.w(TAG,content)
        }

        fun d(tag:String,content:String){
            Log.d(tag,content)
        }

        fun e(tag:String,content:String){
            Log.e(tag,content)
        }

        fun v(tag:String,content:String){
            Log.v(tag,content)
        }

        fun w(tag:String,content:String){
            Log.w(tag,content)
        }
    }
}