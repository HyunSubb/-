package nhn_socket.github_socket_doc;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class _01_socket {
    public static void main(String[] args) {
        /*
         *  - Socket 통신
         *  소켓 통신은 네트워크 상에서 두 프로그램 간의 통신을 가능하게 하는 기술이다.
         *  소켓은 IP 주소와 Port 번호로 구성되며, 클라이언트와 서버 간의 데이터 전송을 담당한다.
         * 
         *  - IP 주소 (Internet Protocol Address)
         *  IP 주소는 네트워크 상에서 장치를 식별하는 고유한 주소이다. IP 주소는 두 가지 버전이 있다.
         *  1. IPv4 : 32 비트 주소 체계로, 4개의 8비트 숫자로 구성된다.
         *  2. IPv6 : 128 비트 주소 체계로, 8개의 16비트 숫자로 구성된다.
         *  IP 주소는 네트워크 상에서 데이터를 전송할 때 송신자와 수신자를 식별하는 데 사용된다.
         * 
         *  - 포트 (Port)
         *  포트는 IP 주소와 결합되어 특정 네트워크 서비스나 애플리케이션을 식별하는 숫자이다. 
         *  포트 번호는 0부터 65535까지의 범위를 가지며, 주로 다음과 같이 분류된다.
         * 
         *  1. 잘 알려진 포트 : 0 ~ 1023 범위의 포트 번호로, HTTP 80 / HTTPS 443 / FTP 21 등과 같은 표준 서비스에 할당된다.
         *  2. 등록된 포트 : 1024 ~ 49151 범위의 포트 번호로, 특정 애플리케이션이나 서비스에 할당된다.
         *  3. 동적/사설 포트 : 49152 ~ 65535 범위의 포트 번호로, 임시 연결에 사용된다.
         * 
         *  - IP 주소와 Port의 결합
         *  IP 주소와 Port 번호는 함께 사용되어 네트워크 상에서 특정 애플리케이션을 식별한다. 
         *  예를 들어, 192.168.0.1:8080은 IP 주소 192.168.0.1 의 포트 번호 8080 에서 실행되는 애플리케이션을 나타낸다.
         *  
         *  이렇게 IP 주소아 포트 번호를 사용하여 네트워크 상에서 데이터를 주고 받는 애플리케이션을 식별하고 통신을 수행할 수 있다.
         * 
         * 
         * [퍼플렉시티]
         *  **소켓(Socket)**은 클라이언트와 서버가 네트워크 상에서 데이터를 주고받기 위한 통신 통로라고 이해하면 됩니다. 
         * serverSocket.accept() 메서드는 클라이언트의 연결 요청을 수락한 후, 
         * 클라이언트와의 통신에 사용할 새로운 소켓 객체를 반환합니다. 
         * 이 소켓은 클라이언트와 서버 간의 데이터 송수신을 담당합니다.
         * 
         * 서버 소켓(ServerSocket)과 일반 소켓(Socket)의 차이:
         * 
         * ServerSocket:
            서버 측에서 클라이언트의 연결 요청을 대기하고 수락하기 위한 역할을 합니다.
            클라이언트가 연결 요청을 보내면, accept() 메서드를 호출하여 새로운 Socket 객체를 생성합니다.

            Socket:
            클라이언트와 서버 간의 실제 데이터 송수신에 사용됩니다.
            서버는 accept()로 생성된 Socket 객체를 사용해 클라이언트와 통신합니다.

            [소켓을 통한 지속적인 통신]
            serverSocket.accept()로 반환된 소켓은 특정 클라이언트와의 통신 전용이다. 
            즉, 이 소켓은 해당 클라이언트와 연결이 유지되는 동안 계속 사용된다.
            
            소켓 = 전화선 
            누군가 전화를 걸면 (클라이언트) 교환원은 전화를 연결해준다. (accept())

            전화 교환원 (ServerSocket)
            전화선 (Socket)

            [소켓을 통한 데이터 송수신]
            소켓(클라이언트와 서버간 통신을 위한 통로)은 데이터를 송수신하기 위해 내부적으로 스트림(Stream)을 제공한다.
            이를 통해 네트워크 상에서 데이터를 읽거나 쓸 수 있다.

            입력 스트림 (InputStream) : 클라이언트가 보낸 데이터를 읽는 데 사용
            출력 스트림 (OutputStream) : 서버가 클라이언트에게 데이터를 보내는 데 사용
         */

         // ex
        try {
            ServerSocket serverSocket = new ServerSocket(1234); // 1234 포트로 클라이언트가 들어온다면
            Socket clientSocket = serverSocket.accept(); // 서버 소켓은 클라이언트의 접속을 수락하고 새로운 소켓을 반환한다.

            InputStream input = clientSocket.getInputStream(); // 클라이언트가 보낸 데이터 읽기
            OutputStream outputStream = clientSocket.getOutputStream(); // 클라이언트로 데이터 보내기
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}