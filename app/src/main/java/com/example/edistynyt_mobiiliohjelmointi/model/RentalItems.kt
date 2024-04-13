package com.example.edistynyt_mobiiliohjelmointi.model

import com.google.gson.annotations.SerializedName

data class RentalItemsState(
    val list: List<RentalItem> = emptyList(),
    val isAddingRentalItem: Boolean = false,
    val err: String? = null,
    val loading: Boolean = false
)

data class RentalItemState(
    val item: RentalItem = RentalItem(),
    val err: String? = null,
    val ok: Boolean = false,
    val loading: Boolean = false
)

data class RentalItem(
    @SerializedName("rental_item_id")
    val id: Int = 0,
    @SerializedName("rental_item_name")
    val name: String = ""
)

data class RentalItemsResponse(val items: List<RentalItem> = emptyList())