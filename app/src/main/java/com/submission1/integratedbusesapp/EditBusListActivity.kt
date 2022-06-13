package com.submission1.integratedbusesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission1.integratedbusesapp.databinding.ActivityEditBusListBinding


class EditBusListActivity : AppCompatActivity() {
    private var _binding: ActivityEditBusListBinding? = null
    private val binding get() = _binding!!
    private val list = ArrayList<Buses>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEditBusListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()
        binding.rvEditbus.setHasFixedSize(true)

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
        binding.rvEditbus.layoutManager = LinearLayoutManager(this)
        val listBusesAdapter = EditBusListAdapter(list)
        binding.rvEditbus.adapter = listBusesAdapter

        listBusesAdapter.setOnItemClickCallback(object : EditBusListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Buses) {
                val moveIntent = Intent(this@EditBusListActivity,EditBusDetailsActivity::class.java)
                moveIntent.putExtra(EditBusDetailsActivity.DATA_USER,data)
                startActivity(moveIntent)
            }
        })
    }
}