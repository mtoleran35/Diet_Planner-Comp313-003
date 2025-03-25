package com.dietplanner;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dietplanner.model.User;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setAccounttype("USER");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setWeight(70.5);
        user.setCaloricIntakeGoal(2000);
        user.setDietPreference("Vegetarian");
        user.setUsername("johndoe");
        user.setPassword("password123"); // TODO: Encrypt before saving
    }

    @Test
    public void testGetFullname() {
        assertEquals("John Doe", user.getFullname());
    }

    @Test
    public void testGetAndSetId() {
        user.setId(2L);
        assertEquals(2L, user.getId());
    }

    @Test
    public void testGetAndSetAccounttype() {
        user.setAccounttype("ADMIN");
        assertEquals("ADMIN", user.getAccounttype());
    }

    @Test
    public void testGetAndSetFirstName() {
        user.setFirstName("Jane");
        assertEquals("Jane", user.getFirstName());
    }

    @Test
    public void testGetAndSetLastName() {
        user.setLastName("Smith");
        assertEquals("Smith", user.getLastName());
    }

    @Test
    public void testGetAndSetWeight() {
        user.setWeight(75.0);
        assertEquals(75.0, user.getWeight());
    }

    @Test
    public void testGetAndSetCaloricIntakeGoal() {
        user.setCaloricIntakeGoal(2500);
        assertEquals(2500, user.getCaloricIntakeGoal());
    }

    @Test
    public void testGetAndSetDietPreference() {
        user.setDietPreference("Vegan");
        assertEquals("Vegan", user.getDietPreference());
    }

    @Test
    public void testGetAndSetUsername() {
        user.setUsername("janesmith");
        assertEquals("janesmith", user.getUsername());
    }

    @Test
    public void testGetAndSetPassword() {
        user.setPassword("newpassword123"); // TODO: Encrypt before saving
        assertEquals("newpassword123", user.getPassword());
    }
}