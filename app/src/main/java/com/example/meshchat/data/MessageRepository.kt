package com.example.meshchat.data

import com.example.meshchat.model.MessageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MessageRepository(private val messageDao: MessageDao) {

    suspend fun insertMessage(message: MessageEntity) = withContext(Dispatchers.IO) {
        messageDao.insertMessage(message)
    }

    suspend fun getAllMessages(): List<MessageEntity> = withContext(Dispatchers.IO) {
        messageDao.getAllMessages()
    }

    suspend fun getUndeliveredMessages(): List<MessageEntity> = withContext(Dispatchers.IO) {
        messageDao.getUndeliveredMessages()
    }

    suspend fun updateMessage(message: MessageEntity) = withContext(Dispatchers.IO) {
        messageDao.updateMessage(message)
    }

    suspend fun markMessageAsDelivered(messageId: Int) = withContext(Dispatchers.IO) {
        val message = messageDao.getAllMessages().find { it.id == messageId }
        message?.let {
            messageDao.updateMessage(it.copy(isDelivered = true))
        }
    }
} 