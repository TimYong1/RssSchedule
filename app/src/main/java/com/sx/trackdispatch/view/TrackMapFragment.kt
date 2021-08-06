package com.sx.trackdispatch.view

import android.content.Intent
import android.os.Bundle
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.LocationSource.OnLocationChangedListener
import com.amap.api.maps.UiSettings
import com.amap.api.maps.model.*
import com.sx.base.BaseFragment
import com.sx.trackdispatch.R
import com.sx.trackdispatch.databinding.FragmentTrackMapBinding
import com.sx.trackdispatch.dialog.ChatBoxDialog
import com.sx.trackdispatch.viewmodel.TrackMapViewModel
import java.lang.Exception
import java.util.*


class TrackMapFragment : BaseFragment<FragmentTrackMapBinding,TrackMapViewModel>(),AMapLocationListener {
    private lateinit var myLocationStyle: MyLocationStyle
    private lateinit var aMap: AMap
    private lateinit var uiSetting: UiSettings
    //定位需要的声明
    private lateinit var mLocationClient: AMapLocationClient //定位发起端
    private lateinit var mLocationOption: AMapLocationClientOption //定位参数
    private lateinit var mListener: OnLocationChangedListener //定位监听器
    private lateinit var chatBoxDialog:ChatBoxDialog

    override fun init(savedInstanceState: Bundle?) {
        binding.mapView.onCreate(savedInstanceState)
        binding.click = ClickProxy()
        initMap()
        initLoc()
        initLine()
    }

    fun initMap(){
        aMap = binding.mapView.map
        uiSetting = aMap.uiSettings
        uiSetting.setMyLocationButtonEnabled(false) // 是否显示默认的定位按钮
        aMap.setMyLocationEnabled(true)
//        aMap.
//        mViewModel.lines.value.javaClass.
//        var angle = 10F
//        aMap.setOnMapClickListener {
//            angle+=10
//            aMap.setMyLocationRotateAngle(angle)
//            aMap.moveCamera(CameraUpdateFactory.changeBearing(angle))
//        }
//
//        aMap.setOnMapClickListener {
//            aMap.setMyLocationRotateAngle(mineAngle.toFloat())
//            aMap.moveCamera(CameraUpdateFactory.changeBearing(mineAngle.toFloat()))
//        }

        myLocationStyle = MyLocationStyle()
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.gps_point))
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.track_map_select))
        // 自定义精度范围的圆形边框颜色
//        myLocationStyle.strokeColor(STROKE_COLOR)
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5F)
        // 设置圆形的填充颜色0
//        myLocationStyle.radiusFillColor(FILL_COLOR)
        aMap.myLocationStyle = myLocationStyle
        //追随
        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE))

        aMap.moveCamera(CameraUpdateFactory.changeLatLng(mViewModel.lines.value?.get(0)))
    }

    private fun initLine(){
        //用一个数组来存放纹理
        val texTuresList: MutableList<BitmapDescriptor> = ArrayList()
        texTuresList.add(BitmapDescriptorFactory.fromResource(R.mipmap.map_alr))
        //指定某一段用某个纹理，对应texTuresList的index即可, 四个点对应三段颜色
        val texIndexList: MutableList<Int> = ArrayList()
        texIndexList.add(0) //对应上面的第0个纹理
        val options = PolylineOptions()
        options.width(20f) //设置宽度
        //加入四个点
        mViewModel.lines.value?.forEach {
            options.add(it)
        }
        //加入对应的颜色,使用setCustomTextureList 即表示使用多纹理；
        options.customTextureList = texTuresList
        //设置纹理对应的Index
        options.customTextureIndex = texIndexList
        aMap.addPolyline(options)
    }

    private fun initLoc() {
        mLocationClient = AMapLocationClient(this.activity?.application)
        mLocationClient.setLocationListener(this)
        mLocationOption = AMapLocationClientOption()
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        // 可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mLocationOption.isGpsFirst = true
        // 可选，设置是否使用传感器。默认是false
        mLocationOption.isSensorEnable = true
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.isNeedAddress = true
        //设置是否只定位一次,默认为false
        mLocationOption.isOnceLocation = false
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.isWifiActiveScan = true
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.isMockEnable = false
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.interval = 2000
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption)
        mLocationClient.startLocation()
    }

    inner class ClickProxy {
        fun messageClick() {
            if(!this@TrackMapFragment::chatBoxDialog.isInitialized){
                chatBoxDialog = ChatBoxDialog.Builder.create(this@TrackMapFragment.activity!!,fragmentManager!!,this@TrackMapFragment)
            }
            chatBoxDialog.show()
        }
        fun settingClick(){
            startActivity(Intent(this@TrackMapFragment.context,SettingActivity::class.java))
        }
    }

    override fun onLocationChanged(location: AMapLocation) {
        var latlng = LatLng(location.latitude,location.longitude)
//        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latlng))
//        val latlnga = mViewModel.lines.value?.get(0)
//        val latlngb = mViewModel.lines.value?.get(1)
//        val mineAngle = BitMapUtils.TwoCoordinateAzimuth(latlnga?.latitude!!,latlnga.longitude,latlngb?.latitude!!,latlngb.longitude)
//        LogUtil.d(mineAngle.toString())
//        aMap.setMyLocationRotateAngle(mineAngle.toFloat())
//        aMap.moveCamera(CameraUpdateFactory.changeBearing(mineAngle.toFloat()+45))
//        aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
//        LogUtil.d("经纬度变化-->latitude:"+location?.latitude+"  longitude:"+location?.longitude)
//        LogUtil.d("速度-->"+location.speed)
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            binding.mapView.onDestroy()
        }catch (e:Exception){

        }

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_track_map
    }
}