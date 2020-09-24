package com.upperlucky.generics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        ArrayList<? extends View> fruitList = new ArrayList<TextView>();

        ArrayList<? super AppCompatTextView> fruitList = new ArrayList<TextView>();
        HashMap<String, String> hm = new HashMap<>();
        hm.put("key", "value");
    }

    /**
     * 要求：把参数 param 的类型限制为必须同时满足：
     * 1. 是 Runnable；
     * 2. 是 Serializable。
     *
     * @param param
     */
    <T extends Runnable & Serializable> void someMethod(T param) {

    }


    /**
     * 要求：放宽参数限制，改为填任何类型的参数都行，只要参数 item 的类型和参数 list 的 List<> 尖括号里的类型一致就可以。
     * @param item
     * @param list
     */
    public <T> void merge(T item, List<T> list) {
        list.add(item);
    }
}