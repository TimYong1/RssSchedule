package com.sx.trackdispatch.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import com.example.common.utils.LogUtil
import com.sx.base.BaseFragment
import com.sx.base.utils.FileUtils
import com.sx.trackdispatch.R
import com.sx.trackdispatch.adapter.TransferOrderAdapter
import com.sx.trackdispatch.databinding.FragmentTransferOrderBinding
import com.sx.trackdispatch.dialog.TransferOrderConfirmDialog
import com.sx.trackdispatch.viewmodel.TransferOrderViewModel
import com.xdf.tts.SpeechUtils
import droidninja.filepicker.FilePickerConst


class TransferOrderFragment : BaseFragment<FragmentTransferOrderBinding, TransferOrderViewModel>() {
    private lateinit var confirmDialog:TransferOrderConfirmDialog
    private val ActivityResult_CODE = 1001

    override fun getLayoutId(): Int {
        return R.layout.fragment_transfer_order
    }

    override fun init(savedInstanceState: Bundle?) {
        binding.vm = mViewModel
        binding.adapter = TransferOrderAdapter(this.context,itemClick)
        binding.settingClick = settingClick
        SpeechUtils.init(this.activity?.application!!)

//        val checkIntent = Intent()
//        checkIntent.action = TextToSpeech.Engine.ACTION_CHECK_TTS_DATA
//        startActivityForResult(checkIntent, ActivityResult_CODE)
//        val installIntent = Intent()
//        installIntent.action = TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA
//        startActivity(installIntent)
    }

    private val itemClick = object :TransferOrderAdapter.ItemClickListener{

        override fun confirm(bean: String) {
            showDialog()
        }

        override fun reject() {
        }

        override fun execute() {
        }

        override fun speak(content:String) {
            SpeechUtils.speakText("说话测试")
        }
    }

    private val settingClick = View.OnClickListener { startActivity(Intent(this.activity,SettingActivity::class.java)) }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            FilePickerConst.REQUEST_CODE_DOC -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    try {
                        val dataList = data.getParcelableArrayListExtra<Uri>(FilePickerConst.KEY_SELECTED_DOCS)
                        if(dataList!=null){
                            val fileUrls = ArrayList<Uri>()
                            val fileList = mutableListOf<String>()
                            fileUrls?.addAll(dataList)
                            fileList.addAll(confirmDialog.getViewModel().fileList.value!!)
                            dataList.forEach {
                                fileList.add(fileList.size-1,FileUtils.getPathFromUri(this.context!!,it)!!)
                            }
                            if(fileList.size-1==confirmDialog.getViewModel().MAX_FILE_SIZE.value!!){
                                fileList.removeAt(fileList.size-1)
                            }
                            confirmDialog.getViewModel().fileList.value = fileList
                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                        LogUtil.d("解析文件路径失败")
                    }
                }
            }
            ActivityResult_CODE -> {
                val installIntent = Intent()
                installIntent.action = TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA
                startActivity(installIntent)
            }
        }
    }

    fun showDialog(){
        if(!this::confirmDialog.isInitialized){
            confirmDialog = TransferOrderConfirmDialog.Builder.create(this.activity!!,this)
        }
        confirmDialog.show()
    }
}