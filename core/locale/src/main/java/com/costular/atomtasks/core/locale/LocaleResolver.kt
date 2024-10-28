package com.costular.atomtasks.core.locale

import java.util.Locale

interface LocaleResolver {
    fun getLocale(): Locale
}