package com.mealserviceapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "meal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mealid")
    private Long mealId;

    @Column(name = "mealname", nullable = false)
    private String mealName;

    @Column(name = "calories")
    private int calories;

    @Column(name = "carbohydrate")
    private int carbohydrate;

    @Column(name = "fat")
    private int fat;

    @Column(name = "protein")
    private int protein;

    @Column(name = "status")
    private String status;

	  
    
}
