package com.tematihonov.hoteltest.presentation.ui.util

fun priceConverter(price: Int): String {
    return String.format("%,d", price).replace(',',' ') + " ₽"
}