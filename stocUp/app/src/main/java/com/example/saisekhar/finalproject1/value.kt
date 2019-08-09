package com.example.saisekhar.finalproject1

import java.io.Serializable

data class value(
        var portfo:Double?,
        var buy_power:Double?
): Serializable {
    constructor () : this ( 0.0,0.0)
}