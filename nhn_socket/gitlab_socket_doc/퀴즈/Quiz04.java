package nhn_socket.gitlab_socket_doc.퀴즈;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Quiz04 {
    public static void main(String[] args) {
        String ipAddress = "192.168.71.216";
        int port = 4321;
        try(Socket socket = new Socket(ipAddress,port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            Scanner kb = new Scanner(System.in);) {
                System.out.println(ipAddress + ":" + port + " 에 접속하였습니다.");
                while(true) {
                    System.out.println("서버로 보낼 메시지를 입력해주세요. (접속을 종료하시려면 exit를 입력해주세요.) ");
                    String input = kb.nextLine();
                    if(input.equals("exit")) {
                        break;
                    }
                    out.println(input); // 서버로 보내는 메시지
                }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
