package com.costular.atomtasks.tasks.analytics

import com.costular.atomtasks.analytics.TrackingEvent

data object NotificationsActionsDone : TrackingEvent(name = "notifications_actions_done")

data object NotificationsActionsPostpone : TrackingEvent(name = "notifications_actions_postpone")
