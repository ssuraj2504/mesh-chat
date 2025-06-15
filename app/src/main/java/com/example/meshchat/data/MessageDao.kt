package com.example.meshchat.data

import androidx.room.*
import com.example.meshchat.model.MessageEntity

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)

    @Query("SELECT * FROM messages ORDER BY timestamp ASC")
    suspend fun getAllMessages(): List<MessageEntity>

    @Query("SELECT * FROM messages WHERE isDelivered = 0")
    suspend fun getUndeliveredMessages(): List<MessageEntity>

    @Update
    suspend fun updateMessage(message: MessageEntity)
} 