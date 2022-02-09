package com.example.desafio_android_klever.repository

import androidx.lifecycle.LiveData
import com.example.desafio_android_klever.data.db.entity.CadastroEntity

interface CadastroRepository {

    suspend fun insertCadastro(nome: String, email: String): Long

    suspend fun updateCadastro(id: Long, nome: String, email: String)

    suspend fun deleteCadastro(id: Long)

    suspend fun deleteAllCadastro()

    fun getAllCadastro(): LiveData<List<CadastroEntity>>
}