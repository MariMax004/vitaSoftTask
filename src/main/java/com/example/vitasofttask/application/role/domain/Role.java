package com.example.vitasofttask.application.role.domain;


import com.example.vitasofttask.application.role.model.RoleType;
import com.example.vitasofttask.application.user.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(name = "role")
@AllArgsConstructor
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    @SequenceGenerator(name = "role_seq", allocationSize = 1, sequenceName = "role_seq")
    private Long id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private RoleType name;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, mappedBy = "roles")
    private List<Customer> customers;

}
