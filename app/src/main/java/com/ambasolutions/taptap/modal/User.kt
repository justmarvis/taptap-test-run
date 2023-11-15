package com.ambasolutions.taptap.modal

//data class User (
//    var userID: String = "",
//    var userName: String = "",
//    var userImage: String = ""
//)

class User {
    var imageUrl: String? = null
    var email: String? = null
    var firstname: String? = null
    var lastname: String? = null
    var dob: String? = null
    var country: String? = null
    var username: String? = null
    var password: String? = null
    var uid: String? = null

    var recentChatMessages: String? = null

    constructor(){}

    constructor(imageUrl: String?, email: String?, firstname: String?, lastname: String?, dob: String?, country: String?, username: String?, password: String?, uid: String?, recentChatMessages: String?) {
        this.imageUrl = imageUrl
        this.email = email
        this.firstname = firstname
        this.lastname = lastname
        this.dob = dob
        this.country = country
        this.username = username
        this.password = password
        this.uid = uid

        this.recentChatMessages = recentChatMessages
    }

}