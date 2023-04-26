package com.dkit.oop.sd2.Clients;

import com.dkit.oop.sd2.DTOs.Rockets;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {

    public static void main(String[] args)
    {
        Client client = new Client();
        client.start();
    }

    public void start()
    {
        Gson gsonParser = new Gson();
        Scanner in = new Scanner(System.in);
        try {
            Socket socket = new Socket("localhost", 8080);  // connect to server socket

            System.out.println("Client message: The Client is running and has connected to the server");

            System.out.print("""
                        1 - Display Rocket By ID
                        2 - Display All Rockets (Json)
                        3 - Insert Rocket
                        4 - Delete Rocket By ID
                        5 - Exit
                        """);
            String command = in.nextLine();

            if(command.startsWith("1")){
                System.out.println("Enter Rocket ID: ");
                command = command + " " + in.nextLine();
            }
            OutputStream os = socket.getOutputStream();
            PrintWriter out = new PrintWriter(os, true);

            out.write(command+"\n");  // write command to socket, and newline terminator
            out.flush();              // flush (force) the command over the socket

            Scanner inStream = new Scanner(socket.getInputStream());  // wait for, and retrieve the reply

            if(command.startsWith("1"))   //we expect the server to return a time (in milliseconds)
            {
                System.out.println("1");
                String gsonString = inStream.nextLine();
                Rockets rocket = gsonParser.fromJson(gsonString,Rockets.class);
                System.out.println(rocket.toString());
            }
            else if (command.startsWith("2"))                           // the user has entered the Echo command or an invalid command
            {
                List<Rockets> rocketsList = new ArrayList<>();
                String[] stringArray = inStream.nextLine().split("}");
                for(int i = 0; i < stringArray.length-1;i++){
                    stringArray[i] = stringArray[i].substring(1)+"}";
                    rocketsList.add(gsonParser.fromJson(stringArray[i], Rockets.class));
                }
                for(Rockets rocket:rocketsList){
                    System.out.println(rocket.toString());
                }
            }else if(command.startsWith("7")){
                out.close();
                inStream.close();
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Client message: IOException: "+e);
        }
    }
}
