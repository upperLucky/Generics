package com.upperlucky.generics.generics;

import java.util.ArrayList;
import java.util.Collections;

/**
 * created by yunKun.wen on 2020/9/20
 * desc:
 */
class ReversableArrayList<E> extends ArrayList<E> {

    public void reverse() {
        Collections.reverse(this);
    }
}
