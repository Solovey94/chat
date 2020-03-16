package com.solovey.chat.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @OneToMany(
            mappedBy = "client",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    private Set<Message> messages;

}
