package nhn_socket.gitlab_socket_doc.퀴즈;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Quiz02 {
    /*
     * try - with - resources 를 이용해서 socket 자원을 관리하자.
     * 프로그램에서 socket, file 등 유한한 시스템 자원을 사용할 경우, 필요 없는 시점에 이를 해체 시켜줘야 한다.
     * 자바에서는 garbage collection을 지원하여서 자동으로 해제가 되지만, 그 시점이 언제가 될 지는 알 수 없다.
     * 자원의 활용을 최소한의 시간동안만 사용할 수 있도록 try - with - resources를 사용하자.
     * 
     * InetSocketAddress 클래스는 자바 네트워크 프로그래밍에서 IP 주소와 포트 번호를 함께 나타내는 객체
     * InetSocketAddress는 IP 주소(또는 호스트 이름)와 포트 번호를 하나의 객체로 묶어 관리
     * 
     * [InetAddress와 InetSocketAddress의 차이점]
     * InetAddress 클래스 : IP 주소를 나타내는 클래스이다. / IP 주소만 포함하며, 포트 번호는 포함하지 않는다.
     * InetSocketAddress 클래스 : IP 주소와 포트 번호를 함께 나타내는 클래스이다. 
     * InetAddress 객체를 내부적으로 포함하며, 여기에 포트 번호를 추가한 구조이다.
     */
    public static void main(String[] args) {
        int startPort = 0;
        int endPort = 1000;
        for(int i = startPort; i <= endPort; i++) {
            try (Socket socket = new Socket()) { // 리소스 선언
                // 리소스 선언부에는 단순히 자원만 선언을 해야 한다.
                socket.connect(new InetSocketAddress("192.168.71.216", i), 10);
                // 포트 연결 시도 10ms
                System.out.println(i + "포트가 열려져 있습니다.");
            } catch (Exception e) {
                // 포트가 닫혀 있거나 연결 실패 시 예외 처리
            }
        }
    }
}
