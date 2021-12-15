package com.example.rubbishapp

class User(_id:Int, _username:String, _password:String, _email:String,
           _profilePictureAddress:String, _bio:String, _role:String, _score:Int)
{
    // Declaration of all variable and their getter and setter methods
    private var id:Int
        get() = this.id
        set(newId) {
            this.id = newId
        }

    private var username:String
        get() = this.username
        set(newUsername) {
            this.username = newUsername
        }

    private var password:String
        get() = this.password
        set(newPassword) {
            this.password = newPassword
        }

    private var email:String
        get() = this.email
        set(newEmail) {
            this.email = newEmail
        }

    private var profilePictureAddress:String
        get() = this.profilePictureAddress
        set(newProfilePictureAddress) {
            this.profilePictureAddress = newProfilePictureAddress
        }

    private var bio:String
        get() = this.bio
        set(newBio) {
            this.bio = newBio
        }

    private var role:String
        get() = this.role
        set(newRole) {
            this.role = newRole
        }

    private var score:Int
        get() = this.score
        set(newScore) {
            this.score = newScore
        }

    // Initializer
    init {
        this.id = _id
        this.username = _username
        this.password = _password
        this.email = _email
        this.profilePictureAddress = _profilePictureAddress
        this.bio = _bio
        this.role = _role
        this.score = _score
    }


}