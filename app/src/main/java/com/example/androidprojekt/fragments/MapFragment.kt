package com.example.androidprojekt.fragments

import android.content.Intent
import android.net.Uri
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
import com.example.androidprojekt.webclient.Webclient
import kotlinx.coroutines.*

class MapFragment  : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
            CoroutineScope(Dispatchers.IO).launch {
                val city = Webclient.requestCity(parent.getItemAtPosition(pos).toString(),super.requireContext())
                if(city == null){
                    println("City could not be found")
                }
                else{
                    val gmmIntentUri = Uri.parse("geo:${city.latitude},${city.longitude}")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    startActivity(mapIntent)
                }
            }
        }
    override fun onNothingSelected(parent: AdapterView<*>) {}
}