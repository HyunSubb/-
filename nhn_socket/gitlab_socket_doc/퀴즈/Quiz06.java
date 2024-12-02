package nhn_socket.gitlab_socket_doc.퀴즈;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Quiz06 {
    public static void main(String[] args) {
        int port = 4321;
        while(true) {
            // 서버 개설 - 서버는 클라이언트가 들어올 때 까지 블로킹 상태가 된다.
            try (ServerSocket serverSocket = new ServerSocket(port);
            ){
                System.out.println("서버를 실행하였습니다 : 대기상태");

                while(true) {
                    System.out.println("클라이언트를 대기 중...");
                // 클라이언트 연결 수락
                /*
                 * 이 코드는 클라이언트가 연결 요청을 보낼 때까지 대기를 하는 코드이다.
                 * 이 단계에서 메서드는 블로킹 상태가 되어 (즉, 실행이 멈춘 상태로 기다림)
                 * 클라이언트의 요청이 올 때까지 서버는 다른 작업을 수행하지 않는다.
                 * 
                 * 클라이언트가 연결을 요청하면, 서버는 해당 요청을 수락하고, 클라이언트와의
                 * 통신을 위한 새로운 'Socket' 객체를 생성한다.
                 * 
                 * 반환된 'Socket' 객체는 클라이언트와의 통신에 사용된다. 이를 통해 서버는 
                 * 클라이언트와 데이터를 주고받을 수 있다. 
                 * 
                 * 이후, 이 소켓에서 입출력 스트림(InputStream, OutputStream)을 생성하여
                 * 실제 데이터 송수신 작업이 이루어진다.
                 */
                    Socket socket = serverSocket.accept(); // 서버에 클라이언트가 접속하면 전용 소켓을 할당 / 클라이언트 들올 때 까지 블로킹 상태
                    System.out.println("클라이언트가 연결되었습니다. - " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + " 접속");

                    try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {
                        
                        String message;

                        while((message = in.readLine()) != null) {
                            System.out.println("클라이언트가 보낸 메세지 : " + message);
                            out.println(message);
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
                

            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
}
