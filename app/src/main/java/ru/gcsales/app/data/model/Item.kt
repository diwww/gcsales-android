package ru.gcsales.app.data.model

/**
 * Item model.
 *
 * @property name name of the item
 * @author Maxim Surovtsev
 * @since 07/04/2019
 */
data class Item(
        var name: String,
        var imageUrl: String?,
        var oldPrice: Double,
        var newPrice: Double
) {
    constructor() : this(
            "",
            "",
            .0,
            .0
    )
}
