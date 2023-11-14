package com.example.startingtemplate

import androidx.lifecycle.*

class MyViewModel() : ViewModel() {

    // add all observable properties here
    val property: MutableLiveData<Int> = MutableLiveData<Int>(0)

    init {
    }

    // add modelview update functions here

}