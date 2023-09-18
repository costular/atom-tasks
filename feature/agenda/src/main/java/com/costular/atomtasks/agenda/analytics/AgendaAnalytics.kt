package com.costular.atomtasks.agenda.analytics

import com.costular.atomtasks.analytics.TrackingEvent


internal object AgendaAnalytics {
    data object SelectToday : TrackingEvent(name = "agenda_select_today")

    data object ExpandCalendar : TrackingEvent(name = "agenda_expand_calendar")

    data object CollapseCalendar : TrackingEvent(name = "agenda_collapse_calendar")

    data object ShowConfirmDeleteDialog :
        TrackingEvent(name = "agenda_show_confirm_delete_dialog")

    data object ConfirmDelete : TrackingEvent(name = "agenda_confirm_delete_task")

    data object CancelDelete : TrackingEvent(name = "agenda_cancel_delete_task")

    data object EditTask : TrackingEvent(name = "agenda_actions_edit")

    object CreateNewTask : TrackingEvent(name = "create_new_task")

    data class NavigateToDay(val day: String) : TrackingEvent(
        name = "agenda_navigate_to_day",
        attributes = mapOf("day" to day),
    )

    data object MarkTaskAsDone : TrackingEvent(name = "agenda_mark_task_as_done")

    data object MarkTaskAsNotDone : TrackingEvent(name = "agenda_mark_task_as_not_done")

    data object OpenTaskActions : TrackingEvent(name = "agenda_task_actions")

    data object OrderTask : TrackingEvent(name = "agenda_order_task")

}
