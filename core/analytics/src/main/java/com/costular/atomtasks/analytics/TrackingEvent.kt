package com.costular.atomtasks.analytics

open class TrackingEvent(
    val name: String,
    val attributes: Map<String, Any?>? = null,
)
