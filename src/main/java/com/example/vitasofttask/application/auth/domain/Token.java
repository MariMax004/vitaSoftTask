package com.example.vitasofttask.application.auth.domain;

import com.example.vitasofttask.application.user.domain.Customer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import javax.persistence.*;

@Data
@Entity
@ToString(of = "id")
@Table(name = "token")
@RequiredArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_seq")
    @SequenceGenerator(name = "token_seq", allocationSize = 1, sequenceName = "token_seq")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private Customer user;

    @Column(name = "token")
    private String token;
}