package com.codery.atheneum.app

data class InvalidDocument(override val message: String?) : Exception()

data class DocumentNotFound(override val message: String?) : Exception()