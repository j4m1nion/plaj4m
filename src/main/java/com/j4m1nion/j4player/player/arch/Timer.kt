package com.j4m1nion.j4player.player.arch

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

object Timer {
    private const val DEFAULT_DELAY = 1000L
    private var template : () -> Unit = {}
    private var job : Job? = null


    fun init(procedure : () -> Unit){
        template = procedure
    }

    fun start(scope: CoroutineScope, dispatcher: CoroutineDispatcher = Dispatchers.Main, delay : Long = DEFAULT_DELAY){
        job = scope.launch(dispatcher) {
            while (isActive){
                template.invoke()
                delay(delay)
            }
        }
    }

    fun stop(){
        job?.cancel()
    }

}