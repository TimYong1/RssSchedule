package com.sx.trackdispatch.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.sx.base.BaseFragment
import com.sx.trackdispatch.R
import com.sx.trackdispatch.databinding.FragmentBlueprintBinding
import com.sx.trackdispatch.viewmodel.BlueprintViewModel

class BlueprintFragment: BaseFragment<FragmentBlueprintBinding, BlueprintViewModel>() {
    val colors = mutableListOf(Color.argb(255,162,84,227),Color.argb(255,255,169,94),Color.argb(50,63,200,50))
    override fun getLayoutId(): Int {
        return R.layout.fragment_blueprint
    }

    override fun init(savedInstanceState: Bundle?) {
        binding.vm = mViewModel
        binding.settingClick = settingClick
        initChart()
    }

    private val settingClick = View.OnClickListener { startActivity(Intent(this.activity,SettingActivity::class.java)) }

    private fun initXYAxis(){
        val xAxis = binding.chartLine.xAxis
        xAxis.enableGridDashedLine(1f, 1f, 0f)
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
//        xAxis.setAxisMinimum(0F)

        val yAxis = binding.chartLine.getAxisLeft()
        binding.chartLine.getAxisRight().setEnabled(false)
        yAxis.enableGridDashedLine(1f, 0.5f, 0f)
        yAxis.setAxisMaximum(120f)
        yAxis.setAxisMinimum(0f)
        yAxis.setDrawGridLines(false)
    }

    private fun initChart(){
        initXYAxis()
        binding.chartLine.apply {
            setBackgroundColor(Color.WHITE)
            getDescription().setEnabled(false)
            setTouchEnabled(true)
            setDrawGridBackground(false)
            setDragEnabled(true)
            setScaleEnabled(true)
            setPinchZoom(true)
        }
        val l: Legend = binding.chartLine.getLegend()
        l.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        updateLineChart()
    }

    private fun updateLineChart(){
        binding.chartLine.resetTracking()
        mViewModel.datasets.value?.clear()
        mViewModel.lineData.value?.forEachIndexed {index, mutableList ->
            var lable = ""
            if(index==0){
                lable = "司机：赵志龙"
            }else{
                lable = "司机：张明"
            }
            val d = LineDataSet(mutableList,lable)
            d.setLineWidth(1.5f)
            d.setCircleRadius(4f)
            d.setDrawCircles(true)
            d.setDrawCircleHole(false)
            d.setDrawValues(false)
            d.mode = LineDataSet.Mode.CUBIC_BEZIER
            val color = colors[index % colors.size]
            d.setColor(color)
            d.setCircleColor(color)
            mViewModel.datasets.value?.add(d)
        }
        val data = LineData(mViewModel.datasets.value)
        binding.chartLine.setData(data)
        binding.chartLine.animateXY(3000,3000)
//        binding.chartLine.invalidate()
    }
}