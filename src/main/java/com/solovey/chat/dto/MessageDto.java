package com.solovey.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDto {
    private Long id;

    private String text;

    private Long client_id;
}
