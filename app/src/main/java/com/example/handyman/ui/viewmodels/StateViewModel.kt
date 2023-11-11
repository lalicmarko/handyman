package com.example.handyman.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.reflect.KClass

abstract class StateViewModel<State>(state: State) : ViewModel() {
    /**
     * Current state
     */
    protected val state: State
        get() = stateFlow.value

    /**
     * shadow property for state flow, which populates state changes
     */
    private val _stateFlow = MutableStateFlow(state)

    /**
     * state subscription property with state changes
     */
    val stateFlow: StateFlow<State> = _stateFlow.asStateFlow()

    /**
     * when we set state, we have to publish it to publisher
     * in the context of same state as we can clone it with efficient `copy()` operator
     */
    protected fun setState(block: State.() -> (State)) {
        _stateFlow.update(block)
    }

    val registrants: MutableSet<String> = mutableSetOf()

    /**
     * A way to register a registrant to this view model.
     */
    fun start(registrant: Class<*>) {
        start(registrant.canonicalName)
    }

    /**
     * A way to register a registrant to this view model for a given class with a custom tag.
     */
    fun start(registrant: Class<*>, tag: String) {
        start("${registrant.canonicalName}#$tag")
    }

    /**
     * A way to unregister a registrant from this view model.
     */
    fun stop(registrant: Class<*>) {
        stop(registrant.canonicalName)
    }

    /**
     * A way to unregister a registrant from this view model for a given class with a custom tag.
     */
    fun stop(registrant: Class<*>, tag: String) {
        stop("${registrant.canonicalName}#$tag")
    }

    /**
     * A way to register a registrant to this view model.
     */
    fun start(registrant: KClass<*>) {
        start(registrant.java.canonicalName)
    }

    /**
     * A way to register a registrant to this view model for a given class with a custom tag.
     */
    fun start(registrant: KClass<*>, tag: String) {
        start("${registrant.java.canonicalName}#$tag")
    }

    /**
     * A way to unregister a registrant from this view model.
     */
    fun stop(registrant: KClass<*>) {
        stop(registrant.java.canonicalName)
    }

    /**
     * A way to unregister a registrant from this view model for a given class with a custom tag.
     */
    fun stop(registrant: KClass<*>, tag: String) {
        stop("${registrant.java.canonicalName}#$tag")
    }

    private fun start(registrant: String) {
        registrants.add(registrant)
        onStart()
    }

    private fun stop(registrant:String) {
        registrants.remove(registrant)
        onStop()
    }

    protected open fun onStart() {}
    protected open fun onStop() {}
}