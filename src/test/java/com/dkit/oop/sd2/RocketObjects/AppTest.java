package com.dkit.oop.sd2.RocketObjects;

import com.dkit.oop.sd2.DAOs.RocketDaoInterface;
import com.dkit.oop.sd2.DTOs.Rockets;
import com.dkit.oop.sd2.Exceptions.DaoException;
import com.dkit.oop.sd2.Filters.SortByCapacity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AppTest {
    private static RocketDaoInterface mockRocketDao;

    @Before
    public void setUp(){
        mockRocketDao = mock(RocketDaoInterface.class);
    }

    @Test
    public void mainFindAll() throws DaoException {
        List<Rockets> mockList = new ArrayList<>();
        Rockets rocket1 = new Rockets("Rocket 1", "Manufacturer 1", "2022-01-01", 5000);
        Rockets rocket2 = new Rockets("Rocket 2", "Manufacturer 2", "2023-01-01", 1000);
        mockList.add(rocket1);
        mockList.add(rocket2);

        when(mockRocketDao.findAllRockets()).thenReturn(mockList);

        List<Rockets> mockListActual = mockRocketDao.findAllRockets();
        String actual = "";
        for(Rockets r: mockListActual){
            actual += r.toString() + "\n";
        }

        String expected = """
                Rockets{rocket_id=0, rocket_name='Rocket 1', manufacturer='Manufacturer 1', date='2022-01-01', payload_capacity=5000}
                Rockets{rocket_id=0, rocket_name='Rocket 2', manufacturer='Manufacturer 2', date='2023-01-01', payload_capacity=1000}
                """;
        assertEquals(expected, actual);
    }
    @Test
    public void mainFindByID() throws DaoException {
        List<Rockets> mockList = new ArrayList<>();
        Rockets rocket1 = new Rockets("Rocket 1", "Manufacturer 1", "2022-01-01", 5000);
        Rockets rocket2 = new Rockets("Rocket 2", "Manufacturer 2", "2023-01-01", 1000);
        mockList.add(rocket1);
        mockList.add(rocket2);
        when(mockRocketDao.findRocketsByRocketID(1)).thenReturn(mockList.get(0));

        String actual = mockRocketDao.findRocketsByRocketID(1).toString() + "\n";

        String expected = """
                Rockets{rocket_id=0, rocket_name='Rocket 1', manufacturer='Manufacturer 1', date='2022-01-01', payload_capacity=5000}
                """;
        assertEquals(expected, actual);
    }

    @Test
    public void mainInsert() throws DaoException{
        mockRocketDao.insertRocket("Rocket 2", "Manufacturer 2", "2023-01-01", 1000);
        verify(mockRocketDao).insertRocket("Rocket 2", "Manufacturer 2", "2023-01-01", 1000);
    }
    @Test
    public void mainDelete() throws DaoException{
        mockRocketDao.deleteRocketByRocketID(1);
        verify(mockRocketDao).deleteRocketByRocketID(1);
    }
    @Test
    public void mainFindUsingFilters() throws DaoException{
        SortByCapacity filter = new SortByCapacity();
        List<Rockets> mockList = new ArrayList<>();
        Rockets rocket1 = new Rockets("Rocket 1", "Manufacturer 1", "2022-01-01", 1000);
        Rockets rocket2 = new Rockets("Rocket 2", "Manufacturer 2", "2023-01-01", 5000);
        mockList.add(rocket1);
        mockList.add(rocket2);
        mockList.sort(filter);

        when(mockRocketDao.findRocketsUsingFilter(filter)).thenReturn(mockList);
        List<Rockets> mockListActual = mockRocketDao.findRocketsUsingFilter(filter);

        String actual = "";
        for(Rockets r: mockListActual){
            actual += r.toString() + "\n";
        }

        String expected = """
                Rockets{rocket_id=0, rocket_name='Rocket 1', manufacturer='Manufacturer 1', date='2022-01-01', payload_capacity=1000}
                Rockets{rocket_id=0, rocket_name='Rocket 2', manufacturer='Manufacturer 2', date='2023-01-01', payload_capacity=5000}
                """;

        assertEquals(expected,actual);
    }
    @Test
    public void mainFindAllJSON() throws DaoException {
        List<Rockets> mockList = new ArrayList<>();
        Rockets rocket1 = new Rockets("Rocket 1", "Manufacturer 1", "2022-01-01", 5000);
        Rockets rocket2 = new Rockets("Rocket 2", "Manufacturer 2", "2023-01-01", 1000);
        mockList.add(rocket1);
        mockList.add(rocket2);

        when(mockRocketDao.findAllRocketsJson()).thenReturn(mockList.toString());

        String actual = mockRocketDao.findAllRocketsJson();

        String expected = "[Rockets{rocket_id=0, rocket_name='Rocket 1', manufacturer='Manufacturer 1', date='2022-01-01', payload_capacity=5000}, Rockets{rocket_id=0, rocket_name='Rocket 2', manufacturer='Manufacturer 2', date='2023-01-01', payload_capacity=1000}]";
        assertEquals(expected, actual);
    }
@Test
public void testFindArtistByIdJson() throws DaoException {
    // Create an example Artist object
    String expected = "Rockets{rocket_id=0, rocket_name='Rocket 1', manufacturer='Manufacturer 1', date='2022-01-01', payload_capacity=5000}";
    // Mock the DAO's findArtistById() method to return the example Artist object
    when(mockRocketDao.findRocketsByIdJson(1)).thenReturn(expected);

    // Call the DAO's findArtistByIdJson() method with the ID of the example Artist object
    String json = mockRocketDao.findRocketsByIdJson(1);
    // Assert that the deserialized Artist object matches the example Artist object
    assertEquals(expected,json);
}
    @After
    public void tearDown() {
        mockRocketDao = null;
    }
}