package com.tematihonov.hoteltest.domain.models

data class Tourist(
    val title: String,
    var expand: Boolean = false,
    var new: Boolean = true,
    var firstName: String = "",
    var secondName: String = "",
    var dateOfBirth: String = "",
    var citizen: String = "",
    var passportNumber: String = "",
    var passportValidation: String = "",
)