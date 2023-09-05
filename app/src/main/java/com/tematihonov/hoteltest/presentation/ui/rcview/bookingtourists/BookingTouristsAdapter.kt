package com.tematihonov.hoteltest.presentation.ui.rcview.bookingtourists

import android.app.DatePickerDialog
import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tematihonov.hoteltest.data.models.Tourist
import com.tematihonov.hoteltest.databinding.ItemTouristBinding
import com.tematihonov.hoteltest.presentation.viewmodel.BookingViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BookingTouristsAdapter(
    private val bookingViewModel: BookingViewModel
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
            bookingAddTouristButton.setOnClickListener {
                rListener.onClick(position)
            }

            when(tourist.expand) {
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

            bookingTouristName.onFocusChangeListener =
                OnFocusChangeListener { v, hasFocus -> if (!hasFocus) { bookingViewModel.updateTourist(position,1, bookingTouristName.text.toString()) } }
            bookingTouristNameSecond.onFocusChangeListener =
                OnFocusChangeListener { v, hasFocus -> if (!hasFocus) { bookingViewModel.updateTourist(position,2, bookingTouristName.text.toString()) } }
            bookingTouristCitizenship.onFocusChangeListener =
                OnFocusChangeListener { v, hasFocus -> if (!hasFocus) { bookingViewModel.updateTourist(position,4, bookingTouristName.text.toString()) } }
            bookingTouristPassportNumber.onFocusChangeListener =
                OnFocusChangeListener { v, hasFocus -> if (!hasFocus) { bookingViewModel.updateTourist(position,5, bookingTouristName.text.toString()) } }
            datePicker(position)

        }
    }

//    fun ItemTouristBinding.checkEmptyStrings() {
//        if (bookingTouristName.text!!.isNotEmpty()) {
//            Log.d("GGG", "Phone GOOD")
//            bookingTouristName.backgroundTintList = colorStateList(R.color.edit_text_bg)
//            //return true
//        } else {
//            bookingTouristName.backgroundTintList = colorStateList(R.color.error)
//            Log.d("GGG", "Phone BAD")
//            //return false
//        }
//    }

    private fun ItemTouristBinding.datePicker(position: Int) {
        val cal = Calendar.getInstance()
        val dateSetListenerDateOfBirth =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "MM/dd/yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                sdf.format(cal.time).also {
                    bookingTouristDateOfBirth.text = Editable.Factory.getInstance().newEditable(it)
                }
            }

        val dateSetListenerPassportValidity =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "MM/dd/yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                sdf.format(cal.time).also {
                    bookingTouristNamePassportValidityPeriod.text = Editable.Factory.getInstance().newEditable(it)
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
            OnFocusChangeListener { v, hasFocus -> if (!hasFocus) { bookingViewModel.updateTourist(position,3, bookingTouristName.text.toString()) } }

        bookingTouristNamePassportValidityPeriod.setOnClickListener {
            DatePickerDialog(
                context,
                dateSetListenerPassportValidity,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        bookingTouristNamePassportValidityPeriod.onFocusChangeListener =
            OnFocusChangeListener { v, hasFocus -> if (!hasFocus) { bookingViewModel.updateTourist(position,6, bookingTouristName.text.toString()) } }
    }

//    private fun colorStateList(color: Int) =
//        ColorStateList.valueOf(ContextCompat.getColor(context, color))
}

