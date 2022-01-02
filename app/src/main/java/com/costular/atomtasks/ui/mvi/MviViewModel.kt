package com.costular.atomtasks.ui.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.costular.decorit.presentation.base.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class MviViewModel<S> constructor(initialState: S) : ViewModel() {

    private val _uiEvents = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvents: Flow<UiEvent>
        get() = _uiEvents.receiveAsFlow()

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S>
        get() = _state.asStateFlow()

    protected fun setState(block: S.() -> S) {
        _state.update(block)
    }

    protected fun withState(block: S.() -> Unit) {
        block(_state.value)
    }

    protected fun sendEvent(uiEvent: UiEvent) = viewModelScope.launch {
        _uiEvents.send(uiEvent)
    }
}