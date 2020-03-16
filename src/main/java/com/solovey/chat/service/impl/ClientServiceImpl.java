package com.solovey.chat.service.impl;

import com.solovey.chat.dto.ClientDto;
import com.solovey.chat.dto.MessageDto;
import com.solovey.chat.exception.NotFoundException;
import com.solovey.chat.model.Client;
import com.solovey.chat.model.Message;
import com.solovey.chat.repository.ClientRepository;
import com.solovey.chat.repository.MessageRepository;
import com.solovey.chat.service.ClientService;
import com.solovey.chat.service.MessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final MessageService messageService;
    private final MessageRepository messageRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, @Lazy MessageService messageService, MessageRepository messageRepository) {
        this.clientRepository = clientRepository;
        this.messageService = messageService;
        this.messageRepository =messageRepository;
    }

    @Override
    public Client addClient(ClientDto clientDto) {
        Client client;
        if (clientDto.getId() != null) {
            client = getClientById(clientDto.getId());
        } else {
            client = new Client();
            clientRepository.save(client);
        }
        BeanUtils.copyProperties(clientDto, client, "id");
        return client;
    }

    @Override
    public ClientDto add(ClientDto clientDto) {
        return convertToDto(addClient(clientDto));
    }

    @Override
    public Client updateClient(ClientDto clientDto) {
        Client client = getClientById(clientDto.getId());
        BeanUtils.copyProperties(clientDto, client, "id");
        return client;
    }

    @Override
    public ClientDto update(ClientDto clientDto) {
        return convertToDto(clientRepository.saveAndFlush(updateClient(clientDto)));
    }

    @Override
    public Client getClientById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            return client.get();
        } else {
            throw new NotFoundException("Not found element by id " + id.toString());
        }
    }

    @Override
    public ClientDto findClientById(Long id) {
        return convertToDto(getClientById(id));
    }

    @Override
    public List<ClientDto> findAllClients() {
        List<Client> clients = clientRepository.findAll();
        if (clients.size() > 0) {
            return convertToDto(clients);
        } else {
            throw new NotFoundException("Not found any elements");
        }
    }

    @Override
    public List<MessageDto> findMessagesByClientId(Long id) {
        Client client = getClientById(id);
        List<Message> messages = messageRepository.findByClientId(client.getId());
        return messageService.convertToDto(messages);
    }

    @Override
    public void deleteClientById(Long id) {
        Client client = getClientById(id);
        Set<Message> messages = client.getMessages();
        for (Message message : messages) {
            messageService.deleteMessageById(message.getId());
        }
        clientRepository.save(client);
        clientRepository.delete(client);
    }

    @Override
    public ClientDto convertToDto(Client client) {
        ClientDto clientDto = new ClientDto();
        BeanUtils.copyProperties(client, clientDto);
        return clientDto;
    }

    @Override
    public List<ClientDto> convertToDto(Iterable<Client> clients) {
        List<ClientDto> result = new ArrayList<>();
        for (Client client : clients) {
            result.add(convertToDto(client));
        }
        return result;
    }
}
