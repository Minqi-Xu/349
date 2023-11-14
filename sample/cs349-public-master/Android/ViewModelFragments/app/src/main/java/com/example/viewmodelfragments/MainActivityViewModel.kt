package com.example.viewmodelfragments

import androidx.lifecycle.*

class MainActivityViewModel() : ViewModel() {

    val counter: MutableLiveData<Int> = MutableLiveData<Int>(0)

    init {
        println("MainActivityViewModel startup")
    }

    fun increment() {
        counter.value = (counter.value ?: 0) + 1
    }

    fun reset() {
        counter.value = 0
    }
}