package com.example.androidprojekt.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.androidprojekt.R
import com.example.androidprojekt.databinding.FragmentMapBinding
import com.example.androidprojekt.dataformats.CityData
import com.example.androidprojekt.webclient.Webclient
import kotlinx.coroutines.*

class MapFragment  : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)

        binding.spinnerCities.onItemSelectedListener

        val spinner = binding.spinnerCities

        ArrayAdapter.createFromResource(this.requireContext(), R.array.cities_array,android.R.layout.simple_spinner_dropdown_item
        ).also {adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = this

        return binding.root
    }
        override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
            val item = parent.getItemAtPosition(pos)
            if(item == "--Select a city--")
                return
            println(parent.getItemAtPosition(pos))
            binding.mapCityCoords.text = "..."
            GlobalScope.launch(Dispatchers.Main) {
                val city = Webclient.requestCity(parent.getItemAtPosition(pos).toString())
                if(city == null){
                    Toast.makeText(super.requireContext(), "City could not be found", Toast.LENGTH_SHORT).show()
                }
                else{
                    val location = "City location is ${city.latitude}:${city.longitude}"
                    Toast.makeText(super.requireContext(), location, Toast.LENGTH_SHORT).show()
                    binding.mapCityCoords.text = location
                }
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }
}