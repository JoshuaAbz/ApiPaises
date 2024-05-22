package com.example.pruebaapi

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pruebaapi.databinding.ActivityMainBinding
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Handler
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.rclPaises.layoutManager = LinearLayoutManager(this)

        // Realizar la solicitud del endpoint de API
        fetchCountries()
    }

    private fun fetchCountries() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Fuel.get("https://restcountries.com/v3.1/all?fields=name,capital,flags,area,population")
                    .response { request, response, result ->
                        when (result) {
                            is Result.Failure -> {
                                val error = result.getException()
                                runOnUiThread {
                                    Toast.makeText(this@MainActivity, "Se detecto el error: ${error.message}", Toast.LENGTH_LONG).show()
                                }
                            }
                            is Result.Success -> {
                                val data = result.get()
                                val cuerpoJson = data.toString(Charsets.UTF_8)
                                val convertidor = Gson()
                                val listaPais = convertidor.fromJson(cuerpoJson, Array<Pais>::class.java).toList()

                                runOnUiThread {
                                    binding.txtnota.text = "Hay ${listaPais.size} elementos"
                                    binding.rclPaises.adapter = PaisAdapter(listaPais) { pais ->
                                        val intent = Intent(this@MainActivity, Lista_Pais::class.java).apply {
                                            putExtra("EXTRA_PAIS_NAME", pais.name.official)
                                            putExtra("EXTRA_PAIS_CAPITAL", pais.capital.joinToString(","))
                                            putExtra("EXTRA_PAIS_FLAG", pais.flags.png)
                                            putExtra("EXTRA_PAIS_POBLACION", pais.population)
                                            putExtra("EXTRA_PAIS_AREA", pais.area)
                                        }
                                        startActivity(intent)
                                    }
                                }
                            }
                        }
                    }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Se detecto el error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
