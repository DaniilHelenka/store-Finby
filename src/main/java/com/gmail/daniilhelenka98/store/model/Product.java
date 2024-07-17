package com.gmail.daniilhelenka98.store.model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private String model;
    private int quantity;
    private double weight;
    private double rating;
    private String category;
    private String description;
    private String color;
    private double price;
    private String imageName;
    private String imageUrl;

    @ElementCollection
    private List<String> specialFeatures;

    private String shortDescription;

}