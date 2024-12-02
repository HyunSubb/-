package nhn_socket.gitlab_socket_doc.예제;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketExam {
    public static void main(String[] args) {
        int port = 4321;
        try(ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));) {
            // 여긴 서버측이니까 메시지를 받는걸 연습
            String input;
            while((input = in.readLine()) != null) {
                System.out.println(input);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
