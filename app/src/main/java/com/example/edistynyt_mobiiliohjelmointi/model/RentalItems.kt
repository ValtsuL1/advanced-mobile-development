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

data class DeleteRentalItemState(
    val id: Int = 0,
    val err: String? = null
)

data class RentalItem(
    @SerializedName("rental_item_id")
    val id: Int = 0,
    @SerializedName("rental_item_name")
    val name: String = ""
)

data class RentalItemsResponse(val items: List<RentalItem> = emptyList())

data class RentalItemResponse(val item: RentalItem = RentalItem())

data class RentItemReq(
    @SerializedName("auth_user_auth_user_id")
    val id: Int
)

data class EditItemReq(
    @SerializedName("rental_item_name")
    val name: String
)