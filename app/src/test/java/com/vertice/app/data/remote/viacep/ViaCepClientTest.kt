package com.vertice.app.data.remote.viacep

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class ViaCepClientTest {

    @Test
    fun parseResponse_validAddress_returnsParsedObject() {
        val json = """
            {
              "cep": "01001-000",
              "logradouro": "Praça da Sé",
              "complemento": "lado ímpar",
              "bairro": "Sé",
              "localidade": "São Paulo",
              "uf": "SP",
              "ibge": "3550308",
              "gia": "1004",
              "ddd": "11",
              "siafi": "7107"
            }
        """.trimIndent()

        val result = ViaCepClient.parseResponse(json, "01001000")
        assertNotNull(result)
        assertEquals("01001-000", result?.cep)
        assertEquals("Praça da Sé", result?.street)
        assertEquals("Sé", result?.district)
        assertEquals("São Paulo", result?.city)
        assertEquals("SP", result?.state)
        assertEquals("lado ímpar", result?.complement)
    }

    @Test
    fun parseResponse_errorString_returnsNull() {
        val json = """{"erro": "true"}"""
        val result = ViaCepClient.parseResponse(json, "99999999")
        assertNull(result)
    }

    @Test
    fun parseResponse_errorBoolean_returnsNull() {
        val json = """{"erro": true}"""
        val result = ViaCepClient.parseResponse(json, "99999999")
        assertNull(result)
    }

    @Test
    fun parseResponse_malformedJson_returnsNull() {
        val json = "{ invalid json"
        val result = ViaCepClient.parseResponse(json, "00000000")
        assertNull(result)
    }
}
