package com.example.desafio_android_klever.repository

import androidx.lifecycle.LiveData
import com.example.desafio_android_klever.data.db.dao.CadastroDAO
import com.example.desafio_android_klever.data.db.entity.CadastroEntity

class DatabaseSource(private val cadastroDAO: CadastroDAO) : CadastroRepository {

    override suspend fun insertCadastro(
        nome: String,
        email: String,
        cep: String,
        estado: String,
        cidade: String,
        bairro: String,
        rua: String
    ): Long {
        val cadastro = CadastroEntity(
            nome = nome,
            email = email,
            cep = cep,
            estado = estado,
            cidade = cidade,
            bairro = bairro,
            rua = rua
        )
        return cadastroDAO.insert(cadastro)
    }

    override suspend fun updateCadastro(
        id: Long,
        nome: String,
        email: String,
        cep: String,
        estado: String,
        cidade: String,
        bairro: String,
        rua: String
    ) {
        val cadastro = CadastroEntity(
            id = id,
            nome = nome,
            email = email,
            cep = cep,
            estado = estado,
            cidade = cidade,
            bairro = bairro,
            rua = rua
        )
        cadastroDAO.update(cadastro)
    }

    override suspend fun deleteCadastro(id: Long) {
        cadastroDAO.delete(id)
    }

    override suspend fun deleteAllCadastro() {
        cadastroDAO.deleteAll()
    }

    override suspend fun getAllCadastro(): List<CadastroEntity> {
        return cadastroDAO.getAll()
    }
}