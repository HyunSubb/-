package nhn_socket.gitlab_socket_doc.퀴즈;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Quiz03 {
    /*
     * Socket class의 함수를 이용해 client와 server 접속 정보(host, port)를 확인해보기 
     */
    public static void main(String[] args) {
        String ipAddress = "192.168.71.216";
        int port = 4321;

        try(Socket socket = new Socket(ipAddress, port);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner kb = new Scanner(System.in)) { // 리소스 선언
            System.out.println(ipAddress + ":"+ port + " 에 접속하였습니다.");
            
            System.out.println("Local address : " + socket.getLocalAddress().getHostAddress());
            System.out.println("Local port : " + socket.getLocalPort());
            System.out.println("Remote address : " + socket.getInetAddress().getHostAddress());
            System.out.println("Remote port : " + socket.getPort());
            
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
