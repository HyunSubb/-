package nhn_socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class _04_serverSocket {
    public static void main(String[] args) {
        int port = 1238;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("바인딩에 성공하였습니다.");

            Socket socket = serverSocket.accept();
            BufferedInputStream input = new BufferedInputStream(socket.getInputStream());
            
            try {
                while(socket.isConnected()) {
                    byte[] readBuffer = new byte[1024];
                    int readLength = input.read(readBuffer);
                    if(readLength != 0) {
                        System.out.println(new String(readBuffer));
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
            }


        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
        
        // int port = 4321;

        // try (ServerSocket serverSocket = new ServerSocket(port)) {
        //     Socket socket = serverSocket.accept();

        //     // tag::send[]
        //     socket.getOutputStream().write("Hello!".getBytes());
        //     socket.getOutputStream().flush();
        //     // end::send[]

        //     socket.close();
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }
}
