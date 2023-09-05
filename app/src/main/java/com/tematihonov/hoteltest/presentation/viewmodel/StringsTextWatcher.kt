package com.tematihonov.hoteltest.presentation.viewmodel

import android.text.Editable
import android.text.TextWatcher

class StringsTextWatcher(position1: Int, bookingViewModel: BookingViewModel, param: Int) : TextWatcher {
    private val viewModel = bookingViewModel
    private val position = position1
    private val parameter = param

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        viewModel.updateTourist(position,parameter, s.toString())
    }

    override fun afterTextChanged(s: Editable?) {}
}