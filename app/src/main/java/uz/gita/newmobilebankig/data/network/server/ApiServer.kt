package uz.gita.newmobilebankig.data.network.server

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import uz.gita.newmobilebankig.BuildConfig.BASE_URL
import uz.gita.newmobilebankig.BuildConfig.LOGGING
import uz.gita.newmobilebankig.app.App
import uz.gita.newmobilebankig.data.ScreenState
import uz.gita.newmobilebankig.data.database.LocalDatabase
import uz.gita.newmobilebankig.data.modul.responses.auth.VerifyResponse
import javax.inject.Inject

class ApiServer @Inject constructor(private var pref: LocalDatabase){


    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(getHttpClient())
        .build()

    private fun getHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addLogging()
            .addInterceptor(refreshInterceptor(pref))
            .addInterceptor(tokenInterceptor(pref))
            .build()

    }
}

fun tokenInterceptor(pref: LocalDatabase) = Interceptor { chain ->
    val request = chain.request()
    val newRequest = request.newBuilder().removeHeader("token").addHeader("token", pref.accessToken).build()
    val response = chain.proceed(newRequest)
    response
}

fun refreshInterceptor(pref: LocalDatabase) = Interceptor { chain ->
    val request = chain.request()
    val response = chain.proceed(request)

    if (response.code == 401) {
        response.close()
        val data = JSONObject()
        data.put("phone", pref.phoneNumber)
        val body = data.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val requestRefresh = request.newBuilder()
            .addHeader("refresh_token", pref.refreshToken)
            .method("POST", body)
            .url("${BASE_URL}auth/refresh")
            .build()

        val responseRefresh = chain.proceed(requestRefresh)
        if (responseRefresh.code == 401) {
            pref.screenState = ScreenState.LOGIN.amount
            return@Interceptor responseRefresh
        }
        if (responseRefresh.code == 200) {
            responseRefresh.body?.let {
                val data = Gson().fromJson(it.toString(), VerifyResponse::class.java)
                pref.accessToken = data.data.access_token
                pref.refreshToken = data.data.refresh_token
            }
            responseRefresh.close()

            val requestTwo = request.newBuilder()
                .removeHeader("token")
                .addHeader("token", pref.accessToken)
                .build()

            return@Interceptor chain.proceed(requestTwo)
        }
    }

    return@Interceptor response
}

fun OkHttpClient.Builder.addLogging(): OkHttpClient.Builder {
    if (LOGGING) {
        val logging = HttpLoggingInterceptor { message -> Timber.d(message) }
        logging.level = HttpLoggingInterceptor.Level.BODY
        addNetworkInterceptor(logging)
        addInterceptor(ChuckInterceptor(App.instance))
    }
    return this
}