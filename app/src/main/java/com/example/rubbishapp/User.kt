package com.example.rubbishapp

data class User(
    val id:Int,
    val username:String,
    val password:String,
    val email:String,
    val profilePictureAddress:String,
    val bio:String,
    val role:String,
    val score:Int)
