package br.senai.sp.jandira.rickandmorty.model

data class Info(
    //mesmo nome que está no JSON
    val count: Int = 0,
    val pages: Int = 0,
    val next: String = "",
    val prev: String = ""
)
