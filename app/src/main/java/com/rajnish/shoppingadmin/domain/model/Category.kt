package com.rajnish.shoppingadmin.domain.model

data class Category(
    var name : String = "",
    val date : Long = System.currentTimeMillis(),
    val categoryBy : String = "",
    val imageUrl : String = "",

)
