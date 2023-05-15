package com.example.request.video

import android.text.TextUtils
import android.util.Log
import com.blankj.utilcode.util.SPUtils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class RetrofitManager private constructor() {
    var retrofit: Retrofit? = null
        get() {
            if (field == null) {
                val okHttpClient = OkHttpClient.Builder()
                    .connectTimeout(5000, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addInterceptor(object: Interceptor {
                        override fun intercept(chain: Interceptor.Chain): Response {
                            val builder = chain.request().newBuilder()
                            SPUtils.getInstance().put("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxOTE2MzQxNzIxNCJ9.WO32H2rbdjWWzSLAgVF6n1Q_ScH5LZ8KrJl4h4nHhbs")
                            val token = SPUtils.getInstance().getString("token")
                            if(!TextUtils.isEmpty(token)){
                                builder.addHeader("token",token)

                            }
                            val request = builder.build()
//                            Log.e("===",""+request)
                            return chain.proceed(request)
                        }
                    })
                    .build()
                field = Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://124.70.98.255:8083/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return field
        }
        private set

    companion object {
        private var retrofitManager: RetrofitManager? = null
        val instance: RetrofitManager?
            get() {
                if (retrofitManager == null) {
                    synchronized(RetrofitManager::class.java) {
                        if (retrofitManager == null) {
                            retrofitManager = RetrofitManager()
                        }
                    }
                }
                return retrofitManager
            }
    }
}