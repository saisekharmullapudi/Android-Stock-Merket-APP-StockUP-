package com.example.saisekhar.finalproject1
import java.io.Serializable
data class Person(
        val uid : String?,
        var firstname: String?,
        var lastname:String?,
        var email:String?,
        var password:String?,
        var dob:String?,
        var phone:String?,
        var SSN:String?,
        var address:String?,
        var imgurl:String?,
        var money:Double?
):Serializable {
    constructor () : this ("","","","","","","","","","",1000.0)
}