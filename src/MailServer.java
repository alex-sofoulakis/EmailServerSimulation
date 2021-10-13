import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class MailServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true;

        try {
            serverSocket = new ServerSocket(4444);
            //serverSocket = new ServerSocket(Integer.parseInt(args[0]));
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }

        while (listening) {
            //Create a new thread and start it//
            Thread thread = new EmailThread(serverSocket.accept());
            thread.start();
        }

        serverSocket.close();
    }

}
