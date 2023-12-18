package com.example.divvydrivestaj.config

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


object RetrofitCalistir {
    fun retrofit(baseUrl: String):Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getUnsafeOkHttpClient())
            .build()
    }

    private fun getUnsafeOkHttpClient(): OkHttpClient {
        return try {

            val trustAllCerts =
                arrayOf<TrustManager>(
                    object : X509TrustManager {
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate>,
                            authType: String,
                        ) {
                        }

                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
                )

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory)
            builder.hostnameVerifier { hostname, session -> true }
                .addInterceptor(OAuthInterceptor("Basic", "TkRTU2VydmlzOmNhNTA5NGVmLWVhZTAtNGJkNS1hOTRhLTE0ZGIzYjhmMzk1MA=="))
            builder.build()

        } catch (e: Exception) {
            Log.e("SSLhata",e.message.toString())
            throw RuntimeException(e)
        }
    }


}



class OAuthInterceptor(private val tokenType: String, private val accessToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", "$tokenType $accessToken").build()

        return chain.proceed(request)
    }
    }