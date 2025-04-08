package com.mealserviceapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
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

    @NotBlank(message = "Meal name is required")
    @Column(name = "mealname", nullable = false)
    private String mealName;

    @PositiveOrZero(message = "Calories must be a positive number")
    @Column(name = "calories")
    private int calories;

    @PositiveOrZero(message = "Carbohydrate must be a positive number")
    @Column(name = "carbohydrate")
    private int carbohydrate;

    @PositiveOrZero(message = "Fat must be a positive number")    
    @Column(name = "fat")
    private int fat;

    @PositiveOrZero(message = "Protein must be a positive number")
    @Column(name = "protein")
    private int protein;

    @Column(name = "status")
    private String status;
}
