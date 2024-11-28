package nhn_socket.Quiz;

import java.net.Socket;
import java.util.Scanner;

public class _04 {
    public static void main(String[] args) {
        try (Socket socket = new Socket("192.168.71.216", 2332)){
            // 소켓이 연결 됨.

            System.out.println("서버에 연결되었습니다.");
            Scanner sc = new Scanner(System.in);
            System.out.println("Local address : " + socket.getLocalAddress().getHostAddress());
            System.out.println("Local address : " + socket.getLocalPort());

            System.out.println("Remote address : " + socket.getInetAddress().getHostAddress());
            System.out.println("Remote port : " + socket.getPort());

            while(true) {
                String line = sc.nextLine();

                if(line.equals("exit")) {
                    break;
                }

                byte[] readBuffer = new byte[1024];
                int readLength = socket.getInputStream().read(readBuffer);
                if(readLength != 0) {
                    System.out.println(new String(readBuffer));
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("연결이 거부됨");
        }
    }
}
