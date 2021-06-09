@file:Suppress("UNCHECKED_CAST")

package com.sx.base.utils

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @PackageName : com.waiting.fm.utils
 * @Author : hechao
 * @Date :   2019-12-10 05:52
 * 适用于父类获取子类必须重载的泛型对象
 */
object ClassUtil {
    fun <T> create(type: Type?, index: Int = 0): Class<T> {
        val parameterizedType = type as ParameterizedType
        val actualTypeArguments = parameterizedType.actualTypeArguments
        return actualTypeArguments[index] as Class<T>
    }
}