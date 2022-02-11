package com.example.desafio_android_klever.services

import android.os.AsyncTask
import com.google.gson.Gson
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*


class HttpService(private val cep: String?) :
    AsyncTask<Void?, Void?, CEP>() {
    override fun doInBackground(vararg voids: Void?): CEP {
        val resposta = StringBuilder()
        if (cep != null && cep.length == 8) {
            try {
                val url = URL("https://viacep.com.br/ws/" + cep + "/json/")
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.setRequestMethod("GET")
                connection.setRequestProperty("Content-type", "application/json")
                connection.setRequestProperty("Accept", "application/json")
                connection.setDoOutput(true)
                connection.setConnectTimeout(5000)
                connection.connect()
                val scanner = Scanner(url.openStream())
                while (scanner.hasNext()) {
                    resposta.append(scanner.next())
                }
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return Gson().fromJson(resposta.toString(), CEP::class.java)
    }
}