package com.drow.plazoleta.domain.usecase;

import com.drow.plazoleta.domain.model.CategoryModel;
import com.drow.plazoleta.domain.model.DishModel;
import com.drow.plazoleta.domain.model.ModifyDishModel;
import com.drow.plazoleta.domain.model.RestaurantModel;
import com.drow.plazoleta.domain.spi.ICategoryPersistencePort;
import com.drow.plazoleta.domain.spi.IDishPersistencePort;
import com.drow.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.drow.plazoleta.infrastructure.out.entity.CategoryEntity;
import com.drow.plazoleta.infrastructure.out.entity.DishEntity;
import com.drow.plazoleta.infrastructure.out.entity.RestaurantEntity;
import com.drow.plazoleta.infrastructure.out.mapper.ICategoryEntityMapper;
import com.drow.plazoleta.infrastructure.out.mapper.IRestaurantEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishUseCaseTest {

    @Mock
    private IDishPersistencePort dishPersistencePort;
    @Mock
    private ICategoryPersistencePort categoryPersistencePort;
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    @Mock
    private ICategoryEntityMapper categoryEntityMapper;
    @Mock
    private IRestaurantEntityMapper restaurantEntityMapper;

    @InjectMocks
    private DishUseCase dishUseCase;

    private DishModel dishModel;
    private CategoryModel categoryModel;
    private RestaurantModel restaurantModel;

    @BeforeEach
    void setUp() {
        dishModel = new DishModel();
        dishModel.setName("Pizza");
        dishModel.setPrice(10000F);
        dishModel.setCategoryId(1);
        dishModel.setRestaurantNit("1234");
        dishModel.setDescription("Pizza de peperoni");
        dishModel.setImageUrl("https://www.google.com");

        categoryModel = new CategoryModel();
        categoryModel.setId(1);
        categoryModel.setName("Italiana");

        restaurantModel = new RestaurantModel();
        restaurantModel.setDireccion("Calle 123");
        restaurantModel.setNit("413213");
        restaurantModel.setNit("1234");
        restaurantModel.setNombre("Restaurant");
        restaurantModel.setTelefono("1234567");
        restaurantModel.setUrlLogo("https://www.google.com");
    }

    @Test
    void saveDish_ShouldCallSaveDishInPersistencePort() {
        when(categoryPersistencePort.getCategoryById(dishModel.getCategoryId())).thenReturn(categoryModel);
        when(restaurantPersistencePort.getRestaurantByNit(dishModel.getRestaurantNit())).thenReturn(restaurantModel);
        when(categoryEntityMapper.toEntity(categoryModel)).thenReturn(new CategoryEntity());
        when(restaurantEntityMapper.toEntity(restaurantModel)).thenReturn(new RestaurantEntity());

        dishUseCase.saveDish(dishModel);

        verify(dishPersistencePort, times(1)).saveDish(any(DishEntity.class));
    }

    @Test
    void saveDish_ShouldThrowExceptionWhenRestaurantAndCategoryNotFound() {
        verify(dishPersistencePort, times(0)).saveDish(any(DishEntity.class));
    }

    @Test
    void modifyDish_shouldUpdatePriceAndDescription() {
        // Arrange
        ModifyDishModel modifyDishModel = new ModifyDishModel();
        modifyDishModel.setId(1);
        modifyDishModel.setPrice(100F);
        modifyDishModel.setDescription("New Description");

        DishEntity dishEntity = new DishEntity();
        dishEntity.setId(1);
        dishEntity.setPrice(50F);
        dishEntity.setDescription("Old Description");

        when(dishPersistencePort.getDishById(1)).thenReturn(dishEntity);

        // Act
        dishUseCase.modifyDish(modifyDishModel);

        // Assert
        verify(dishPersistencePort).getDishById(1);
        verify(dishPersistencePort).saveDish(dishEntity);
        assertEquals(100F, dishEntity.getPrice());
        assertEquals("New Description", dishEntity.getDescription());
    }

    @Test
    void modifyDish_shouldNotUpdatePriceIfNull() {
        // Arrange
        ModifyDishModel modifyDishModel = new ModifyDishModel();
        modifyDishModel.setId(1);
        modifyDishModel.setDescription("New Description");

        DishEntity dishEntity = new DishEntity();
        dishEntity.setId(1);
        dishEntity.setPrice(50F);
        dishEntity.setDescription("Old Description");

        when(dishPersistencePort.getDishById(1)).thenReturn(dishEntity);

        // Act
        dishUseCase.modifyDish(modifyDishModel);

        // Assert
        verify(dishPersistencePort).getDishById(1);
        verify(dishPersistencePort).saveDish(dishEntity);
        assertEquals(50F, dishEntity.getPrice()); // Price should remain unchanged
        assertEquals("New Description", dishEntity.getDescription());
    }

    @Test
    void modifyDish_shouldNotUpdateDescriptionIfNull() {
        // Arrange
        ModifyDishModel modifyDishModel = new ModifyDishModel();
        modifyDishModel.setId(1);
        modifyDishModel.setPrice(100F);

        DishEntity dishEntity = new DishEntity();
        dishEntity.setId(1);
        dishEntity.setPrice(50F);
        dishEntity.setDescription("Old Description");

        when(dishPersistencePort.getDishById(1)).thenReturn(dishEntity);

        // Act
        dishUseCase.modifyDish(modifyDishModel);

        // Assert
        verify(dishPersistencePort).getDishById(1);
        verify(dishPersistencePort).saveDish(dishEntity);
        assertEquals(100F, dishEntity.getPrice());
        assertEquals("Old Description", dishEntity.getDescription()); // Description should remain unchanged
    }

}