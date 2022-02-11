package com.example.desafio_android_klever.services

class CEP {
    var cep: String? = null
    var logradouro: String? = null
    var complemento: String? = null
    var bairro: String? = null
    var localidade: String? = null
    var uf: String? = null

    override fun toString(): String {
        return """
               cep: $cep
               logradouro: $logradouro
               complemento: $complemento
               bairro: $bairro
               localidade:$localidade
               uf: $uf
               """.trimIndent()
    }
}