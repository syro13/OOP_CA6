package com.dkit.oop.sd2.RocketObjects;

import com.dkit.oop.sd2.DAOs.MySqlRocketDao;
import com.dkit.oop.sd2.DAOs.RocketDaoInterface;
import com.dkit.oop.sd2.DTOs.Rockets;
import com.dkit.oop.sd2.Exceptions.DaoException;
import com.dkit.oop.sd2.Filters.SortByCapacity;

import java.util.List;
import java.util.Scanner;


public class App {
    public static RocketDaoInterface IRocketDao = new MySqlRocketDao();
    static Scanner kb = new Scanner(System.in);
    public static void main(String[] args) {
        boolean appOn = true;
        try{
            while(appOn) {
                System.out.print("""
                        1 - Display All Rockets
                        2 - Display Rocket By ID
                        3 - Delete Rocket By ID
                        4 - Insert Rocket
                        5 - Filter Rockets
                        6 - Display All Rockets (Json)
                        7 - Display Rocket By ID (Json)
                        8 - Exit
                        """);
                int choice = kb.nextInt();
                switch (choice) {
                    case 1 -> findAll();
                    case 2 -> findByID();
                    case 3 -> deleteByID();
                    case 4 -> insertRocket();
                    case 5 -> filters();
                    case 6 -> findAllJson();
                    case 7 -> findByIDJson();
                    case 8 -> appOn = false;
                }
            }
        }catch ( DaoException e ){
            e.printStackTrace();
        }
    }

    private static void findByIDJson() throws DaoException {
        System.out.print("Enter Rocket ID: ");
        int choice  = kb.nextInt();
        System.out.println(IRocketDao.findRocketsByIdJson(choice));
    }

    private static void findAllJson() throws DaoException {
        System.out.println(IRocketDao.findAllRocketsJson());
    }

    private static void filters() throws DaoException {
        SortByCapacity filter = new SortByCapacity();
        kb.nextLine();
        System.out.println("""
                1 - Sort By Capacity
                """);
        List<Rockets> rockets = IRocketDao.findRocketsUsingFilter(filter);
        if( rockets.isEmpty() ){
            System.out.println("There are no Rockets");
        }else{
            for(Rockets rocket : rockets){
                System.out.println("Rockets" + rocket.toString());
            }
        }
    }

    private static void insertRocket() throws DaoException {
        kb.nextLine();
        System.out.print("Name: ");
        String rocket_name = kb.nextLine();
        System.out.print("Manufacturer: ");
        String manufacturer = kb.nextLine();
        System.out.print("First Flight: ");
        String first_flight = kb.nextLine();
        System.out.print("Payload Capacity");
        int payload_capacity = kb.nextInt();
        IRocketDao.insertRocket(rocket_name,manufacturer,first_flight,payload_capacity);
        System.out.println("Rocket added successfully!!");
    }
    private static void deleteByID() throws DaoException {
        System.out.println("\nCall: deleteRocketByRocketID()");
        int rocket_id = 1;
        IRocketDao.deleteRocketByRocketID(rocket_id);
        System.out.println("Rocket with id: " + rocket_id + " deleted successfully");
    }

    private static void findByID() throws DaoException {
        System.out.println("\nCall: findRocketsByID()");
        int rocket_id = 15;
        Rockets rockets1 = IRocketDao.findRocketsByRocketID(rocket_id);
        if(rockets1 != null){
            System.out.println("Rocket with id: " + rocket_id + " was found: " + rockets1);
        }
    }

    public static void findAll() throws DaoException {

        System.out.println("\nCall findAllRockets()");
        List<Rockets> rockets = IRocketDao.findAllRockets();

        if( rockets.isEmpty() ){
            System.out.println("There are no Rockets");
        }else{
            for(Rockets rocket : rockets){
                System.out.println("Rockets" + rocket.toString());
            }
        }
    }
}
