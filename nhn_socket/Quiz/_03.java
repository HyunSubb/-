package nhn_socket.Quiz;

import java.net.InetAddress;
import java.net.Socket;

public class _03 {
    public static void main(String[] args) {
        try (Socket socket = new Socket("192.168.71.216", 2332)){
            // 소켓이 연결 됨.
            System.out.println("서버에 연결되었습니다.");
            System.out.println("Local address : " + socket.getLocalAddress().getHostAddress());
            System.out.println("Local address : " + socket.getLocalPort());

            System.out.println("Remote address : " + socket.getInetAddress().getHostAddress());
            System.out.println("Remote port : " + socket.getPort());

            socket.getOutputStream().write("Hello".getBytes());

            int ch = 0;
            while((ch = socket.getInputStream().read()) >= 0) {
                System.out.write(ch);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
