package com.dietplanner.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountid")
    private Long id;

    @Column(name = "accounttype", nullable = false)
    private String accounttype; // "USER" or "ADMIN"

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "caloricintakegoal")
    private Integer caloricIntakeGoal;

    @Column(name = "dietpreference")
    private String dietPreference; // Matches your VARCHAR(30) column

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password; // TODO: Encrypt before saving
}
