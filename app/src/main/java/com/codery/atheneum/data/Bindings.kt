package com.codery.atheneum.data

object Bindings {

    private const val id = "id"
    private const val name = "name"

    object Genre {
        const val name = Bindings.name
        const val top = "top"
    }

    object Collections {
        const val Genre = "genre"
    }
}