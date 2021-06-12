package com.sx.base.utils

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.TextUtils
import java.io.File

object FileUtils {
    fun getName(path:String):String{
        if(TextUtils.isEmpty(path)){
            return ""
        }
        val strings =  path.split("/")
        return strings.get(strings.size-1)
    }

    fun getPathFromUri(context: Context, uri: Uri): String? {
        var path: String? = null
        if (DocumentsContract.isDocumentUri(context, uri)) {
            //如果是document类型的Uri，通过document id处理，内部会调用Uri.decode(docId)进行解码
            val docId: String = DocumentsContract.getDocumentId(uri)
            //primary:Azbtrace.txt
            //video:A1283522
            val splits = docId.split(":".toRegex()).toTypedArray()
            var type: String? = null
            var id: String? = null
            if (splits.size == 2) {
                type = splits[0]
                id = splits[1]
            }
            when (uri.getAuthority()) {
                "com.android.externalstorage.documents" -> if ("primary" == type) {
                    path = Environment.getExternalStorageDirectory().path + File.separator.toString() + id
                }
                "com.android.providers.downloads.documents" -> path = if ("raw" == type) {
                    id
                } else {
                    val contentUri: Uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        java.lang.Long.valueOf(docId)
                    )
                    getMediaPathFromUri(context, contentUri, null, null)
                }
                "com.android.providers.media.documents" -> {
                    var externalUri: Uri? = null
                    when (type) {
                        "image" -> externalUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        "video" -> externalUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                        "audio" -> externalUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                    if (externalUri != null) {
                        val selection = "_id=?"
                        val selectionArgs =
                            arrayOf(id)
                        path = getMediaPathFromUri(context, externalUri, selection, selectionArgs)
                    }
                }
            }
        } else if (ContentResolver.SCHEME_CONTENT.endsWith(uri.getScheme()!!,true)) {
            path = getMediaPathFromUri(context, uri, null, null)
        } else if (ContentResolver.SCHEME_FILE.endsWith(uri.getScheme()!!,true)) {
            //如果是file类型的Uri(uri.fromFile)，直接获取图片路径即可
            path = uri.getPath()
        }
        //确保如果返回路径，则路径合法
        return if (path == null) null else if (File(path).exists()) path else null
    }

    private fun getMediaPathFromUri(
        context: Context,
        uri: Uri,
        selection: String?,
        selectionArgs: Array<String?>?
    ): String? {
        var path: String?
        val authroity: String = uri.getAuthority()!!
        path = uri.getPath()
        val sdPath: String = Environment.getExternalStorageDirectory().getAbsolutePath()
        if (!path?.startsWith(sdPath)!!) {
            val sepIndex: Int = path?.indexOf(File.separator, 1)!!
            path = if (sepIndex == -1) null else {
                sdPath.toString() + path.substring(sepIndex)
            }
        }
        if (path == null || !File(path).exists()) {
            val resolver: ContentResolver = context.getContentResolver()
            val projection =
                arrayOf<String>(MediaStore.MediaColumns.DATA)
            val cursor: Cursor = resolver.query(uri, projection, selection, selectionArgs, null)!!
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    try {
                        val index: Int = cursor.getColumnIndexOrThrow(projection[0])
                        if (index != -1) path = cursor.getString(index)
                    } catch (e: IllegalArgumentException) {
                        e.printStackTrace()
                        path = null
                    } finally {
                        cursor.close()
                    }
                }
            }
        }
        return path
    }

}