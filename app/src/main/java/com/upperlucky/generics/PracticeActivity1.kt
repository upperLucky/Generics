package com.upperlucky.generics

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.upperlucky.generics.model.Repo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_practice1.*
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

/**
 * created by yunKun.wen on 2020/9/22
 * desc:
 */
class PracticeActivity1 : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice1)

        GlobalScope.launch(Dispatchers.Main) {
            val data = getData()
            val processedData = processData(data)
            textView.text = processedData
        }
        // 练习内容：用协程让上面 ↑ 这两行放在后台执行，然后把代码截图贴到腾讯课堂的作业里


        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
        val api = retrofit.create(Api::class.java)

        Single.zip<List<Repo>, List<Repo>, String>(
                api.listReposRx("rengwuxian"),
                api.listReposRx("google"),
                BiFunction { repos1, repos2 -> "${repos1[0].name} - ${repos2[0].name}" })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<String> {
                    override fun onSubscribe(d: Disposable?) {

                    }

                    override fun onSuccess(combined: String) {
                        textView.text = combined
                    }

                    override fun onError(e: Throwable) {
                        textView.text = e.message
                    }

                })


        /**
         * kotlin 写法
         */
        lifecycleScope.launch {
            try {
                val rengwuxian = async { api.listReposKt("rengwuxian") }
                val google = async { api.listReposKt("google") }
                textView.text = "${rengwuxian.await()[0].name} - ${google.await()[0].name}"
            } catch (e: Exception) {
                textView.text = e.message
            }

        }

    }

    // 耗时函数 1
    private suspend fun getData() =
            // 假设这个函数比较耗时，需要放在后台
            withContext(Dispatchers.IO) {
                return@withContext "hen_coder"
            }


    // 耗时函数 2
    private suspend fun processData(data: String) =
            // 假设这个函数也比较耗时，需要放在后台
            withContext(Dispatchers.IO) {
                return@withContext data.split("_") // 把 "hen_coder" 拆成 ["hen", "coder"]
                        .map { it.capitalize() } // 把 ["hen", "coder"] 改成 ["Hen", "Coder"]
                        .reduce { acc, s -> acc + s } // 把 ["Hen", "Coder"] 改成 "HenCoder"
            }

}
