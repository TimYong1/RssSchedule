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
import com.example.common.utils.LogUtil
import com.sx.base.BaseFragment
import com.sx.trackdispatch.R
import com.sx.trackdispatch.databinding.FragmentTrackMapBinding
import com.sx.trackdispatch.dialog.ChatBoxDialog
import com.sx.trackdispatch.viewmodel.TrackMapViewModel
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
        myLocationStyle = MyLocationStyle()
        aMap.myLocationStyle = myLocationStyle
        // 定位、但不会移动到地图中心点，并且会跟随设备移动。
        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER))
    }

    private fun initLine(){
        var latitude = 28.213675
        var longitude = 112.891641
        //四个点
        val A = LatLng(latitude , longitude)
        val B = LatLng(latitude + 1, longitude + 1)
        val C = LatLng(latitude + 2, longitude + 2)
        val D = LatLng(latitude + 3, longitude + 3)
        //用一个数组来存放纹理
        val texTuresList: MutableList<BitmapDescriptor> = ArrayList()
        texTuresList.add(BitmapDescriptorFactory.fromResource(R.mipmap.map_alr))
        //指定某一段用某个纹理，对应texTuresList的index即可, 四个点对应三段颜色
        val texIndexList: MutableList<Int> = ArrayList()
        texIndexList.add(0) //对应上面的第0个纹理
        val options = PolylineOptions()
        options.width(20f) //设置宽度
        //加入四个点
        options.add(A, B, C, D)
        //加入对应的颜色,使用setCustomTextureList 即表示使用多纹理；
        options.customTextureList = texTuresList
        //设置纹理对应的Index
        options.customTextureIndex = texIndexList
        aMap.addPolyline(options)
    }

    //定位
    private fun initLoc() {
        //初始化定位
        mLocationClient = AMapLocationClient(this.activity?.application)
        //设置定位回调监听
        mLocationClient.setLocationListener(this)
        //初始化定位参数
        mLocationOption = AMapLocationClientOption()
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
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
        //启动定位
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

        }
    }

    override fun onLocationChanged(location: AMapLocation) {
        var latlng = LatLng(location.latitude,location.longitude)
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latlng))
//        aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
//        LogUtil.d("经纬度变化-->latitude:"+location?.latitude+"  longitude:"+location?.longitude)
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
        binding.mapView.onDestroy()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_track_map
    }
}