package com.sx.trackdispatch.model

class VideoDevice {
    var name = ""
    var ip = ""
    var port = ""
    var userName = ""
    var password = ""
    var url = ""

    constructor(name: String, ip: String, port: String, userName: String, password: String) {
        this.name = name
        this.ip = ip
        this.port = port
        this.userName = userName
        this.password = password
    }
}