package nhn_socket.gitlab_socket_doc.퀴즈;

import java.net.InetSocketAddress;
import java.net.Socket;

public class Quiz01 {
    /*
     * server에 들어가기 위해서 어떤 포트가 열려져 있는지 확인해보자.
     * 해당 포트가 열려 있는 경우 Socket 클래스의 인스턴스가 생성되지만, 
     * 그렇지 않은 경우에는 exception을 발생시키자.
     */
    public static void main(String[] args) {
        int startPort = 0;
        int endPort = 1000;
        for(int i = startPort; i <= endPort; i++) {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress("192.168.71.216", i), 10); // 10ms 타임아웃
                System.out.println(i + "포트가 열려져 있습니다.");
                socket.close();
            } catch (Exception e) {
            }
        }
    }
}
