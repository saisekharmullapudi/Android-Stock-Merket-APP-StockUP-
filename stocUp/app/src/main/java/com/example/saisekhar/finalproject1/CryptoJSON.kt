package com.example.saisekhar.finalproject1

import java.io.Serializable


data class CryptoJSON(
        var Exchange_Rate: Double?,
        var From_Currency_code: String?,
        var From_Currency: String?,
        var To_Currency: String?
): Serializable {
    constructor () : this ( 0.0,"","","" )
}