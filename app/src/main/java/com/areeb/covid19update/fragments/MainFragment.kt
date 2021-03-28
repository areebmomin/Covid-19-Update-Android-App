package com.areeb.covid19update.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.areeb.covid19update.databinding.FragmentMainBinding
import com.areeb.covid19update.viewmodels.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        //get calendar instance
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)

        //initialize date string
        var dateSelectedCountry = String()

        //get default country name
        var countryName = binding.countryCodePicker.defaultCountryName

        //set date button onClick listener
        binding.setDateDailyReportButton.setOnClickListener {
            context?.let { it1 ->
                val datePickerDialog = DatePickerDialog(it1, { _, year, monthOfYear, dayOfMonth ->

                    //set selected in calender
                    calender.set(year, monthOfYear, dayOfMonth)

                    //get formatted date
                    val dateSelected =
                        SimpleDateFormat("yyyy-MM-dd", Locale.US).format(calender.time)

                    //set selected in button
                    binding.setDateDailyReportButton.text = dateSelected

                    //fetch daily report of selected date
                    viewModel.getDailyReportTotals("2020-07-21")

                }, year, month, day)

                //set current date as max date
                datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

                //show date picker dialog
                datePickerDialog.show()
            }
        }

        //country code picker change listener
        binding.countryCodePicker.setOnCountryChangeListener {
            countryName = binding.countryCodePicker.selectedCountryName

            //fetch daily report of selected date and country
            if (dateSelectedCountry.isNotEmpty())
                viewModel.getDailyReportByCountryName(dateSelectedCountry, countryName)
        }

        //set country date onCLick listener
        binding.setDateCountryLinearLayout.setOnClickListener {
            context?.let { it1 ->
                val datePickerDialog = DatePickerDialog(it1, { _, year, monthOfYear, dayOfMonth ->

                    //set selected in calender
                    calender.set(year, monthOfYear, dayOfMonth)

                    //get formatted date
                    dateSelectedCountry =
                        SimpleDateFormat("yyyy-MM-dd", Locale.US).format(calender.time)

                    //set date in button
                    binding.setDateCountrytextView.text = dateSelectedCountry

                    //fetch daily report of selected date and country
                    viewModel.getDailyReportByCountryName(dateSelectedCountry, countryName)

                }, year, month, day)

                //set current date as max date
                datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

                //show date picker dialog
                datePickerDialog.show()
            }
        }

        return binding.root
    }
}