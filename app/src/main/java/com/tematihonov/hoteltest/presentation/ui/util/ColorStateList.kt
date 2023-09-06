package com.tematihonov.hoteltest.presentation.ui.util

import android.content.Context
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat

fun colorStateList(color: Int, context: Context) =
    ColorStateList.valueOf(ContextCompat.getColor(context, color))