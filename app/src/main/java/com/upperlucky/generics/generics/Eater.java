package com.upperlucky.generics.generics;

import com.upperlucky.generics.generics.datatype.Food;

/**
 * created by yunKun.wen on 2020/9/20
 * desc:
 */
interface Eater<T extends Food> {
    void eat(T food);
}
