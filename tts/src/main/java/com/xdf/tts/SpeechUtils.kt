package com.xdf.tts

import android.app.Application
import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import android.util.Log
import com.example.common.utils.LogUtil
import java.util.*

object SpeechUtils {
    private lateinit var textToSpeech: TextToSpeech

    @Synchronized
    fun init(application: Application):SpeechUtils{
        if(!this::textToSpeech.isInitialized){
            textToSpeech = TextToSpeech(application,TextToSpeech.OnInitListener {
                if(it == TextToSpeech.SUCCESS){
                    textToSpeech.setLanguage(Locale.CHINA)
                    textToSpeech.setPitch(1.5f)// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
                    textToSpeech.setSpeechRate(0.5f)
                }
            })
        }
        return this
    }

    @Synchronized
    fun speakText(text:String){
        var success = 0
        LogUtil.d("speakText内容：$text")
        if (this::textToSpeech.isInitialized) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                success = textToSpeech.speak(text,TextToSpeech.QUEUE_ADD,null,"100")
            }else{
                success = textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null)
            }
        }else{
            LogUtil.d("textToSpeech语音合成工具未初始化")
        }
        LogUtil.d("success:"+success)
    }
}