package com.sandeep.E_Commerce_Project.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;

@Data
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    private String name;
    private String description;


    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal price;
    private Integer stock;


    private String imageUrl;     // product image
    private String category;     // example: ELECTRONICS, FASHION, SHOES
}
