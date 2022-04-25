package com.sampleproject.utils.base.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * BaseViewModel with MVI control
 */
abstract class BaseViewModel<Action : UiAction, State : UiState, Effect : UiEffect> : ViewModel() {

    private val initialState: State by lazy { createInitialState() }

    /** Current UI state */
    protected val currentState: State
        get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)

    /** Current UI state as flow */
    val uiState = _uiState.asStateFlow()

    private val _uiAction: MutableSharedFlow<Action> = MutableSharedFlow()

    /** Current UI event as flow */
    val uiAction = _uiAction.asSharedFlow()

    private val _uiEffect: Channel<Effect> = Channel()

    /** Current UI effect as flow */
    val uiEffect = _uiEffect.receiveAsFlow()

    init {
        initListener()
    }

    /** Create initial UI state */
    abstract fun createInitialState(): State

    /** Handle each event */
    abstract fun handleEvent(event: Action)

    /** Set new Action */
    fun setAction(event: Action) {
        viewModelScope.launch { _uiAction.emit(event) }
    }

    /** Set new Ui State */
    protected fun setState(state: State) {
        _uiState.value = state
    }

    /** Set new Effect */
    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder.invoke()
        viewModelScope.launch { _uiEffect.send(effectValue) }
    }

    private fun initListener() {
        /** Start listening to Action */
        viewModelScope.launch {
            uiAction.collect { handleEvent(it) }
        }
    }

    /** Cancel all current works */
    fun clearAllWorks() {
        viewModelScope.coroutineContext.cancelChildren()
        initListener()
    }
}