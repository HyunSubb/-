package nhn_socket.gitlab_socket_doc.예제;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Exam02 {
    public static void main(String[] args) {
        String ipAddress = "192.168.71.216";
        int port = 4321;

        try(Socket socket = new Socket(ipAddress, port)) {
            System.out.println(ipAddress + ":"+ port + " 에 접속하였습니다.");
            
            System.out.println("Local address : " + socket.getLocalAddress().getHostAddress());
            System.out.println("Local port : " + socket.getLocalPort());
            System.out.println("Remote address : " + socket.getInetAddress().getHostAddress());
            System.out.println("Remote port : " + socket.getPort());

            // server에 연결하여 data 전송
            // OutputStream.write() 의 출력 대상은 다양함. (파일, 네트워크)
            // 다양한 데이터 목적지로 바이트 데이터를 전송한다.
            // socket.getOutputStream().write("Hello World!".getBytes());
            // socket.getOutputStream().write("Hello World!22".getBytes());

            // PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream().write("HELLO~!")),true);

            // PrintWriter를 사용하여 문자열을 출력
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            
            // 문자열 전송
            out.println("HELLO~!");

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
