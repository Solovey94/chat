package com.solovey.chat.controller;

import com.solovey.chat.dto.ClientDto;
import com.solovey.chat.dto.MessageDto;
import com.solovey.chat.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clients")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ClientDto addClient(@RequestBody ClientDto clientDto) {
        return clientService.add(clientDto);
    }

    @PutMapping("/{id}")
    public ClientDto updateClient(
            @PathVariable Long id,
            @Validated @RequestBody ClientDto clientDto
    ) {
        clientDto.setId(id);
        return clientService.update(clientDto);
    }

    @GetMapping
    public List<ClientDto> findAllClients() {
        return clientService.findAllClients();
    }

    @GetMapping("/{id}")
    public ClientDto findClientById(@PathVariable Long id) {
        return clientService.findClientById(id);
    }

    @GetMapping("/{id}/messages")
    public List<MessageDto> findMessagesByClientId(@PathVariable Long id) {
        return clientService.findMessagesByClientId(id);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClientById(id);
    }
}
