package ru.gcsales.app.data.model

/**
 * Item model.
 *
 * @property name name of the item
 * @author Maxim Surovtsev
 * @since 07/04/2019
 */
data class Item(
        var name: String
) {
    constructor() : this(
            ""
    )
}
