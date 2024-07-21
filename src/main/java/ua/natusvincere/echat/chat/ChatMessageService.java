package ua.natusvincere.echat.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.natusvincere.echat.chatroom.ChatRoomService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;

    public ChatMessage saveMessage(ChatMessage message) {
        String chatId = chatRoomService.getChatId(message.getSenderId(), message.getRecipientId(), true)
                .orElseThrow();// TODO CREATE EXCEPTION
        message.setChatId(chatId);
        repository.save(message);
        return message;
    }

    public List<ChatMessage> findChatMessages(UUID senderId, UUID recipientId) {
        Optional<String> chatId = chatRoomService.getChatId(senderId, recipientId, false);
        return chatId.map(repository::findAllByChatId).orElse(new ArrayList<>());
    }
}
