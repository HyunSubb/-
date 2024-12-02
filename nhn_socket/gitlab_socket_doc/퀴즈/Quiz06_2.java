package nhn_socket.gitlab_socket_doc.퀴즈;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Quiz06_2 {
    public static void main(String[] args) {
        // server <-> client 동기적 통신
        int port = 4321;
        while(true) {
            try(ServerSocket serverSocket = new ServerSocket(port)) { // 서버 개설
                Socket socket = serverSocket.accept(); // 클라이언트가 서버에 들어올 때 까지 블로킹한다.
                System.out.println(socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + "클라이언트가 해당 서버 접속");
                try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {
                        // 연결된 서버에 inputStream과 outputStream을 만들어서 입출력 가능하도록 함.
                        String message; // 클라이언트에서 넘겨주는 메시지를 저장할 변수
                        while((message = in.readLine()) != null) {
                            // inputStream 에서 null이 아닐 때 까지 읽어들임 = 스트림에서 널은 연결이 끊어진 경우를 의미한다.
                            System.out.println(message); // 클라이언트로부터 받은 메세지 출력
                            out.println(message); // 받은 메시지를 클라이언트에게 날린다.
                        }
                    
                } catch (Exception e) {
                    // TODO: handle exception
                } finally {
                    System.out.println("해당 클라이언트와의 연결이 끊깁니다.");
                }
            } catch (Exception e) {
    
                // TODO: handle exception
            } finally {
                System.out.println("서버가 닫힙니다.");
            }
        }
    }
}
