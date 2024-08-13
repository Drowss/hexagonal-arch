package com.drow.plazoleta.domain.usecase;

import com.drow.plazoleta.application.exception.UserNoPermissions;
import com.drow.plazoleta.domain.model.*;
import com.drow.plazoleta.domain.spi.ICategoryPersistencePort;
import com.drow.plazoleta.domain.spi.IDishPersistencePort;
import com.drow.plazoleta.domain.spi.IJwtHandler;
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
import org.springframework.data.domain.*;

import java.util.Arrays;

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

    @Mock
    private IJwtHandler jwtHandler;

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

        dishUseCase.saveDish(dishModel, "token");

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

    @Test
    public void testToggleDishWithPermission() {
        // Arrange
        Integer dishId = 1;
        String token = "valid-token";
        DishEntity dishEntity = new DishEntity();
        dishEntity.setId(dishId);
        dishEntity.setActive(true);
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setNit("123456789");
        restaurantModel.setCedulaPropietario("123456");
        dishEntity.setRestaurant(new RestaurantEntity());
        dishEntity.getRestaurant().setNit("123456789");

        when(dishPersistencePort.getDishById(dishId)).thenReturn(dishEntity);
        when(restaurantPersistencePort.getRestaurantByNit(anyString())).thenReturn(restaurantModel);
        when(jwtHandler.getCedulaFromToken(token)).thenReturn(123456);

        // Act
        dishUseCase.toggleDish(dishId, token);

        // Assert
        assertFalse(dishEntity.getActive());
        verify(dishPersistencePort, times(1)).saveDish(dishEntity);
    }

    @Test
    public void testToggleDishWithoutPermission() {
        // Arrange
        Integer dishId = 1;
        String token = "invalid-token";
        DishEntity dishEntity = new DishEntity();
        dishEntity.setId(dishId);
        dishEntity.setActive(true);
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setNit("123456789");
        restaurantModel.setCedulaPropietario("654321");
        dishEntity.setRestaurant(new RestaurantEntity());
        dishEntity.getRestaurant().setNit("123456789");

        when(dishPersistencePort.getDishById(dishId)).thenReturn(dishEntity);
        when(restaurantPersistencePort.getRestaurantByNit(anyString())).thenReturn(restaurantModel);
        when(jwtHandler.getCedulaFromToken(token)).thenReturn(123456);

        // Act & Assert
        assertThrows(UserNoPermissions.class, () -> {
            dishUseCase.toggleDish(dishId, token);
        });

        verify(dishPersistencePort, never()).saveDish(dishEntity);
    }

    @Test
    public void testGetDishesRestaurantWithoutCategoryFilter() {
        // Arrange
        String nit = "123456789";
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by("category").ascending());
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(1);
        categoryModel.setName("Category A");
        CategoryModel categoryModel2 = new CategoryModel();
        categoryModel2.setId(2);
        categoryModel2.setName("Category B");
        DishDomain dish1 = new DishDomain();
        dish1.setCategory(categoryModel);
        DishDomain dish2 = new DishDomain();
        dish2.setCategory(categoryModel2);
        Page<DishDomain> dishesPage = new PageImpl<>(Arrays.asList(dish1, dish2), pageable, 2);

        when(dishPersistencePort.getDishesRestaurant(nit, pageable)).thenReturn(dishesPage);

        // Act
        Page<DishDomain> result = dishUseCase.getDishesRestaurant(nit, page, size, null);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
        verify(dishPersistencePort, times(2)).getDishesRestaurant(nit, pageable);
    }

    @Test
    public void testGetDishesRestaurantWithCategoryFilter() {
        // Arrange
        String nit = "123456789";
        int page = 0;
        int size = 10;
        Integer categoryId = 1;
        Pageable pageable = PageRequest.of(page, size, Sort.by("category").ascending());
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(1);
        categoryModel.setName("Category A");
        CategoryModel categoryModel2 = new CategoryModel();
        categoryModel2.setId(2);
        categoryModel2.setName("Category B");
        DishDomain dish1 = new DishDomain();
        dish1.setCategory(categoryModel);
        DishDomain dish2 = new DishDomain();
        dish2.setCategory(categoryModel2);
        Page<DishDomain> dishesPage = new PageImpl<>(Arrays.asList(dish1, dish2), pageable, 2);

        when(dishPersistencePort.getDishesRestaurant(nit, pageable)).thenReturn(dishesPage);

        // Act
        Page<DishDomain> result = dishUseCase.getDishesRestaurant(nit, page, size, categoryId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        assertEquals(categoryId, result.getContent().get(0).getCategory().getId());
        verify(dishPersistencePort, times(1)).getDishesRestaurant(nit, pageable);
    }

}