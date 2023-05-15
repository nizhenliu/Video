package com.example.videohall.state

import com.example.videohall.model.entity.MutilTypeEntity
import java.util.*
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

sealed class MutilTypeState {
    data class getMutilTypeSuccessState(val data:List<MutilTypeEntity>):MutilTypeState(){
        override fun equals(other: Any?): Boolean {
            return false
        }

        override fun hashCode(): Int {
            return Random.nextInt()
        }
    }
    object mutilTypeInit:MutilTypeState()
    object getMutilTypeFailureState:MutilTypeState()

    data class getSubMutilTypeSuccessState(val data:List<MutilTypeEntity>):MutilTypeState(){
        override fun equals(other: Any?): Boolean {
            return false
        }

        override fun hashCode(): Int {
            return Random.nextInt()
        }
    }
    object getSubMutilTypeFailureState:MutilTypeState(){
        override fun equals(other: Any?): Boolean {
            return false
        }

        override fun hashCode(): Int {
            return Random.nextInt()
        }
    }
}
