package com.costular.atomtasks.core.locale

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

internal class LocaleResolverImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : LocaleResolver {
    override fun getLocale(): Locale {
        return context.resources.configuration.locales[0]
    }
}