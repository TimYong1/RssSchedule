package com.sx.trackdispatch.mina.bindsocket

import android.util.Log
import com.armslocker.util.BaseConfig
import com.example.common.Constants
import com.example.mina.common.FrameCodecFactory
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import org.apache.mina.core.service.IoAcceptor
import org.apache.mina.core.session.IdleStatus
import org.apache.mina.filter.codec.ProtocolCodecFilter
import org.apache.mina.transport.socket.nio.NioSocketAcceptor
import java.net.InetSocketAddress
import java.util.concurrent.TimeUnit

class BindSocketPort private constructor(){
    private var mDisposable: Disposable? = null
    private var acceptor: IoAcceptor? = null
    private val TAG = "ServiceNet"
    private var isLoop = true

    companion object {
        @Volatile private var instance: BindSocketPort? = null
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: BindSocketPort().also { instance = it }
        }
    }

    init {
        acceptor = NioSocketAcceptor()
        acceptor?.setHandler(ServiceHandlerAdapter())
        acceptor?.getFilterChain()
            ?.addLast("encoder", ProtocolCodecFilter(FrameCodecFactory()))
        acceptor?.getFilterChain()
            ?.addLast("decoder", ProtocolCodecFilter(FrameCodecFactory()))
        acceptor?.getSessionConfig()?.readBufferSize = 2048 * 100
        acceptor?.getSessionConfig()?.setIdleTime(IdleStatus.BOTH_IDLE, 30)
    }

    fun bindPort(){
        if(mDisposable!=null){
            return
        }
        mDisposable = Observable.interval(0, BaseConfig.rePeatServiceTime, TimeUnit.SECONDS)
            .takeWhile {
             return@takeWhile isLoop
            }
            .doOnNext {
                try {
                    acceptor?.bind(InetSocketAddress(Constants.CHAT_PORT))
                    // 绑定端口
                    Log.d(TAG, "服务器启动成功... 端口号：" + Constants.CHAT_PORT)
                    isLoop = false
//                    Schedulers.shutdown()
                } catch (ex: Exception) {
                    Log.d(TAG, "服务器启动异常...$ex")
                }
            }.subscribe {

            }
    }

    fun unBind(){
        acceptor?.unbind()
        mDisposable?.dispose()
        mDisposable = null
    }
}