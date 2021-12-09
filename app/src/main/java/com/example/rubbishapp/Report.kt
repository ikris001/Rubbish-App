package com.example.rubbishapp

import java.net.UnknownServiceException

class Report constructor(_location:String, _description:String, _user:User, _level:String,
                         _pictureSource:String, _cleaned:Boolean){

    private var location: String
    private var description: String
    private var user: User
    private var level: String
    private var pictureAddress: String
    private var cleaned: Boolean

    // Initialises all attributes
    init{
        this.location = _location
        this.description = _description
        this.user = _user
        this.level = _level
        this.pictureAddress = _pictureSource
        this.cleaned = _cleaned
    }

    // Getter and Setter methods

    fun getLocation():String { return this.location }
    fun setLocation( newLocation:String ){ this.location = newLocation }

    fun getDescription():String { return this.description }
    fun setDescription(newDescription:String) { this.description = newDescription }

    fun getUser():User { return this.user }
    fun setUser(newUser:User) {this.user = newUser}

    fun getLevel():String {return this.level}
    fun setLevel(newLevel:String) { this.level = newLevel}

    fun getPictureSource():String { return this.pictureAddress }
    fun setPictureSource(newPictureSource:String) { this.pictureAddress = newPictureSource}

    fun getCleaned():Boolean { return this.cleaned }
    fun setCleaned(newCleaned:Boolean) { this.cleaned = newCleaned}

}