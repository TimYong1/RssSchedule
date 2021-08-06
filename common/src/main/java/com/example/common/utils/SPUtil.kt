package com.example.common.utils

import android.content.Context
import android.content.SharedPreferences

object SPUtil {
    private var sp: SharedPreferences? = null

    fun initSp(context:Context){
        sp = context.getSharedPreferences(context.opPackageName, Context.MODE_PRIVATE)
    }
    
    @JvmOverloads
    fun put(key: String, value: String, isCommit: Boolean = false) {
        if (isCommit) {
            sp!!.edit().putString(key, value).commit()
        } else {
            sp!!.edit().putString(key, value).apply()
        }
    }

    fun getToken():String{
        return sp!!.getString("Token","")!!
    }

    fun putToken(token:String){
        sp!!.edit().putString("Token", token).commit()
    }

    fun putIMToken(token:String){
        sp!!.edit().putString("IMToken", token).commit()
    }

    fun gutIMToken():String{
        return sp!!.getString("IMToken","")!!
    }

    fun putUserId(token:String){
        sp!!.edit().putString("userId", token).commit()
    }

    fun getUserId():String{
        return sp!!.getString("userId","")!!
    }

    fun putIMUserId(token:String){
        sp!!.edit().putString("IMuserId", token).commit()
    }

    fun getIMUserId():String{
        return sp!!.getString("IMuserId","")!!
    }

    /**
     * Return the string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the string value if sp exists or `defaultValue` otherwise
     */
    @JvmOverloads
    fun getString(key: String, defaultValue: String = ""): String? {
        return sp!!.getString(key, defaultValue)
    }

    /**
     * Put the int value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    @JvmOverloads
    fun put(key: String, value: Int, isCommit: Boolean = false) {
        if (isCommit) {
            sp!!.edit().putInt(key, value).commit()
        } else {
            sp!!.edit().putInt(key, value).apply()
        }
    }

    /**
     * Return the int value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the int value if sp exists or `defaultValue` otherwise
     */
    @JvmOverloads
    fun getInt(key: String, defaultValue: Int = -1): Int {
        return sp!!.getInt(key, defaultValue)
    }

    /**
     * Put the long value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    @JvmOverloads
    fun put(key: String, value: Long, isCommit: Boolean = false) {
        if (isCommit) {
            sp!!.edit().putLong(key, value).commit()
        } else {
            sp!!.edit().putLong(key, value).apply()
        }
    }

    /**
     * Return the long value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the long value if sp exists or `defaultValue` otherwise
     */
    @JvmOverloads
    fun getLong(key: String, defaultValue: Long = -1L): Long {
        return sp!!.getLong(key, defaultValue)
    }

    /**
     * Put the float value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    @JvmOverloads
    fun put(key: String, value: Float, isCommit: Boolean = false) {
        if (isCommit) {
            sp!!.edit().putFloat(key, value).commit()
        } else {
            sp!!.edit().putFloat(key, value).apply()
        }
    }

    /**
     * Return the float value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the float value if sp exists or `defaultValue` otherwise
     */
    @JvmOverloads
    fun getFloat(key: String, defaultValue: Float = -1f): Float {
        return sp!!.getFloat(key, defaultValue)
    }

    /**
     * Put the boolean value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    @JvmOverloads
    fun put(key: String, value: Boolean, isCommit: Boolean = false) {
        if (isCommit) {
            sp!!.edit().putBoolean(key, value).commit()
        } else {
            sp!!.edit().putBoolean(key, value).apply()
        }
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the boolean value if sp exists or `defaultValue` otherwise
     */
    @JvmOverloads
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sp!!.getBoolean(key, defaultValue)
    }

    /**
     * Put the set of string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    @JvmOverloads
    fun put(key: String,
            value: Set<String>,
            isCommit: Boolean = false) {
        if (isCommit) {
            sp!!.edit().putStringSet(key, value).commit()
        } else {
            sp!!.edit().putStringSet(key, value).apply()
        }
    }
}