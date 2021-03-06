import com.sun.org.apache.bcel.internal.generic.LoadClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    private void createRestaurant(String name, String location) {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant(name, location, openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        createRestaurant("Persian Darbar", "Delhi");
        Restaurant spyRes = Mockito.spy(restaurant);
        Mockito.when(spyRes.getCurrentTime()).thenReturn(LocalTime.parse("11:30:00"));
        assertTrue(spyRes.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        createRestaurant("Persian Darbar", "Delhi");
        Restaurant spyRes = Mockito.spy(restaurant);
        Mockito.when(spyRes.getCurrentTime()).thenReturn(LocalTime.parse("09:30:00"));
        assertFalse(spyRes.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        createRestaurant("Amelie's cafe", "Chennai");

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }



    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        createRestaurant("Nick's Pizza", "Mumbai");

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        createRestaurant("Pizza Hut", "Pune");

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void get_order_value_will_return_correct_value_for_list_of_item_names(){
        createRestaurant("Pizza Express", "Noida");
        List<String> orderNames = new ArrayList<>();
        orderNames.add("Sweet corn soup");
        orderNames.add("Vegetable lasagne");
        assertEquals(388, restaurant.getOrderValue(orderNames));
    }

    @Test
    public void get_order_value_will_return_different_value_for_list_of_item_names(){
        createRestaurant("Red Chilli", "Noida");
        List<String> orderNames = new ArrayList<>();
        orderNames.add("Sweet corn soup");
        assertNotEquals(388, restaurant.getOrderValue(orderNames));
    }

}