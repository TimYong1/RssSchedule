package com.example.common.utils

import android.os.Build
import android.util.SparseArray
import android.util.SparseBooleanArray
import android.util.SparseIntArray
import android.util.SparseLongArray
import androidx.annotation.RequiresApi
import androidx.collection.LongSparseArray
import androidx.collection.SimpleArrayMap
import java.lang.reflect.Array

class ObjectUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object {

        /**
         * Return whether object is empty.
         *
         * @param obj The object.
         * @return `true`: yes<br></br>`false`: no
         */
        fun isEmpty(obj: Any?): Boolean {
            if (obj == null) {
                return true
            }
            if (obj.javaClass.isArray() && Array.getLength(obj) == 0) {
                return true
            }
            if (obj is CharSequence && obj.toString().length == 0) {
                return true
            }
            if (obj is Collection<*> && obj.isEmpty()) {
                return true
            }
            if (obj is Map<*, *> && obj.isEmpty()) {
                return true
            }
            if (obj is SimpleArrayMap<*, *> && obj.isEmpty) {
                return true
            }
            if (obj is SparseArray<*> && obj.size() == 0) {
                return true
            }
            if (obj is SparseBooleanArray && obj.size() == 0) {
                return true
            }
            if (obj is SparseIntArray && obj.size() == 0) {
                return true
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                if (obj is SparseLongArray && obj.size() == 0) {
                    return true
                }
            }
            if (obj is LongSparseArray<*> && obj.size() == 0) {
                return true
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                if (obj is android.util.LongSparseArray<*> && obj.size() == 0) {
                    return true
                }
            }
            return false
        }

        fun isEmpty(obj: CharSequence?): Boolean {
            return obj == null || obj.toString().length == 0
        }

        fun isEmpty(obj: Collection<*>?): Boolean {
            return obj == null || obj.isEmpty()
        }

        fun isEmpty(obj: Map<*, *>?): Boolean {
            return obj == null || obj.isEmpty()
        }

        fun isEmpty(obj: SimpleArrayMap<*, *>?): Boolean {
            return obj == null || obj.isEmpty
        }

        fun isEmpty(obj: SparseArray<*>?): Boolean {
            return obj == null || obj.size() == 0
        }

        fun isEmpty(obj: SparseBooleanArray?): Boolean {
            return obj == null || obj.size() == 0
        }

        fun isEmpty(obj: SparseIntArray?): Boolean {
            return obj == null || obj.size() == 0
        }

        fun isEmpty(obj: LongSparseArray<*>?): Boolean {
            return obj == null || obj.size() == 0
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        fun isEmpty(obj: SparseLongArray?): Boolean {
            return obj == null || obj.size() == 0
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        fun isEmpty(obj: android.util.LongSparseArray<*>?): Boolean {
            return obj == null || obj.size() == 0
        }

        /**
         * Return whether object is not empty.
         *
         * @param obj The object.
         * @return `true`: yes<br></br>`false`: no
         */
        fun isNotEmpty(obj: Any): Boolean {
            return !isEmpty(obj)
        }


        fun isNotEmpty(obj: CharSequence): Boolean {
            return !isEmpty(obj)
        }

        fun isNotEmpty(obj: Collection<*>): Boolean {
            return !isEmpty(obj)
        }

        fun isNotEmpty(obj: Map<*, *>): Boolean {
            return !isEmpty(obj)
        }

        fun isNotEmpty(obj: SimpleArrayMap<*, *>): Boolean {
            return !isEmpty(obj)
        }

        fun isNotEmpty(obj: SparseArray<*>): Boolean {
            return !isEmpty(obj)
        }

        fun isNotEmpty(obj: SparseBooleanArray): Boolean {
            return !isEmpty(obj)
        }

        fun isNotEmpty(obj: SparseIntArray): Boolean {
            return !isEmpty(obj)
        }

        fun isNotEmpty(obj: LongSparseArray<*>): Boolean {
            return !isEmpty(obj)
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        fun isNotEmpty(obj: SparseLongArray): Boolean {
            return !isEmpty(obj)
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        fun isNotEmpty(obj: android.util.LongSparseArray<*>): Boolean {
            return !isEmpty(obj)
        }

        /**
         * Return whether object1 is equals to object2.
         *
         * @param o1 The first object.
         * @param o2 The second object.
         * @return `true`: yes<br></br>`false`: no
         */
        fun equals(o1: Any?, o2: Any): Boolean {
            return o1 === o2 || o1 != null && o1 == o2
        }

        /**
         * Require the objects are not null.
         *
         * @param objects The object.
         * @throws NullPointerException if any object is null in objects
         */
        fun requireNonNull(vararg objects: Any) {
            for (`object` in objects) {
            }
        }

        /**
         * Return the nonnull object or default object.
         *
         * @param object        The object.
         * @param defaultObject The default object to use with the object is null.
         * @param <T>           The value type.
         * @return the nonnull object or default object
        </T> */
        fun <T> getOrDefault(`object`: T?, defaultObject: T): T {
            return `object` ?: defaultObject
        }

        /**
         * Return the hash code of object.
         *
         * @param o The object.
         * @return the hash code of object
         */
        fun hashCode(o: Any?): Int {
            return o?.hashCode() ?: 0
        }
    }
}
