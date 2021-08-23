package com.codery.atheneum.data

object Bindings {

    private const val id = "id"
    private const val name = "name"

    object Genre {
        const val name = Bindings.name
        const val top = "top"
    }

    object Book {
        const val name = Bindings.name
        const val author = "author"
        const val image = "image"
        const val desc = "desc"
        const val genres = "genres"
        const val length = "length"
        const val publisher = "publisher"
        const val addedOn = "addedOn"
        const val available = "available"
        const val est = "est"
    }

    object Collections {
        const val Genre = "genre"
        const val Book = "book"
    }
}