package nhn_socket.gitlab_socket_doc.퀴즈;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Quiz05 {
    public static void main(String[] args) {
        String address = "localhost";
        int port = 4321;

        try(Socket socket = new Socket(address, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);) {
            
            System.out.println(address + ":" + port + "에 접속하였습니다.");
            
            String line;

            while((line = in.readLine()) != null) {
            /*  
                해당 코드에서는 소켓의 입력 스트림에서 데이터를 한 줄씩 읽어들이고, 데이터가 null이 아니게 될 때까지 반복한다.
                서버 측에서 클라이언트와의 연결을 닫으면, 클라이언트의 입력 스트림은 더 이상 읽을 데이터가 없게 되고,
                readLine() 메서드는 null을 반환한다. 
                입력 스트림에서 null 값을 받는다는 것은 일반적으로 스트림의 끝에 도달했음을 의미한다.
                이는 네트워크 소켓에서 클라이언트와 서버 간의 연결이 종료되었거나, 연결이 더 이상 데이터를 전송하지 않을 때 발생한다.
            */
                if(line.equals("exit")) {
                    break;
                }
                System.out.println(line);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
