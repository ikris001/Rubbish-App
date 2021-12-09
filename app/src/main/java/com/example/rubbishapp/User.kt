package com.example.rubbishapp

class User constructor(val _userID:Int, val _username:String, val _password:String,
                       val _email:String, val _profilePicture:String, val _bio:String,
                       val _role:String, val _score:Int) {
    private var userID: Int
    private var username: String
    private var password:String
    private var email:String
    private var profilePictureAddress:String
    private var bio:String
    private var role:String
    private var score:Int

    // Initialises all attributes
    init{
        this.userID = _userID
        this.username = _username
        this.password = _password
        this.email = _email
        this.profilePictureAddress = _email
        this.bio = _bio
        this.role = _role
        this.score = _score
    }

    //Getter and Setter methods
    fun getId():Int{ return this.userID }
    fun setID(newId:Int){ this.userID = newId }

    fun getUsername():String{ return this.username }
    fun setUsername(newUsername:String){ this.username = newUsername }

    fun getPassword():String{ return this.password }
    fun setPassword(newPassword:String){ this.password = newPassword }

    fun getEmail():String{ return this.email }
    fun setEmail(newEmail:String){ this.email = newEmail}

    fun getProfilePicture():String{ return this.profilePictureAddress }
    fun setProfilePicture(newProfilePicture:String){ this.profilePictureAddress = newProfilePicture}

    fun getBio():String{ return this.bio }
    fun setBio(newBio:String){ this.bio = newBio}

    fun getRole():String{ return this.role }
    fun setRole(newRole:String){ this.role = newRole}

    fun getScore():Int{ return this.score }
    fun setScore(newScore:Int){ this.score = newScore}

}