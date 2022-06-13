package com.submission1.integratedbusesapp


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.submission1.integratedbusesapp.databinding.RowBusesBinding

data class BusListAdapter(private val listBuses: ArrayList<Buses>): RecyclerView.Adapter<BusListAdapter.ListViewHolder>(){
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
    class ListViewHolder(var binding: RowBusesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = RowBusesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val(idBus,name,awalTujuan,akhirTujuan,jumlahPenumpang,kapasitasMaks)=listBuses[position]
        holder.apply{
            binding.tvName.text = name
            binding.tujuan.text = "$awalTujuan - $akhirTujuan"
            binding.jumlahpenumpang.text = jumlahPenumpang
            binding.kapasitasmax.text = kapasitasMaks
            itemView.setOnClickListener{ onItemClickCallback.onItemClicked(listBuses[holder.adapterPosition])}
        }
    }

    override fun getItemCount(): Int = listBuses.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Buses)
    }
}