package com.example.saisekhar.finalproject1

import java.io.Serializable


data class StocksJSON(
        var comapny: String?,
        var open: Double?,
        var high: Double?,
        var low: Double?,
        var close: Double?,
        var volume: Int?
): Serializable {
    constructor () : this ( "",0.0,0.0,0.0,0.0,0 )
}