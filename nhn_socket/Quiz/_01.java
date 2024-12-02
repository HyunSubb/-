package nhn_socket.Quiz;

import java.net.ServerSocket;
import java.net.Socket;

public class _01 {
        public static void main(String[] args) {

        // ServerSocket socket = null;

        int socket01 = 8000;
        int socket02 = 9999;

        // 소켓의 포트 번호는 1 ~ 65535 까지 이다.
        for(int i = socket01; i <= socket02; i++) {
            try {
                Socket socket = new Socket("192.168.71.214", i);
                // 해당 포트가 이미 사용중(열려 있음)이라면 new ServerSocket(i) 호출 시 예외가 발생한다.
                // socket = new ServerSocket(i);
                System.out.println(i);
                socket.close();
            } catch (Exception e) {
                // 열려 있는 포트는 에외로 빠져서 출력된다.
                System.out.println(i+"닫힘");
            }
        }
    }
}
