package com.sx.trackdispatch.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import com.example.common.utils.LogUtil
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.sx.base.BaseFragment
import com.sx.base.net.HttpBaseObserver
import com.sx.base.net.HttpInterceptor
import com.sx.base.net.HttpUtil
import com.sx.base.utils.FileUtils
import com.sx.trackdispatch.R
import com.sx.trackdispatch.adapter.TransferOrderAdapter
import com.sx.trackdispatch.api.Api
import com.sx.trackdispatch.databinding.FragmentTransferOrderBinding
import com.sx.trackdispatch.dialog.LoadingDialog
import com.sx.trackdispatch.dialog.TransferOrderConfirmDialog
import com.sx.trackdispatch.model.ResponseBaseData
import com.sx.trackdispatch.model.TransferOrderBean
import com.sx.trackdispatch.viewmodel.TransferOrderViewModel
import com.util.toast.ToastUtils
import com.xdf.tts.TTSUtil
import droidninja.filepicker.FilePickerConst
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class TransferOrderFragment : BaseFragment<FragmentTransferOrderBinding, TransferOrderViewModel>(),
    OnLoadMoreListener, OnRefreshListener {
    private lateinit var confirmDialog:TransferOrderConfirmDialog
    private val ActivityResult_CODE = 1001
    private lateinit var api: Api
    private lateinit var loadingDialog: LoadingDialog

    override fun getLayoutId(): Int {
        return R.layout.fragment_transfer_order
    }

    override fun init(savedInstanceState: Bundle?) {
        binding.vm = mViewModel
        binding.adapter = TransferOrderAdapter(this.context,itemClick)
        binding.settingClick = settingClick
        //初始化引擎
        TTSUtil.getInstance(this.activity?.application)

        api = HttpUtil.getRetrofit().create(Api::class.java)
        loadingDialog = LoadingDialog.Builder().create(this.activity!!)
        mViewModel.page.value = 0
        mViewModel.list.value = mutableListOf()
        getListData()
        initListener()
    }

    private fun initListener() {
        binding.refreshView.setOnRefreshListener(this)
        binding.refreshView.setOnLoadMoreListener(this)
    }

    fun getListData(){
//        loadingDialog.show()
        api.getProjectDispatch(mViewModel.page.value!!,10).subscribeOn(Schedulers.io()) // 上面 异步
            .observeOn(AndroidSchedulers.mainThread()) // 下面 主线程
            .subscribe (object : HttpBaseObserver<ResponseBaseData<ArrayList<TransferOrderBean>>>(){
                override fun succeed(t: ResponseBaseData<ArrayList<TransferOrderBean>>?) {
                    LogUtil.d("网络请求返回了"+t.toString())
                    binding.refreshView.finishRefresh()
                    binding.refreshView.finishLoadMore()
                    val list = mViewModel.list.value
                    list?.addAll(t?.data!!)
                    if(t?.code==200){
                        mViewModel.list.value = list
                        if(t.count.toInt()>mViewModel.list.value!!.size){
                            binding.refreshView.setEnableLoadMore(true)
                        }else{
                            binding.refreshView.setEnableLoadMore(false)
                        }
                    }
                }

                override fun failed(e: Throwable?) {
                    LogUtil.d("网络请求失败：${e?.message}")
                    binding.refreshView.finishRefresh()
                    binding.refreshView.finishLoadMore()
//                    loadingDialog.dismiss()
                }
            })
    }

    private val itemClick = object :TransferOrderAdapter.ItemClickListener{

        override fun confirm(bean: TransferOrderBean) {
            showDialog(bean)
        }

        override fun reject(bean: TransferOrderBean) {
        }

        override fun execute() {
        }

        override fun speak(content:TransferOrderBean) {
//            SpeechUtils.speakText(content.workContent)
            TTSUtil.getInstance(this@TransferOrderFragment.activity?.application).play(content.workContent)
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

    fun showDialog(bean:TransferOrderBean){
        if(!this::confirmDialog.isInitialized){
            confirmDialog = TransferOrderConfirmDialog.Builder.create(this.activity!!,this)
            confirmDialog.setDialogClick(object :TransferOrderConfirmDialog.DialogClick{
                override fun click(bean: TransferOrderBean) {
                    loadingDialog.show()
//                    val createTime = SimpleDateFormatUtils.getDataByFormatString(bean.createTime,SimpleDateFormatUtils.YYYY_MM_DD_HH_MM_SS)
                    val request = HashMap<String,String>()
                    request.put("status",bean.status)
                    request.put("id",bean.id)
                    request.put("remark",bean.remark)
                    api.putProjectDispatch(HttpInterceptor.initFormBodyBuilder(request).build()).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe (object : HttpBaseObserver<ResponseBaseData<String>>(){
                            override fun succeed(t: ResponseBaseData<String>?) {
                                LogUtil.d("网络请求返回了"+t?.msg)
                                loadingDialog.dismiss()
                                if(t?.code==200){
                                    LogUtil.d("网络请求操作成功")
                                }else{
                                    ToastUtils.show(t?.msg)
                                }
                                mViewModel.page.value = 0
                                getListData()
                            }

                            override fun failed(e: Throwable?) {
                                LogUtil.d("网络请求失败：${e?.message}")
                                loadingDialog.dismiss()
                            }
                        })
                }
            })
        }
        confirmDialog.showDialog(bean)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mViewModel.page.value = mViewModel.page.value!!+1
        getListData()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mViewModel.page.value = 0
        mViewModel.list.value = mutableListOf()
        getListData()

    }
}