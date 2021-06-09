package com.sx.base.net

import android.text.TextUtils
import com.example.common.utils.ObjectUtils
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Exception
import java.util.*

/**
 * 网络请求拦截器
 */
class HttpInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response? {
        var response:Response? = null
        try {
            val request = chain.request()
            response = chain.proceed(request)
        }catch (e:Exception){
            e.printStackTrace()
        }
        return response
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