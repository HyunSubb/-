package _19.nhn_socket;

import java.net.Socket;

public class _02_java_socket_communication {
    public static void main(String[] args) {

        try {
            Socket socket = new Socket("192.168.71.214", 4321);
            System.out.println("소켓이 연결되었습니다.");

            socket.close();
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e);
        }
    }
}
