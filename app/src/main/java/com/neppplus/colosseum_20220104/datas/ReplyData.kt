package com.neppplus.colosseum_20220104.datas

import org.json.JSONObject

class ReplyData {

    var id = 0
    var content = ""

    var writer = UserData() //사용자 데이터가 들어올 거라고 명시.

    companion object {

        fun getReplyDataFromJson(jsonObj: JSONObject): ReplyData {

            val replyData = ReplyData()

            replyData.id = jsonObj.getInt("id")
            replyData.content = jsonObj.getString("content")


            val userObj = jsonObj.getJSONObject("user")
            replyData.writer = UserData.getUserDataFromServer(userObj)

            return replyData
        }

    }

}