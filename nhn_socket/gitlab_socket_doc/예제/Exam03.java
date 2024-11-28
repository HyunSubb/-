package nhn_socket.gitlab_socket_doc.예제;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Exam03 {
        public static void main(String[] args) {
        String ipAddress = "192.168.71.216";
        int port = 4321;

        try(Socket socket = new Socket(ipAddress, port)) {
            System.out.println(ipAddress + ":"+ port + " 에 접속하였습니다.");

            System.out.println("Local address : " + socket.getLocalAddress().getHostAddress());
            System.out.println("Local port : " + socket.getLocalPort());
            System.out.println("Remote address : " + socket.getInetAddress().getHostAddress());
            System.out.println("Remote port : " + socket.getPort());

            int ch;
            // System.out.write()는 자바에서 바이트 단위로 데이터를 출력하는 메서드입니다.
            // System.out.write(int b)는 정수 값을 받아들여, 해당 값의 **하위 8비트(1바이트)**를 출력합니다.
            // 예를 들어, System.out.write(65);는 아스키 코드 값 65에 해당하는 문자 'A'를 출력합니다.
            // system.out.write()의 출력대상은 표준 출력 스트림 (콘솔)이다.
            // system.out.write(ch) 는 바이트를 문자로 변환하는 기능을 제공하지는 않는다. 콘솔이 이를 문자로 해석하여 화면에 표시하는 것임.
            while((ch = socket.getInputStream().read()) >= 0) {
                // System.out.write(ch)는 바이트 값을 그대로 표준 출력 스트림으로 보냅니다.
                System.out.write(ch);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            // input stream (byte)을 string으로 변환을 해주자.
            // readLine()은 입력 스트림에서 한 줄 전체를 읽는다. 줄의 끝은 일반적으로 개행(엔터)으로 구분된다. ex) "Hello" 를 입력하면 "Hello"라는 문자열이 반환됩니다.
            // read()는 입력 스트림에서 한 문자를 읽는다. 읽은 문자의 아스키 코드 값을 int 타입으로 반환한다. 스트림의 끝에 도달하면 -1을 반환. ex) "A" 를 입력하면 65 (아스키 코드)가 반환된다.
            String line;

            while((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
