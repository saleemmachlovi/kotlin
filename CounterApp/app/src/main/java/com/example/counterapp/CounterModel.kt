package com.example.counterapp

data class CounterModel (var count: Int)

class CounterRepository{
    private val _counter = CounterModel(0)

    fun getCounter() = _counter

    fun increment() { _counter.count++}

    fun decrement() { _counter.count-- }

}