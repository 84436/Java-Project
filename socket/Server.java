import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static class ServiceThread extends Thread {
        private int clientNumber;
        private Socket socketOfServer;

        public ServiceThread(Socket socketOfServer, int clientNumber) {
            this.clientNumber = clientNumber;
            this.socketOfServer = socketOfServer;

            log("New connection with client #" + this.clientNumber + " at " + socketOfServer);
        }

        @Override
        public void run() {
            try {
                BufferedReader is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
                BufferedWriter os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));

                while (true) {
                    String line = is.readLine();
                    System.out.println("Client " + clientNumber + ": " + line);
                    
                    if (line.equals("quit")) {
                        os.write("Quit command received");
                        os.newLine();
                        os.flush();
                        break;
                    }

                    String response = solveRequest(line);
                    os.write(response);
                    os.newLine();
                    os.flush();
                }
            } 
            catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }

        private String solveRequest(String request) {
            return "This is a response";
        }
    }

    public static void main(String args[]) throws IOException {
        final int PORT = 9999;
        ServerSocket listener = null;
        int clientNumber = 0;

        try {
            listener = new ServerSocket(PORT);
            System.out.println("Server is waiting at port " + PORT);

        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }

        try {
            while (true) {
                Socket socketOfServer = listener.accept();
                new ServiceThread(socketOfServer, clientNumber++).start();
                System.out.println("Active thread " + Thread.activeCount());
            }
        } finally {
            listener.close();
        }
    }

    private static void log(String message) {
        System.out.println(message);
    }
}
