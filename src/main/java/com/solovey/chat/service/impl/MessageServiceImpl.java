package com.solovey.chat.service.impl;

import com.solovey.chat.dto.MessageDto;
import com.solovey.chat.exception.NotFoundException;
import com.solovey.chat.model.Client;
import com.solovey.chat.model.Message;
import com.solovey.chat.repository.MessageRepository;
import com.solovey.chat.service.ClientService;
import com.solovey.chat.service.MessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ClientService clientService;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, ClientService clientService) {
        this.messageRepository = messageRepository;
        this.clientService = clientService;
    }

    @Override
    public Message addMessage(MessageDto messageDto) {
        Message message;
        if (messageDto.getId() != null) {
            message = getMessageById(messageDto.getId());
        } else {
            message = new Message();
            messageRepository.save(message);
        }
        Client client = clientService.getClientById(messageDto.getClient_id());
        BeanUtils.copyProperties(messageDto, message, "id");
        message.setClient(client);
        List<Message> messagesByClient = messageRepository.findByClientId(client.getId());
        return message;
    }

    @Override
    public MessageDto add(MessageDto messageDto) {
        return convertToDto(messageRepository.saveAndFlush(addMessage(messageDto)));
    }

    @Override
    public MessageDto update(MessageDto messageDto) {
        Message message;
        if (messageDto.getId() != null) {
            message = getMessageById(messageDto.getId());
        } else {
            message = new Message();
            messageRepository.save(message);
        }
        Client client = clientService.getClientById(messageDto.getClient_id());
        BeanUtils.copyProperties(messageDto, message, "id");
        message.setClient(client);
        List<Message> messagesByClient = messageRepository.findByClientId(client.getId());
        return convertToDto(message);
    }

    @Override
    public Message getMessageById(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent()) {
            return message.get();
        }
        throw new NotFoundException("Not found element by id " + id.toString());
    }

    @Override
    public MessageDto findMessageById(Long id) {
        return convertToDto(getMessageById(id));
    }

    @Override
    public List<MessageDto> findAllMessages() {
        List<Message> messages = messageRepository.findAll();
        if (messages.size() > 0) {
            return convertToDto(messages);
        }
        throw new NotFoundException("Not found any elements");
    }

    @Override
    public void deleteMessageById(Long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public MessageDto convertToDto(Message message) {
        MessageDto messageDto = new MessageDto();
        BeanUtils.copyProperties(message, messageDto);
        Client client = message.getClient();
        messageDto.setClient_id(clientService.convertToDto(client).getId());
        return messageDto;
    }

    @Override
    public List<MessageDto> convertToDto(Iterable<Message> messages) {
        List<MessageDto> result = new ArrayList<>();
        for (Message message : messages) {
            result.add(convertToDto(message));
        }
        return result;
    }
}
