package ru.gcsales.app.data.model

import com.google.firebase.firestore.Exclude

/**
 * Item model.
 *
 * @property id id of the item
 * @property name name of the item
 * @property shop name of the shop
 * @property imageUrl image url
 * @property oldPrice old price
 * @property newPrice new price
 *
 * @author Maxim Surovtsev
 * @since 07/04/2019
 */
data class Item(
        @get:Exclude var id: String,
        var name: String,
        var shop: String,
        var imageUrl: String?,
        var oldPrice: Double,
        var newPrice: Double
) {
    constructor() : this(
            "",
            "",
            "",
            "",
            .0,
            .0
    )
}
