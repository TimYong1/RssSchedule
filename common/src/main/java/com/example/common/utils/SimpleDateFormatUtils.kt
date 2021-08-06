package com.example.common.utils

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class SimpleDateFormatUtils {

    companion object{
        val YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"
        /**
         * 根据输入的format格式，以及format字符串，返回对应的日期
         *
         * @param pattern，字符串的format格式，例如：yyyy-MM-dd HH:mm:ss
         * @param dateFormatStr，format后的日期字符串，例如：2015-02-10 22:00:00
         * @return java.util.Date对象
         * @throws ParseException
         */
        public fun getDataByFormatString(pattern:String ,dateFormatStr:String): Date?{
            val date:Date
            try {
                val simpleDateFormat  = SimpleDateFormat(dateFormatStr);
                date  = simpleDateFormat.parse(pattern);
            }catch (e:Exception){
                e.printStackTrace()
                return null
            }
            return date
        }

        /**
         * @param pattern，字符串的format格式，例如：yyyy-MM-dd HH:mm:ss
         * @param date,需要转换为指定格式的日期对象
         * @return
         */
        public fun getFormatStrByPatternAndDate(pattern:String,date:Date ):String{
            val simpleDateFormat = SimpleDateFormat(pattern);
            val formatStr = simpleDateFormat.format(date);
            return formatStr;
        }
    }
}