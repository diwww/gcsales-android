package ru.gcsales.app.data.model

/**
 * Shop model.
 *
 * @property name name of the shop
 * @property imageUrl url of shop logo
 *
 * @author Maxim Surovtsev
 * @since 03/04/2019
 */
data class Shop(
        val name: String,
        val imageUrl: String
) {
    constructor() : this(
            "",
            ""
    )
}