package com.sx.trackdispatch.model

class LoginToken {
    var token = ""
    var tokenType = ""
    var refreshToken = ""
    var additionalInformation:AdditionalInformation? = null
    var expire = 0

    class AdditionalInformation{
        var im:Im? = null

        class Im{
             var code = 0
             var message = ""
             var result:Result? = null

            class Result{
                var userId=""
                var token=""
                var register=false
            }
        }
    }
}