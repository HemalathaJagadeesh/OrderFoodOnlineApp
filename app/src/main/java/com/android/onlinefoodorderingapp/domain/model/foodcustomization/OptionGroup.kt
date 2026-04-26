package com.android.onlinefoodorderingapp.domain.model.foodcustomization


data class OptionGroup(  val title: String,
                         val isSingleChoice: Boolean, // radio vs checkbox
                         val options: List<Option>)

data class Option(
    val id: String,
    val name: String,
    val price: Int = 0
)