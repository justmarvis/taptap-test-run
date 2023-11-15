package com.ambasolutions.taptap.modal

class Message {

    var imageUrl: String? = null
    var message: String? = null
    var senderId: String? = null
    constructor(){}

    constructor(message: String?, senderId: String?) {
        this.imageUrl = imageUrl
        this.message = message
        this.senderId = senderId
    }
}