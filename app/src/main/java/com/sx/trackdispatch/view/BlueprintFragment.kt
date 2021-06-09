package com.sx.trackdispatch.view

import android.graphics.Color
import android.os.Bundle
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
    val colors = mutableListOf(Color.argb(50,50,50,50),Color.argb(50,100,100,100),Color.argb(50,63,200,50))
    override fun getLayoutId(): Int {
        return R.layout.fragment_blueprint
    }

    override fun init(savedInstanceState: Bundle?) {
        initChart()
    }

    private fun initXYAxis(){
        val xAxis = binding.chartLine.xAxis
        xAxis.enableGridDashedLine(1f, 0.5f, 0f)
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setAxisMinimum(0F)

        val yAxis = binding.chartLine.getAxisLeft()
        binding.chartLine.getAxisRight().setEnabled(false)
        yAxis.enableGridDashedLine(1f, 0.5f, 0f)
        yAxis.setAxisMaximum(10f)
        yAxis.setAxisMinimum(0f)
        yAxis.setDrawGridLines(false)
    }

    private fun initChart(){
        initXYAxis()
        binding.chartLine.apply {
            setBackgroundColor(Color.WHITE);
            // disable description text
            getDescription().setEnabled(false);
            // enable touch gestures
            setTouchEnabled(true);
            // set listeners
            setDrawGridBackground(false);
            // enable scaling and dragging
            setDragEnabled(true);
            setScaleEnabled(false);
            // force pinch zoom along both axis
            setPinchZoom(true);
        }
        val l: Legend = binding.chartLine.getLegend()
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        updateLineChart()
    }

    private fun updateLineChart(){
        binding.chartLine.resetTracking()
        mViewModel.datasets.value?.clear()
        mViewModel.lineData.value?.forEachIndexed {index, mutableList ->
            val d = LineDataSet(mutableList,"number1")
            d.setLineWidth(2.5f)
            d.setCircleRadius(4f)
            d.setDrawCircles(true)
            d.mode = LineDataSet.Mode.CUBIC_BEZIER
            val color = colors[index % colors.size]
            d.setColor(color)
            d.setCircleColor(color)
            mViewModel.datasets.value?.add(d)
        }
        val data = LineData(mViewModel.datasets.value)
        binding.chartLine.setData(data)
        binding.chartLine.animateXY(1000,1000)
//        binding.chartLine.invalidate()
    }
}