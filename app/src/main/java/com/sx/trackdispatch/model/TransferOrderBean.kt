package com.sx.trackdispatch.model

import java.io.Serializable

class TransferOrderBean :Serializable{
    var sn = ""  //编号
    var dispatcher = "" //调度员
    var watchman = "" //值班员
    var captain = "" //列车长
    var train = "" //受令机车
    var station = "" //受令车站
    var driver = "" //司机
    var startArea = 0 //开始区域
    var endArea = 0 //结束区域
    var lineType = "" //线别
    var startTime = "" //开始时间
    var endTime = "" //结束时间
    var procedureInterval = 0//工序-区间
    var procedureInStation = 0 //工序-站内
    var procedureForkArea = 0 //工序-岔区
    var workContent = "" //工作内容
    var id = "" //工作内容
    var createTime = "" //创建时间
    var remark = "" //创建时间
    var status = ""//状态  await confirmed abolished finished
}