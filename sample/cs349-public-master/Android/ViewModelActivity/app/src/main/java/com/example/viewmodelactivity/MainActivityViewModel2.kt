package com.example.viewmodelactivity

import androidx.lifecycle.*

class MainActivityViewModel2(private val state: SavedStateHandle) : ViewModel() {

    val counter: MutableLiveData<Int> = MutableLiveData<Int>(0)

    init {
        println("MainActivityViewModel2 startup (with state persistance)")

        // restore state
        counter.value = state["counter"] ?: 0

        checkState("counter")
    }

    fun increment() {
        counter.value = (counter.value ?: 0) + 1
        state.set("counter", counter.value)
        checkState("counter")
    }

    // region for debugging

    fun checkAllStates() {
        println("viewmodel check")
        checkState("counter")
    }

    fun checkState(k: String) {
        val contains = state.contains(k)
        if (contains) {
            val v = state.getLiveData<Int>(k).value
            println("state has '$k', value is '$v'")
        } else {
            println("'$k' not in state")
        }
    }

    //endregion
}