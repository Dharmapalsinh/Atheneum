package com.codery.atheneum

data class BookBuilder(
    val name : String,
    val author : String,
    val publisher : String,
    val length : Int,
    val desc : String,
    val image : String,

)

/*
* Collection name -> genres
* Id -> Autogenerate
*
* Name -> Edittext
*
* */

/*

Collection name -> books
Book Id -> Auto Generate

name -> EditText
author -> EditText
desc -> Multiline EditText (max length 250)
length -> EditText (Only numbers)
publisher -> EditText

* */