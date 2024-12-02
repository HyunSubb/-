package nhn_socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Practice {
    public static void main(String[] args) {
        while(true) {
            System.out.println("클라이언트를 기다리고 있습니다.");

            try (ServerSocket serverSocket = new ServerSocket(4321)) {
            
                try (Socket socket = serverSocket.accept(); // 클라이언트와 연결할 소켓 생성
                    Scanner kb = new Scanner(System.in);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);) {
                    System.out.println("해당 client가 접속함 : " + socket.getInetAddress() + ":" +socket.getPort());
                    
                    String text;
                    while((text = in.readLine()) != null) {
                        System.out.println(text);
                        if(text.equals("exit")) {
                            System.out.println("클라이언트와의 접속이 끊어집니다.");
                            break;
                        }
                        text = kb.nextLine();
                        out.println(text);
                        if(text.equals("exit")) {
                            System.out.println("클라이언트와의 접속이 끊어집니다.");
                            break;
                        }
                    }
    
                } catch (Exception e) {
                    // TODO: handle exception
                    System.out.println("클라이언트와의 접속이 끊어집니다.");
                }
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("서버를 종료합니다.");
            }
        }

    }
}
