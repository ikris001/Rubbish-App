package com.example.rubbishapp

data class User(
    val id:String,
    val username:String,
    val profilePictureAddress:String,
    val bio:String,
    val role:String,
    var score:Int) {

    constructor() : this("","","","","",0) {}
}
