package com.tematihonov.hoteltest.presentation.ui.util

import android.text.Editable
import android.text.TextWatcher
import java.lang.Integer.min

class NumberTextWatcher() : TextWatcher {
    companion object {
        const val MASK_CHAR = '*'
    }

    private var mask: String = "(***) ***-**-**"
    private var isCursorRunning = false
    private var isDeleting = false

    override fun afterTextChanged(s: Editable?) {
        if (isCursorRunning || isDeleting) {
            return
        }
        isCursorRunning = true
        s?.let {
            val onlyDigits = removeMask(it.toString())
            it.clear()
            it.append(applyMask(mask, onlyDigits))
        }
        isCursorRunning = false
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        isDeleting = count > after
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    private fun applyMask(mask: String, onlyDigits: String): String {
        var onlyDigits1 = onlyDigits

        val maskPlaceholderCharCount = mask.count { it == MASK_CHAR }
        var maskCurrentCharIndex = 0
        var output = ""

        if (onlyDigits.length>1) {
            onlyDigits1 = onlyDigits1.drop(1)
        }

        onlyDigits1.take(min(maskPlaceholderCharCount, onlyDigits1.length)).forEach { c ->
            for (i in maskCurrentCharIndex until mask.length) {
                if (mask[i] == MASK_CHAR) {
                    output += c
                    maskCurrentCharIndex += 1
                    break
                } else {
                    output += mask[i]
                    maskCurrentCharIndex = i + 1
                }
            }
        }
        return "+7 $output"
    }

    private fun removeMask(value: String): String {
        return Regex("\\D+").replace(value, "")
    }
}