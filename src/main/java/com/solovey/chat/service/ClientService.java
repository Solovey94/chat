package com.solovey.chat.service;

import com.solovey.chat.dto.ClientDto;
import com.solovey.chat.dto.MessageDto;
import com.solovey.chat.model.Client;

import java.util.List;


public interface ClientService {

    Client addClient(ClientDto clientDto);

    ClientDto add(ClientDto clientDto);

    Client updateClient(ClientDto clientDto);

    ClientDto update(ClientDto clientDto);

    Client getClientById(Long id);

    ClientDto findClientById(Long id);

    List<ClientDto> findAllClients();

    List<MessageDto> findMessagesByClientId(Long id);

    void deleteClientById(Long id);

    ClientDto convertToDto(Client client);

    List<ClientDto> convertToDto(Iterable<Client> clients);

}
