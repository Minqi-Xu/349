package com.example.viewmodelactivity

import androidx.lifecycle.*

class MainActivityViewModel1() : ViewModel() {

    val counter: MutableLiveData<Int> = MutableLiveData<Int>(0)

    init {
        println("MainActivityViewModel1 startup (no state persistance)")
    }

    fun increment() {
        counter.value = (counter.value ?: 0) + 1
    }
}