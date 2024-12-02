package nhn_socket.gitlab_socket_doc.예제;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Exam04 {
    public static void main(String[] args) {
        int port = 4321;
        while(true) {
            try(ServerSocket serverSocket = new ServerSocket(port)) {
                Socket socket = serverSocket.accept(); // 클라이언트 대기
    
                System.out.println(socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + "클라이언트가 접속");

                try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                    Scanner kb = new Scanner(System.in)) {
                    String message;
                    String outMesage;
                    while(true) {
                        outMesage = kb.nextLine();
                        out.println(outMesage);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }

            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                System.out.println("서버가 종료됩니다.");
            }
        }
    }
}
