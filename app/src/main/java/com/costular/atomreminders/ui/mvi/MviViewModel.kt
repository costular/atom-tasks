package com.costular.decorit.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.reflect.KProperty1

abstract class MviViewModel<S> constructor(initialState: S) : ViewModel() {

    private val _uiEvents = Channel<UiEvent>(Channel.BUFFERED)

    val uiEvents: Flow<UiEvent>
        get() = _uiEvents.receiveAsFlow()

    private val _state = MutableStateFlow(initialState)
    private val stateMutex = Mutex()

    val state: StateFlow<S>
        get() = _state.asStateFlow()

    protected fun setState(block: S.() -> S) {
        _state.update(block)
    }

    protected fun sendEvent(uiEvent: UiEvent) = viewModelScope.launch {
        _uiEvents.send(uiEvent)
    }
}