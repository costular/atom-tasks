package com.costular.atomtasks.createtask.analytics

import com.costular.atomtasks.analytics.TrackingEvent

object CreateTaskAnalytics {

    data object TaskCreated : TrackingEvent(name = "create_task_created")

    data class SetReminder(val time: String) : TrackingEvent(
        name = "create_task_reminder_set",
        attributes = mapOf("time" to time)
    )

    data object SetDay : TrackingEvent(name = "create_task_day_set")

}
