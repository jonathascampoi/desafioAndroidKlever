package com.example.desafio_android_klever.repository

import androidx.lifecycle.LiveData
import com.example.desafio_android_klever.data.db.entity.CadastroEntity

interface CadastroRepository {

    suspend fun insertCadastro(
        nome: String,
        email: String,
        cep: String,
        estado: String,
        cidade: String,
        bairro: String,
        rua: String
    ): Long

    suspend fun updateCadastro(
        id: Long,
        nome: String,
        email: String,
        cep: String,
        estado: String,
        cidade: String,
        bairro: String,
        rua: String
    )

    suspend fun deleteCadastro(id: Long)

    suspend fun deleteAllCadastro()

    suspend fun getAllCadastro(): List<CadastroEntity>
}