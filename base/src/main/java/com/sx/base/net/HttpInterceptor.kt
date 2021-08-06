package com.sx.base.net

import android.text.TextUtils
import android.util.Log
import com.alibaba.fastjson.JSONObject
import com.example.common.utils.ObjectUtils
import com.example.common.utils.SPUtil
import com.util.toast.ToastUtils
import okhttp3.*
import okio.Buffer
import java.io.IOException
import java.lang.Exception
import java.util.*

/**
 * 网络请求拦截器
 */
class HttpInterceptor : Interceptor {
    private val TAG: String = HttpInterceptor::class.java.getSimpleName()
    override fun intercept(chain: Interceptor.Chain): Response? {
        var response:Response? = null
        try {
            var request = chain.request()
            val build = request.newBuilder()
            if(!TextUtils.isEmpty(SPUtil.getToken())){
                build.addHeader("Authorization","Bearer ${SPUtil.getToken()}")
            }
            request = build.build()
            logForRequest(request)
            response = chain.proceed(request)
        }catch (e:Exception){
            e.printStackTrace()
        }
        return logForResponse(response!!)
    }

    private fun logForResponse(
        response: Response
    ): Response? {
        try {
//            if (UrlConfig.DEBUG) {
            Log.e(TAG, "========response'log=======")
            val builder = response.newBuilder()
            val clone = builder.build()
            Log.e(TAG, "url : " + clone.request().url())
            Log.e(TAG, "code : " + clone.code())
            Log.e(TAG, "protocol : " + clone.protocol())
            if (!TextUtils.isEmpty(clone.message())) {
                Log.e(TAG, "message : " + clone.message())
            }
            var body = clone.body()
            if (body != null) {
                val mediaType = body.contentType()
                if (mediaType != null) {
                    Log.e(TAG, "responseBody's contentType : $mediaType")
                    if (isText(mediaType)) {
                        val resp = body.string()
                        Log.e(TAG, "responseBody's content : $resp")
                        //添加错误Toast---------
                        try{
                            val msgData = JSONObject.parseObject(resp)
                            val code = msgData.getIntValue("code")
                            if(code!=200){
                                val msg = msgData.getString("msg")
                                if(!TextUtils.isEmpty(msg)){
                                    ToastUtils.show("code:"+code+"  msg:"+msg)
                                }
                            }
                        }catch (e:Exception){
                            e.printStackTrace()
                        }
                        //----------------
                        body = ResponseBody.create(mediaType, resp)
                        return response.newBuilder().body(body).build()
                    } else {
                        Log.e(TAG, "responseBody's content : " + " maybe [file part] , too large too print , ignored!")
                    }
                }
            }
            Log.e(TAG, "========response'log=======end")
//            }
        } catch (e: java.lang.Exception) {
        }
        return response
    }

    private fun logForRequest(request: Request) {
        try {
            val url = request.url().toString()
            val headers = request.headers()
            Log.e(TAG, "========request'log=======")
            Log.e(TAG, "method : " + request.method())
            Log.e(TAG, "url : $url")
            if (headers != null && headers.size() > 0) {
                Log.e(TAG, "headers : $headers")
            }
            val requestBody = request.body()
            if (requestBody != null) {
                val mediaType = requestBody.contentType()
                if (mediaType != null) {
                    Log.e(TAG, "requestBody's contentType : $mediaType")
                    if (isText(mediaType)) {
                        Log.e(TAG, "requestBody's content : " + bodyToString(request))
                    } else {
                        Log.e(TAG, "requestBody's content : " + " maybe [file part] , too large too print , ignored!")
                    }
                }
            }
            Log.e(TAG, "========request'log=======end")
        } catch (e: Exception) {
            e.printStackTrace();
        }
    }

    private fun isText(mediaType: MediaType): Boolean {
        if (mediaType.type() != null && mediaType.type() == "text") {
            return true
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype() == "json" || mediaType.subtype() == "xml" || mediaType.subtype() == "html" || mediaType.subtype() == "webviewhtml" || mediaType.subtype() == "x-www-form-urlencoded") return true
        }
        return false
    }

    private fun bodyToString(request: Request): String? {
        return try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body()!!.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "something error when show requestBody."
        }
    }

    companion object{
        fun initFormBodyBuilder(params: HashMap<String, String>): FormBody.Builder {
            val builder = FormBody.Builder()
            if (!ObjectUtils.isEmpty(params)) {
                val keys: Set<String> = params.keys
                val iterator: Iterator<*> = keys.iterator()
                while (iterator.hasNext()) {
                    val key = iterator.next() as String
                    if (!TextUtils.isEmpty(params[key])) builder.add(
                        key,
                        params[key]
                    )
                }
            }
            return builder
        }
    }
}