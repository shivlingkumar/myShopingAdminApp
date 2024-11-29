package com.rajnish.shoppingadmin.domain.model

data class ProductDataModel(
    val name: String = "",
    val price: String = "",
    val finalPrice: String = "",
    val description: String = "",
    val category: String = "",
    val imageUrl: String = "",
    val date: Long = System.currentTimeMillis(),
    val createBy: String = "",
    val availableUnits: Int = 0,
    val productId : String = "",
)
