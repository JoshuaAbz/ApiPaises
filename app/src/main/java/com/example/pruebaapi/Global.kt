package com.example.pruebaapi

data class Pais(val name:Name, val capital:List<String>,val flags:Flag, val area:String,val population:String)

data class Flag(val png:String, val alt:String)

data class Name(val common:String, val official:String)

//Almacen de los paises
lateinit var listaPais: List<Pais>