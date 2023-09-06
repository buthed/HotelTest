package com.tematihonov.hoteltest.presentation.ui.rcview.bookingtourists

import android.app.DatePickerDialog
import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tematihonov.hoteltest.R
import com.tematihonov.hoteltest.databinding.ItemTouristBinding
import com.tematihonov.hoteltest.domain.models.Tourist
import com.tematihonov.hoteltest.presentation.ui.util.colorStateList
import com.tematihonov.hoteltest.presentation.viewmodel.BookingViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BookingTouristsAdapter(
    private val bookingViewModel: BookingViewModel,
) : RecyclerView.Adapter<BookingTouristsAdapter.BookingTouristsViewHolder>() {

    private lateinit var context: Context
    private lateinit var rListener: onItemClickListener

    interface onItemClickListener {
        fun onClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        rListener = listener
    }

    var tourists: List<Tourist> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    inner class BookingTouristsViewHolder(val binding: ItemTouristBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingTouristsViewHolder {

        context = parent.context
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTouristBinding.inflate(inflater, parent, false)
        return BookingTouristsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tourists.size
    }

    override fun onBindViewHolder(holder: BookingTouristsViewHolder, position: Int) {
        val tourist = tourists[position]
        with(holder.binding) {
            touristTitle.text = tourist.title
            when (tourist.expand) {
                true -> {
                    touristTitle.text = tourist.title
                    touristExpandPart.visibility = View.VISIBLE
                    touristHideExpandButton.rotation = -90f
                }

                false -> {
                    touristTitle.text = tourist.title
                    touristExpandPart.visibility = View.GONE
                    touristHideExpandButton.rotation = 90f
                }
            }
            bookingAddTouristButton.setOnClickListener { rListener.onClick(position) }
            bookingTouristName.setText(tourist.firstName)
            bookingTouristNameSecond.setText(tourist.secondName)
            bookingTouristDateOfBirth.setText(tourist.dateOfBirth)
            bookingTouristCitizenship.setText(tourist.citizen)
            bookingTouristPassportNumber.setText(tourist.passportNumber)
            bookingTouristPassportValidityPeriod.setText(tourist.passportValidation)

            if (bookingViewModel.touristsListInput.isNotEmpty() && !bookingViewModel.touristsListInput[position].new) {
                checkEmptyStrings(bookingViewModel.touristsListInput[position])
            }

            bookingTouristName.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    bookingViewModel.updateTourist(
                        position,
                        1,
                        bookingTouristName.text.toString()
                    )
                }
            }
            bookingTouristNameSecond.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    bookingViewModel.updateTourist(
                        position,
                        2,
                        bookingTouristNameSecond.text.toString()
                    )
                }
            }
            bookingTouristCitizenship.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    bookingViewModel.updateTourist(
                        position,
                        4,
                        bookingTouristCitizenship.text.toString()
                    )
                }
            }
            bookingTouristPassportNumber.onFocusChangeListener =
                OnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) {
                        bookingViewModel.updateTourist(
                            position,
                            5,
                            bookingTouristPassportNumber.text.toString()
                        )
                    }
                }

            datePicker(position)
        }
    }

    private fun ItemTouristBinding.checkEmptyStrings(tourist: Tourist) {

        if (tourist.firstName.isNotEmpty()) {
            bookingTouristName.backgroundTintList = colorStateList(R.color.edit_text_bg, context)
        } else {
            bookingTouristName.backgroundTintList = colorStateList(R.color.error, context)
        }

        if (tourist.secondName.isNotEmpty()) {
            bookingTouristNameSecond.backgroundTintList = colorStateList(R.color.edit_text_bg, context)
        } else {
            bookingTouristNameSecond.backgroundTintList = colorStateList(R.color.error, context)
        }

        if (tourist.dateOfBirth.isNotEmpty()) {
            bookingTouristDateOfBirth.backgroundTintList = colorStateList(R.color.edit_text_bg, context)
        } else {
            bookingTouristDateOfBirth.backgroundTintList = colorStateList(R.color.error, context)
        }

        if (tourist.citizen.isNotEmpty()) {
            bookingTouristCitizenship.backgroundTintList = colorStateList(R.color.edit_text_bg, context)
        } else {
            bookingTouristCitizenship.backgroundTintList = colorStateList(R.color.error, context)
        }

        if (tourist.passportNumber.isNotEmpty()) {
            bookingTouristPassportNumber.backgroundTintList = colorStateList(R.color.edit_text_bg, context)
        } else {
            bookingTouristPassportNumber.backgroundTintList = colorStateList(R.color.error, context)
        }

        if (tourist.passportValidation.isNotEmpty()) {
            bookingTouristPassportValidityPeriod.backgroundTintList =
                colorStateList(R.color.edit_text_bg, context)
        } else {
            bookingTouristPassportValidityPeriod.backgroundTintList = colorStateList(R.color.error, context)
        }
    }

    private fun ItemTouristBinding.datePicker(position: Int) {
        val cal = Calendar.getInstance()
        val dateSetListenerDateOfBirth =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "MM/dd/yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                sdf.format(cal.time).also {
                    bookingTouristDateOfBirth.text = Editable.Factory.getInstance().newEditable(it)
                }
            }

        val dateSetListenerPassportValidity =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "MM/dd/yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                sdf.format(cal.time).also {
                    bookingTouristPassportValidityPeriod.text =
                        Editable.Factory.getInstance().newEditable(it)
                }
            }

        bookingTouristDateOfBirth.setOnClickListener {
            DatePickerDialog(
                context,
                dateSetListenerDateOfBirth,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        bookingTouristDateOfBirth.onFocusChangeListener =
            OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    bookingViewModel.updateTourist(
                        position,
                        3,
                        bookingTouristDateOfBirth.text.toString()
                    )
                }
            }

        bookingTouristPassportValidityPeriod.setOnClickListener {
            DatePickerDialog(
                context,
                dateSetListenerPassportValidity,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        bookingTouristPassportValidityPeriod.onFocusChangeListener =
            OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    bookingViewModel.updateTourist(
                        position,
                        6,
                        bookingTouristPassportValidityPeriod.text.toString()
                    )
                }
            }
    }
}

