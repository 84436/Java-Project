import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        final String serverHost = "localhost";
        final int PORT = 9999;

        Socket socketOfClient = null;
        BufferedWriter os = null;
        BufferedReader is = null;

        try {
            socketOfClient = new Socket(serverHost, PORT);
            os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
            is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));
        } 
        catch (UnknownHostException e) {
            System.err.println("Don't know about host " + serverHost + ":" + PORT);
            return;
        } 
        catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + serverHost + ":" + PORT);
            return;
        }

        try {
            Scanner sc = new Scanner(System.in);
            while (true) {
                String line = sc.nextLine();
                os.write(line);
                os.newLine();
                os.flush();

                String responseLine = is.readLine();
                if (!responseLine.equals("Quit command received")) {
                    System.out.println("Server: " + responseLine);
                }
                else {
                    break;
                }
            }

            os.close();
            is.close();
            sc.close();
            socketOfClient.close();
        } catch (UnknownHostException e) {
            System.err.println("Trying to connect to unknown host: " + e);
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }
}