package com.dkit.oop.sd2.Clients;

import com.dkit.oop.sd2.DTOs.Rockets;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    public boolean appOn = true;
    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void start() {
        Gson gsonParser = new Gson();
        Scanner in = new Scanner(System.in);
        try {

                Socket socket = new Socket("localhost", 8080);  // connect to server socket
                System.out.println("Client: Port# of this client : " + socket.getLocalPort());
                System.out.println("Client: Port# of Server :" + socket.getPort());

                System.out.println("Client message: The Client is running and has connected to the server");
            while(appOn) {
                System.out.print("""
                        1 - Display Rocket By ID
                        2 - Display All Rockets (Json)
                        3 - Insert Rocket
                        4 - Delete Rocket By ID
                        5 - Exit
                        """);
                String command = in.nextLine();

                if (command.startsWith("1")) {
                    System.out.println("Enter Rocket ID: ");
                    command = command + " " + in.nextLine();
                } else if (command.startsWith("3")) {
                    System.out.print("Name: ");
                    command = command + " " + in.nextLine() + ",";
                    System.out.print("Manufacturer: ");
                    command = command + " " + in.nextLine() + ",";
                    System.out.print("First Flight: ");
                    command = command + " " + in.nextLine() + ",";
                    System.out.print("Payload Capacity: ");
                    command = command + " " + in.nextLine() + ",";
                } else if (command.startsWith("4")) {

                    System.out.println("Enter Rocket ID: ");
                    int rocketId = in.nextInt();
                    command = command + " " + rocketId;
                }
                OutputStream os = socket.getOutputStream();
                PrintWriter socketWriter = new PrintWriter(os, true);

                socketWriter.println(command);

                Scanner inStream = new Scanner(socket.getInputStream());  // wait for, and retrieve the reply
                if (command.startsWith("1"))   //we expect the server to return a time (in milliseconds)
                {
                    String gsonString = inStream.nextLine();
                    Rockets rocket = gsonParser.fromJson(gsonString, Rockets.class);
                    System.out.println(rocket);
                } else if (command.startsWith("2"))                           // the user has entered the Echo command or an invalid command
                {
                    List<Rockets> rocketsList = new ArrayList<>();
                    String[] stringArray = inStream.nextLine().split("}");
                    for (int i = 0; i < stringArray.length - 1; i++) {
                        stringArray[i] = stringArray[i].substring(1) + "}";
                        rocketsList.add(gsonParser.fromJson(stringArray[i], Rockets.class));
                    }
                    for (Rockets rocket : rocketsList) {
                        System.out.println(rocket.toString());
                    }
                } else if (command.startsWith("3")) {
                    System.out.println("Success!!");

                } else if (command.startsWith("4")) {
                    String gsonString = inStream.nextLine();
                    if (gsonString != null) {
                        System.out.println("Rocket deleted successfully");
                        in.nextLine();
                    } else {
                        System.out.println("Rocket NOT deleted successfully");
                        in.nextLine();
                    }
                } else if (command.startsWith("5")) {
                    socketWriter.close();
                    inStream.close();
                    socket.close();
                    appOn = false;
                }
            }
            System.out.println("GoodBye!!");
        } catch (IOException e) {
            System.out.println("Client message: IOException: " + e);
        }
    }
}
