package com.example.desafio_android_klever.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.desafio_android_klever.data.db.entity.CadastroEntity

@Dao
interface CadastroDAO {
    @Insert
    suspend fun insert(cadastro: CadastroEntity): Long

    @Update
    suspend fun update(cadastro: CadastroEntity)

    @Query("DELETE FROM cadastro WHERE id = :id")
    suspend fun delete(id:Long)

    @Query("DELETE FROM cadastro")
    suspend fun deleteAll()

    @Query("SELECT * FROM cadastro")
    fun getAll(): LiveData<List<CadastroEntity>>
}