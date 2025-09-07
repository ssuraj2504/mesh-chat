package com.example.meshchat.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0005J\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0005J\u0019\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u0019\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\n\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\f"}, d2 = {"Lcom/example/meshchat/data/MessageDao;", "", "getAllMessages", "", "Lcom/example/meshchat/model/MessageEntity;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUndeliveredMessages", "insertMessage", "", "message", "(Lcom/example/meshchat/model/MessageEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateMessage", "app_debug"})
@androidx.room.Dao
public abstract interface MessageDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertMessage(@org.jetbrains.annotations.NotNull
    com.example.meshchat.model.MessageEntity message, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM messages ORDER BY timestamp ASC")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getAllMessages(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.example.meshchat.model.MessageEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM messages WHERE isDelivered = 0")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getUndeliveredMessages(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.example.meshchat.model.MessageEntity>> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateMessage(@org.jetbrains.annotations.NotNull
    com.example.meshchat.model.MessageEntity message, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}