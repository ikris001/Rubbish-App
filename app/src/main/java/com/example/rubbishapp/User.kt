package com.example.rubbishapp

class User(_userId:Int, _username:String, _password:String, _email:String,
           _profilePictureAddress:String, _bio:String, _role:String, _score:Int)
{
    // Declaration of all variable and their getter and setter methods
    private var userId:Int
        get() = this.userId
        set(value) {
            this.userId = value
        }

    private var username:String
        get() = this.username
        set(value) {
            this.username = value
        }

    private var password:String
        get() = this.password
        set(value) {
            this.password = value
        }

    private var email:String
        get() = this.email
        set(value) {
            this.email = value
        }

    private var profilePictureAddress:String
        get() = this.profilePictureAddress
        set(value) {
            this.profilePictureAddress = value
        }

    private var bio:String
        get() = this.bio
        set(value) {
            this.bio = value
        }

    private var role:String
        get() = this.role
        set(value) {
            this.role = value
        }

    private var score:Int
        get() = this.score
        set(value) {
            this.score = value
        }

    // Initializer
    init {
        this.userId = _userId
        this.username = _username
        this.password = _password
        this.email = _email
        this.profilePictureAddress = _profilePictureAddress
        this.bio = _bio
        this.role = _role
        this.score = _score
    }


}