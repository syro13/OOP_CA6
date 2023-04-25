package com.dkit.oop.sd2.Clients;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args)
    {
        Client client = new Client();
        client.start();
    }

    public void start()
    {
        Scanner in = new Scanner(System.in);
        try {
            Socket socket = new Socket("localhost", 8080);  // connect to server socket

            System.out.println("Client message: The Client is running and has connected to the server");

            System.out.println("Please enter a command:  (\"Time\" to get time, or \"Echo message\" to get echo) \n>");
            String command = in.nextLine();

            OutputStream os = socket.getOutputStream();
            PrintWriter out = new PrintWriter(os, true);

            out.write(command+"\n");  // write command to socket, and newline terminator
            out.flush();              // flush (force) the command over the socket

            Scanner inStream = new Scanner(socket.getInputStream());  // wait for, and retrieve the reply

            if(command.startsWith("Time"))   //we expect the server to return a time (in milliseconds)
            {
                String timeString = inStream.nextLine();
                System.out.println("Client message: Response from server Time: " + timeString);
            }
            else                            // the user has entered the Echo command or an invalid command
            {
                String input = inStream.nextLine();
                System.out.println("Client message: Response from server: \"" + input + "\"");
            }

            out.close();
            inStream.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("Client message: IOException: "+e);
        }
    }
}
