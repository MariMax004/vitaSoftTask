package com.example.vitasofttask.application.user.domain;


import com.example.vitasofttask.application.auth.domain.Token;
import com.example.vitasofttask.application.role.domain.Role;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="customer")
@AllArgsConstructor
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @SequenceGenerator(name = "customer_seq", allocationSize = 1, sequenceName = "customer_seq")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="last_name")
    private String lastName;

    @Column(name="birthday_date")
    private java.util.Date birthdayDate;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Token> tokenList;

    @ManyToMany
    @JoinTable(name = "customer_role", joinColumns = {@JoinColumn(name = "customer_id")},
            inverseJoinColumns = {@JoinColumn(
                    name = "role_id")})
    private List<Role> roles;
}
