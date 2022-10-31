package com.costular.atomtasks.ui

class NavigationError(route: String?) : RuntimeException("Unknown nav graph for destination $route")
