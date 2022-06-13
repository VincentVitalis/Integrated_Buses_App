package com.submission1.integratedbusesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.submission1.integratedbusesapp.databinding.ActivityEditBusDetailsBinding

class EditBusDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBusDetailsBinding
    private lateinit var data: Buses
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBusDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        data = intent.getParcelableExtra(DATA_USER)!!
        setToolbar()
        showdata()
        binding.simpanData.setOnClickListener {
            Toast.makeText(this, "App in Development", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setToolbar() {
        supportActionBar?.title = data.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    private fun showdata(){

        binding.apply {
            namaBus.setText(data.name)
            lokasiAwal.setText(data.route_start)
            lokasiTujuan.setText(data.route_end)
            kapasitasBus.setText(data.passenger_count)
            kapasitasMaks.setText(data.maxcapacity)
        }
    }
    companion object{
        const val DATA_USER = "DATA_USER"
    }
}