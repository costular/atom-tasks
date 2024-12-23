package com.atomtasks.feature.detail

import com.costular.atomtasks.analytics.TrackingEvent

internal object DetailAnalytics {

    data object Closed : TrackingEvent(name = "detail_close")
    data object DatePickerOpened : TrackingEvent(name = "detail_task_date_picker_opened")
    data object ReminderPickerOpened : TrackingEvent(name = "detail_task_reminder_picker_opened")
    data object RecurrencePickerOpened : TrackingEvent(name = "detail_task_recurrence_picker_opened")
    data object TaskCreated : TrackingEvent(name = "detail_task_created")
    data object TaskEdited : TrackingEvent(name = "detail_task_edited")
    data object DiscardChangesShown : TrackingEvent(name = "detail_discard_changes_shown")
    data object DiscardChangesCancel : TrackingEvent(name = "detail_discard_changes_action_cancel")
    data object DiscardChangesDiscard : TrackingEvent(name = "detail_discard_changes_shown_action_discard")

}
