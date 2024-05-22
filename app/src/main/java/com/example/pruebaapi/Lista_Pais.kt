package com.example.pruebaapi

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.pruebaapi.databinding.ActivityListaPaisBinding
import com.example.pruebaapi.databinding.ActivityMainBinding

class Lista_Pais : AppCompatActivity() {
    private lateinit var biding:ActivityListaPaisBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        biding=ActivityListaPaisBinding.inflate(layoutInflater)
        setContentView(biding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Obtener los datos del intent
        val paisName = intent.getStringExtra("EXTRA_PAIS_NAME")
        val paisCapital = intent.getStringExtra("EXTRA_PAIS_CAPITAL")
        val paisFlag = intent.getStringExtra("EXTRA_PAIS_FLAG")
        val paisArea=intent.getStringExtra("EXTRA_PAIS_AREA")
        val paispoblacion=intent.getStringExtra("EXTRA_PAIS_POBLACION")

        // Mostrar los datos en las vistas correspondientes
        biding.txtPais.text = paisName
        biding.txtDescrip.text = paisCapital
        biding.pob.text=paispoblacion
        biding.area.text=paisArea

        Glide.with(this)
            .load(paisFlag)
            .into(biding.imgVBandera)
    }
}