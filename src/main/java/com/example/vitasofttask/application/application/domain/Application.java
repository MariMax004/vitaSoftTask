package com.example.vitasofttask.application.application.domain;

import com.example.vitasofttask.application.application.model.ApplicationStatus;
import com.example.vitasofttask.application.user.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name="application")
@AllArgsConstructor
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_seq")
    @SequenceGenerator(name = "app_seq", allocationSize = 1, sequenceName = "app_seq")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name="date_create")
    private java.util.Date dateCreate;

    @Column(name="comment")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer user;

}
