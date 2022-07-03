package com.costular.atomtasks.domain.model

sealed class Theme {
    object Light : Theme()
    object Dark : Theme()
    object System : Theme()

    fun asString(): String = when (this) {
        Light -> LIGHT
        Dark -> DARK
        System -> SYSTEM
    }

    companion object {
        const val LIGHT = "light"
        const val DARK = "dark"
        const val SYSTEM = "system"

        fun fromString(theme: String): Theme = when (theme) {
            LIGHT -> Light
            DARK -> Dark
            SYSTEM -> System
            else -> System
        }
    }
}
