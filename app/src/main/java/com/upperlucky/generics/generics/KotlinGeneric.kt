package com.upperlucky.generics.generics

/**
 * created by yunKun.wen on 2020/9/21
 * desc:
 */
interface KotlinGeneric<T> {

    val value: T
    fun setValue(value: T)
}