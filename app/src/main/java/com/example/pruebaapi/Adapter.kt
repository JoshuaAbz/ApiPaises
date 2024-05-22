package com.example.pruebaapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PaisAdapter(private val paises: List<Pais>, private val onItemClick: (Pais) -> Unit) :
    RecyclerView.Adapter<PaisAdapter.PaisViewHolder>() {

    inner class PaisViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textName = itemView.findViewById<TextView>(R.id.txtPais)
        //private val textDescrip = itemView.findViewById<TextView>(R.id.txtDescrip)
        private val imgP = itemView.findViewById<ImageView>(R.id.imgVBandera)

        fun bind(pais: Pais) {
            textName.text = pais.name.official
            //textDescrip.text = pais.capital.joinToString(",")
            Glide.with(itemView.context)
                .load(pais.flags.png)
                .into(imgP)

            // Configurar el clic en el itemView
            itemView.setOnClickListener {
                onItemClick(pais)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaisViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lista_paises, parent, false)
        return PaisViewHolder(view)
    }

    override fun getItemCount(): Int {
        return paises.size
    }

    override fun onBindViewHolder(holder: PaisViewHolder, position: Int) {
        val pais = paises[position]
        holder.bind(pais)
    }
}
