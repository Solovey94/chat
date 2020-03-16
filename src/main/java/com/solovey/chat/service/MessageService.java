package com.solovey.chat.service;

import com.solovey.chat.dto.MessageDto;
import com.solovey.chat.model.Message;

import java.util.List;

public interface MessageService {
    Message addMessage(MessageDto messageDto);

    MessageDto add(MessageDto messageDto);

    MessageDto update(MessageDto messageDto);

    Message getMessageById(Long id);

    MessageDto findMessageById(Long id);

    List<MessageDto> findAllMessages();

    void deleteMessageById(Long id);

    MessageDto convertToDto(Message message);

    List<MessageDto> convertToDto(Iterable<Message> messages);
}
