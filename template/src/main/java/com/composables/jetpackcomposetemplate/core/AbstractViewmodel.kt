package com.composables.jetpackcomposetemplate.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composables.jetpackcomposetemplate.verboseLn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class AbstractViewModel<State : Any, Event : Any, UserAction : Any>(
    startWith: State,
) : ViewModel() {

    private val innerState = MutableStateFlow(startWith)
    val state: StateFlow<State> = innerState.asStateFlow()

    private val internalEvents = Channel<Event>(Channel.BUFFERED)
    val events = internalEvents.receiveAsFlow()

    private val internalUserActions = MutableSharedFlow<UserAction>(
        extraBufferCapacity = 1
    )
    private val userActions: Flow<UserAction> = internalUserActions.asSharedFlow()

    init {
        viewModelScope.launch {
            userActions.collect {
                processUserAction(it)
            }
        }
    }

    open suspend fun processUserAction(userAction: UserAction) {

    }

    protected suspend fun emitState(state: State) {
        withContext(Dispatchers.Main.immediate) {
            verboseLn { "${viewModelName()} ⬅️ ${simpleName(state)}" }
            innerState.emit(state)
        }
    }

    protected suspend fun emitEvent(event: Event) {
        withContext(Dispatchers.Main.immediate) {
            verboseLn { "${viewModelName()} ⚡️ ${simpleName(event)}" }
            internalEvents.trySend(event)
        }
    }

    fun pushUserAction(userAction: UserAction) {
        viewModelScope.launch {
            withContext(Dispatchers.Main.immediate) {
                verboseLn { "${viewModelName()} ➡️ ${simpleName(userAction)}" }
                internalUserActions.emit(userAction)
            }
        }
    }

    private fun simpleName(userAction: Any): String {
        return if (userAction.javaClass.kotlin.objectInstance != null) {
            userAction::class.simpleName ?: "object"
        } else {
            userAction.toString()
        }
    }

    private fun viewModelName(): String =
        this@AbstractViewModel::class.simpleName ?: "object"
}
