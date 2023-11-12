package com.ambasolutions.taptap

//data class User (
//    var userID: String = "",
//    var userName: String = "",
//    var userImage: String = ""
//)

class User {
    var email: String? = null
    var firstname: String? = null
    var lastname: String? = null
    var dob: String? = null
    var country: String? = null
    var username: String? = null
    var password: String? = null
    var uid: String? = null

    constructor(){}

    constructor( email: String?, firstname: String?, lastname: String?, dob: String?, country: String?, username: String?, password: String?, uid: String?) {
        this.email = email
        this.firstname = firstname
        this.lastname = lastname
        this.dob = dob
        this.country = country
        this.username = username
        this.password = password
        this.uid = uid
    }

}