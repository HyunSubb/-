package nhn_socket.gitlab_socket_doc.예제;

import java.net.Socket;

public class Exam01 {
    /*
     * exam01) 클라이언트 소켓을 이용해서 server에 연결해보기
     */
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("192.168.71.216", 4321);
            // IP와 포트번호를 이용해서 서버에 접속하기
            // 라즈베리파이접속해서 포트 열어준 다음 접속해주기.
            System.out.println("서버에 연결되었습니다.");

            socket.close();
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e);
        }
    }
}
