package com.dkit.oop.sd2.BusinessObjects;

import com.dkit.oop.sd2.DAOs.MySqlRocketDao;
import com.dkit.oop.sd2.DAOs.RocketDaoInterface;
import com.dkit.oop.sd2.DTOs.Rockets;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.util.List;
import java.util.Scanner;


public class Main {
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
                        5 - Exit
                        """);
                int choice = kb.nextInt();
                switch (choice) {
                    case 1:
                        findAll();
                        break;
                    case 2:
                        findByID();
                        break;
                    case 3:
                        deleteByID();
                        break;
                    case 4:
                        insertRocket();
                        break;
                    case 5:
                        appOn = false;
                        break;
                }
            }
        }catch ( DaoException e ){
            e.printStackTrace();
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
        int rocket_id = 1;
        Rockets rockets1 = IRocketDao.findRocketsByRocketID(rocket_id);
        if(rockets1 != null){
            System.out.println("Rocket with id: " + rocket_id + " was found: " + rockets1);
        }else{
            System.out.println("Rocket_ID" + rocket_id + "is not valid");
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
