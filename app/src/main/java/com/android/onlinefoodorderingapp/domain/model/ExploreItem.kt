package com.android.onlinefoodorderingapp.domain.model

data class ExploreItem(
    val id: Int,
    val title: String,
    val iconUrl: String,
    val type: String
)

/*[
  {
    "id": 1,
    "title": "Top Picks",
    "imageUrl": "...",
    "type": "TOP_RATED"
  },
  {
    "id": 2,
    "title": "Fast Delivery",
    "type": "FAST_DELIVERY"
  }
]*/
