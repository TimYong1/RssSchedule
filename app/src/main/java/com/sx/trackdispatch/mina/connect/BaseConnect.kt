package com.sx.trackdispatch.mina.connect

import android.text.TextUtils
import android.util.Log
import com.armslocker.util.BaseConfig
import com.example.common.utils.LogUtil
import com.example.common.utils.MainHandler
import com.example.mina.common.FrameCodecFactory
import com.example.mina.common.SessioManage
import com.sx.armslockermvvm.mina.request.RequestManage
import com.sx.trackdispatch.viewmodel.MainViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import org.apache.mina.core.service.IoHandlerAdapter
import org.apache.mina.core.service.IoServiceListener
import org.apache.mina.core.session.IoSession
import org.apache.mina.filter.codec.ProtocolCodecFilter
import org.apache.mina.filter.keepalive.KeepAliveFilter
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory
import org.apache.mina.transport.socket.nio.NioSocketConnector
import java.lang.RuntimeException
import java.net.InetSocketAddress
import java.util.concurrent.TimeUnit

abstract class BaseConnect{
    private var mDisposable: Disposable? = null
    private var mSocketAddress: InetSocketAddress? = null
    private var mSocketConnector: NioSocketConnector? = null
    private var session: IoSession? = null
    private var ip: String = ""
    private var port: Int = 0
    private var ioServiceListener: IoServiceListener? = null
    private var isLoop = true

    init {
        mSocketConnector = NioSocketConnector()
        mSocketConnector!!.filterChain.addLast("protocol", ProtocolCodecFilter(FrameCodecFactory()))
        mSocketConnector!!.connectTimeoutMillis = 5000
        mSocketConnector!!.sessionConfig.readBufferSize = 1024 * 1024
        val heartFilter = KeepAliveFilter(getKeepAliveFilter())
        heartFilter.requestInterval = 11
        heartFilter.requestTimeout = 10
        val filterChain = mSocketConnector!!.filterChain
        filterChain.addLast("encoder", ProtocolCodecFilter(FrameCodecFactory()))
        filterChain.addLast("decoder", ProtocolCodecFilter(FrameCodecFactory()))
        mSocketConnector!!.handler = getIoHandlerAdapter()
//        mSocketConnector!!.addListener(ServiceListener(mSocketConnector))
        mSocketConnector!!.filterChain.addLast("heartbeat", heartFilter)
    }

    fun checkDataStatus(ip:String,port:Int, listener: ConnectListener):Boolean{
        if(TextUtils.isEmpty(ip)||port<0||port>65535){
            Log.e("---异常---","ip为空或者端口错误")
            return false
        }
        //ip 或者 port 改变重新创建InetSocketAddress
        if(!ip.equals(this.ip)||port!=this.port){
            mSocketAddress = InetSocketAddress(ip, port)
            this.ip = ip
            this.port = port
            if(ioServiceListener==null){
                ioServiceListener = getIoServiceListener(listener)
                mSocketConnector!!.addListener(ioServiceListener)
            }
            return true
        }else{
            if(session!=null&&session!!.isActive){
                return false
            }
        }
        return true
    }

    fun connect(ip:String,port:Int,listener: ConnectListener){
        if(!checkDataStatus(ip,port,listener)){
            return
        }
        if(session!=null){
            LogUtil.d("状态：${session!!.isActive} ${session!!.isClosing}  ${session!!.isConnected}")
        }
        if(mDisposable!=null){
            LogUtil.d("mDisposable 不为null")
            return
        }
        mDisposable = Observable.interval(0, BaseConfig.rePeatConnectTime, TimeUnit.SECONDS)
            .takeWhile {
                return@takeWhile isLoop
            }
            .doOnNext {
                try {
                    LogUtil.d("开始连接：" + ip + "  " + port)
                    //发起连接
                    val mFuture = mSocketConnector!!.connect(mSocketAddress)
                    mFuture.awaitUninterruptibly()
                    mFuture?.isConnected
                    session = mFuture.session
                    initSession()
                    MainHandler.instance.getHandler().post {
                        listener.onLine()
                    }
                    isLoop = false
                    mDisposable?.dispose()
                    mDisposable = null
                    isLoop = true
                    LogUtil.d("======连接成功${session?.id}")
                } catch (ex: RuntimeException) {
                    ex.printStackTrace()
                    LogUtil.d("connect", "连接失败：" + ip + "  " + port)
                }
            }.subscribe{}
    }


    fun closeSession(){
        LogUtil.d("主动销毁连接 $ip $port")
        session?.closeFuture?.setClosed()
        mSocketConnector?.dispose()
        sessionClosed()
        ip = ""
        port = 0
        mDisposable?.dispose()
        mDisposable = null
    }

    fun initSession(){
        SessioManage.instance.hostSession = session
    }

    abstract fun getIoHandlerAdapter(): IoHandlerAdapter

    abstract fun getKeepAliveFilter(): KeepAliveMessageFactory

    abstract fun getIoServiceListener(listener: ConnectListener): IoServiceListener

    abstract fun sessionClosed()

    fun getSession():IoSession?{
        return session
    }
}