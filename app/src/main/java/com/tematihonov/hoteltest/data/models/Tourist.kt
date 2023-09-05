package com.tematihonov.hoteltest.data.models

data class Tourist(
    val title: String,
    var expand: Boolean = false,
    var firstName: String = "",
    var secondName: String = "",
    var dateOfBirth: String = "",
    var citizen: String = "",
    var passportNumber: String = "",
    var passportValidation: String = "",
)