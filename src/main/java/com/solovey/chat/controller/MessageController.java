package com.solovey.chat.controller;

import com.solovey.chat.dto.MessageDto;
import com.solovey.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("messages")
public class MessageController {
    MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public MessageDto create(@RequestBody MessageDto messageDto) {
        return messageService.add(messageDto);
    }

    @GetMapping
    public List<MessageDto> findAll() {
        return messageService.findAllMessages();
    }

    @GetMapping("/{id}")
    public MessageDto findById(@PathVariable Long id) {
        return messageService.findMessageById(id);
    }

    @PutMapping("/{id}")
    public MessageDto update(
            @PathVariable Long id,
            @Validated @RequestBody MessageDto messageDto
    ) {
        messageDto.setId(id);
        return messageService.update(messageDto);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        messageService.deleteMessageById(id);
    }

    @MessageMapping("/changeMessage")
    @SendTo("/topic/activity")
    public MessageDto change(MessageDto messageDto) {
        return messageService.update(messageDto);
    }

}
