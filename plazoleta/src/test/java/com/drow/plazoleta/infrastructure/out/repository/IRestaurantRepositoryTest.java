package com.drow.plazoleta.infrastructure.out.repository;

import com.drow.plazoleta.infrastructure.out.entity.RestaurantEntity;
import com.drow.plazoleta.infrastructure.out.repository.IRestaurantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class IRestaurantRepositoryTest {

    @Autowired
    private IRestaurantRepository restaurantJpaAdapter;

    @Test
    void restaurantRepository_SaveRestaurant_ReturnRestaurantEntity() {
        // Arrange
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setNit("1234");
        restaurantEntity.setNombre("Restaurant");
        restaurantEntity.setDireccion("Calle 123");
        restaurantEntity.setTelefono("1234567");
        restaurantEntity.setUrlLogo("https://www.google.com");
        restaurantEntity.setIdPropietario(1);
        // Act
        RestaurantEntity savedRestaurant = restaurantJpaAdapter.save(restaurantEntity);
        // Assert
        assertThat(savedRestaurant).isNotNull();
        assertThat(savedRestaurant.getNit()).isEqualTo(restaurantEntity.getNit());
    }
}