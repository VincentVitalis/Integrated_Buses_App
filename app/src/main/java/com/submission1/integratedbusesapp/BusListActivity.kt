package com.submission1.integratedbusesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission1.integratedbusesapp.databinding.ActivityBusListBinding


class BusListActivity : AppCompatActivity() {

    private var _binding: ActivityBusListBinding? = null
    private val binding get() = _binding!!
    private var lat: Double = 0.0
    private var long: Double = 0.0
    private val list = ArrayList<Buses>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityBusListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lat = intent.getDoubleExtra(LAT,-33.8523341)
        long = intent.getDoubleExtra(LONG,151.2106085)

        setToolbar()
        list.addAll(listBuses)
        showRecyclerList()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private val listBuses : ArrayList<Buses>
        get() {
            val idBus = resources.getIntArray(R.array.id)
            val name = resources.getStringArray(R.array.namabus)
            val awalTujuan = resources.getStringArray(R.array.awaltujuan)
            val akhirTujuan = resources.getStringArray(R.array.akhirtujuan)
            val jumlahPenumpang = resources.getStringArray(R.array.jumlahpenumpang)
            val kapasitasMaks = resources.getStringArray(R.array.kapasitasmaks)
            val finalList = ArrayList<Buses>()
            for(i in idBus.indices){
                val temp = Buses(idBus[i],name[i],awalTujuan[i],akhirTujuan[i],jumlahPenumpang[i],kapasitasMaks[i])
                finalList.add(temp)
            }
            return finalList
        }
    private fun showRecyclerList() {
        binding.cariBusList.layoutManager = LinearLayoutManager(this)
        val listBusesAdapter = BusListAdapter(list)
        binding.cariBusList.adapter = listBusesAdapter

        listBusesAdapter.setOnItemClickCallback(object : BusListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Buses) {
                Toast.makeText(this@BusListActivity, "App in Development", Toast.LENGTH_SHORT).show()
            }
        })
    }
    companion object{
        const val LAT = "LAT"
        const val LONG = "LONG"
    }
}