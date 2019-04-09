package ru.gcsales.app.data.model

import com.google.firebase.firestore.Exclude
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
        @get:Exclude var id: String,
        var name: String,
        var imageUrl: String
) : Serializable {
    constructor() : this(
            "",
            "",
            ""
    )
}