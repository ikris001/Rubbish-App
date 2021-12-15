package com.example.rubbishapp

class Report(_id:Int, _location:String, _user:User, _level:String, _pictureSource:String,
             _cleaned:Boolean) {

    // Declaration of all attributes and their getter and setter methods
    private var id:Int
        get() = this.id
        set(newId) {
            this.id = newId
        }

    private var location:String
        get() = this.location
        set(newLocation) {
            this.location = newLocation
        }

    private var user:User
        get() = this.user
        set(newUser) {
            this.user = newUser
        }
    private var level:String
        get() = this.level
        set(newLevel) {
            this.level = newLevel
        }

    private var pictureSource:String
        get() = this.pictureSource
        set(newPictureSource) {
            this.pictureSource = newPictureSource
        }

    private var cleaned:Boolean
        get() = this.cleaned
        set(newCleaned) {
            this.cleaned = newCleaned
        }

    // Initializer
    init {
        this.id = _id
        this.location = _location
        this.user = _user
        this.level = _level
        this.pictureSource = _pictureSource
        this.cleaned = _cleaned
    }
}