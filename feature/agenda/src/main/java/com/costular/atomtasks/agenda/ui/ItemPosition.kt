package com.costular.atomtasks.agenda.ui

import androidx.compose.foundation.lazy.LazyListItemInfo

data class ItemPosition(
    val index: Int,
    val key: Long,
)

internal fun LazyListItemInfo.asItemPosition(): ItemPosition = ItemPosition(
    index = index,
    key = key as Long,
)
