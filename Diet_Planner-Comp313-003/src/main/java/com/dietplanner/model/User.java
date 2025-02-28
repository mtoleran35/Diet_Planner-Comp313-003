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

    // alice - Generated getters and setters due to Lombok absence
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Integer getCaloricIntakeGoal() {
		return caloricIntakeGoal;
	}

	public void setCaloricIntakeGoal(Integer caloricIntakeGoal) {
		this.caloricIntakeGoal = caloricIntakeGoal;
	}

	public String getDietPreference() {
		return dietPreference;
	}

	public void setDietPreference(String dietPreference) {
		this.dietPreference = dietPreference;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
