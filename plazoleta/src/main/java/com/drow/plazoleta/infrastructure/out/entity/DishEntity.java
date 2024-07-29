package com.drow.plazoleta.infrastructure.out.entity;

import com.drow.plazoleta.domain.model.CategoryModel;
import com.drow.plazoleta.domain.model.RestaurantModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "dishTable")
@AllArgsConstructor
@NoArgsConstructor
public class DishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private Boolean active;
    private Float price;
    private String description;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
    @ManyToOne
    @JoinColumn(name = "restaurant_nit")
    private RestaurantEntity restaurant;
}
