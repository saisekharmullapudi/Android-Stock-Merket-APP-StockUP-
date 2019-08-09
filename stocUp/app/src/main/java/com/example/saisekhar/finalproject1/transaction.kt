package com.example.saisekhar.finalproject1

import java.io.Serializable

data class Transaction(
        var uid:String?,
        var company:String?,
        var count:Double?,
        var price:Double?
): Serializable {
    constructor () : this ( "","",0.0 ,0.0)
}