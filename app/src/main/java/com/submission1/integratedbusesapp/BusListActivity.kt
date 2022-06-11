package com.submission1.integratedbusesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.submission1.integratedbusesapp.databinding.ActivityBusListBinding
import com.submission1.integratedbusesapp.databinding.ActivityRegisterBinding

class BusListActivity : AppCompatActivity() {

    private var _binding: ActivityBusListBinding? = null
    private val binding get() = _binding!!
    private var lat: Double = 0.0
    private var long: Double = 0.0
    private var TAG = BusListActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityBusListBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
    companion object{
        const val LAT = "LAT"
        const val LONG = "LONG"
    }
}