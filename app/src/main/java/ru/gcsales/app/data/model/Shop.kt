package ru.gcsales.app.data.model

import java.io.Serializable

/**
 * Shop model.
 *
 * @property id id of the shop
 * @property name name of the shop
 * @property imageUrl url of shop logo
 *
 * @author Maxim Surovtsev
 * @since 03/04/2019
 */
data class Shop(
        var id: String,
        var name: String,
        var imageUrl: String
) : Serializable {
    constructor() : this(
            "",
            "",
            ""
    )
}