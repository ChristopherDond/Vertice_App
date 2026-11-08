package com.vertice.app.data.remote.viacep

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

data class ViaCepAddress(
    val cep: String,
    val street: String,
    val district: String,
    val city: String,
    val state: String,
    val complement: String = "",
)

object ViaCepClient {

    private val okHttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    fun parseResponse(jsonBody: String, defaultCep: String = ""): ViaCepAddress? {
        return try {
            val json = Json.parseToJsonElement(jsonBody).jsonObject
            val isError = json["erro"]?.jsonPrimitive?.content == "true"
            if (isError) return null

            ViaCepAddress(
                cep = json["cep"].asString().ifBlank { defaultCep },
                street = json["logradouro"].asString(),
                district = json["bairro"].asString(),
                city = json["localidade"].asString(),
                state = json["uf"].asString(),
                complement = json["complemento"].asString(),
            )
        } catch (_: Exception) {
            null
        }
    }

    private fun JsonElement?.asString(): String =
        if (this == null || this is JsonNull) "" else this.jsonPrimitive.content

    suspend fun getAddressByCep(cep: String): ViaCepAddress? = withContext(Dispatchers.IO) {
        val cleanCep = cep.filter { it.isDigit() }
        if (cleanCep.length != 8) return@withContext null

        try {
            val request = Request.Builder()
                .url("https://viacep.com.br/ws/$cleanCep/json/")
                .get()
                .build()

            okHttp.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@withContext null
                val body = response.body?.string() ?: return@withContext null
                parseResponse(body, cleanCep)
            }
        } catch (_: Exception) {
            null
        }
    }
}
