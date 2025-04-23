package com.example.meshchat.db

import androidx.room.*

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMessage(message: MessageEntity)

    @Query("SELECT * FROM messages ORDER BY timestamp ASC")
    suspend fun getAllMessages(): List<MessageEntity>

    @Query("SELECT * FROM messages WHERE isDelivered = 0")
    suspend fun getUndeliveredMessages(): List<MessageEntity>

    @Update
    suspend fun updateMessage(message: MessageEntity)
}
