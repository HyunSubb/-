package nhn_socket.gitlab_socket_doc.퀴즈;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Quiz06 {
    public static void main(String[] args) {
        
        String address = "localhost";
        int port = 1234;

        try(Socket socket = new Socket(address, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            Scanner kb = new Scanner(System.in);) {
            
            System.out.println(address + ":" + port + "에 접속하였습니다.");

            while(true) {
                System.out.println("서버로 보낼 메시지를 입력해주세요. (접속을 종료하시려면 exit를 입력해주세요.) ");
                String str = kb.nextLine();
                if(str.equals("exit")) {
                    break;
                }
                out.println(str); // 서버로 보내는 메시지

                String line;

                while((line = in.readLine()) != null) {
                    System.out.println(line); // 서버가 보낸 메시지
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
