package nhn_socket.Quiz;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class _09_echoServer {
    public static void main(String[] args) {
        /* 
            1. 실행시 서비스를 위한 port를 지정할 수 있다. 별도의 port 지정이 없는 경우, 1234를 기본으로 한다.

            2. Server는 실행 후 대기 상태를 유지하고, client가 접속되면 client 정보를 출력한다.

            3. Server에서는 연결된 socket이 끊어지기 전까지 client에서 보내온 데이터를 client로 다시 돌려 보낸다.

            4. Client 연결이 끊어지면, server socket을 닫고 프로그램을 종료한다.
        */
        int port = 1234;

        System.out.println("Port 번호를 변경하고 싶으시면 O를 입력해주세요.");
        Scanner kb = new Scanner(System.in);
        char ch = kb.nextLine().charAt(0);
        if(ch == 'O') {
            System.out.println("변경하고 싶은 Port 번호를 입력해주세요.");
            port = kb.nextInt();
        }

        System.out.println("변경된 Port 번호는 : " + port + " 입니다.");

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("서버를 실행하였습니다. : 대기상태");

            while(true) {
                System.out.println("클라이언트를 기다리는 중...");

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
                Socket clientSocket = serverSocket.accept(); // 서버 소켓은 수락을 하면 새로운 소켓을 생성한다.
                // 새로 생성된 소켓을 가지고 클라이언트와 서버가 서로 통신을 하는 것이다.

                // 클라이언트 정보 출력
                System.out.println("클라이언트 연결 됨 : ");
                // clientSocket.getInetAddress() : 클라이언트의 IP 주소 정보를 나타내내는 객체 (InetAddress)를 반환한다.
                // 이 객체는 네트워크 상에서 클라이언트를 식별하기 위한 IP 주소를 포함한다.
                // getHostAddress() : InetAddress 객체에서 실제 IP 주소를 문자열 형태로 반환한다.
                // 클라이언트의 IP 주소가 192.168.0.101이라면 이 메서드는 "192.168.0.101" 문자열을 반환합니다.
                System.out.println("IP 주소 : " + clientSocket.getInetAddress().getHostAddress());
                System.out.println("포트 번호 : " + clientSocket.getPort());

                // 에코 기능 구현
                /*
                 * clientSocket.getInputStream() : 클라이언트가 서버로 보내는 데이터를 읽기 위한 입력 스트림을 가져온다.
                 * 소켓은 네트워크 연결을 통해 데이터룰 주고받으므로, 데이터를 읽으려면 이 스트림을 사용해야 한다.
                 * (바이트 기반)
                 * 
                 * InputStreamReader() : 바이트 기반의 InputSream을 문자 기반으로 변환하는 역할을 한다.
                 * 네트워크 데이터는 기본적으로 바이트(byte) 단위로 전송되기 때문에, 이를 사람이 읽을 수 있는 문자 데이터로
                 * 변환해야 한다.
                 * (문자 데이터로 변환)
                 * 
                 * BufferedReader() : 문자 기반의 입력 스트림에 버퍼링 기능을 추가하여 효율적으로 데이터를 읽을 수 있다.
                 * readLine() : 이 메서드를 사용하여 한 줄 단위로 데이터를 읽을 수 있다.
                 * 
                 * clientSocket.getOutputStream() : 서버가 클라이언트로 데이터를 보내기 위한 출력 스트림을 가져온다.
                 * 
                 * OutputStreamWriter() : 바이트 기반의 OutputStream을 문자 기반으로 변환한다.
                 * 
                 * PrintWriter() : 출력 스트림에 편리한 메서드(println)을 제공하며, 데이터를 줄 단위로 쉽게 전송할 수 있다.
                 * 두 번째 매개변수인 true는 자동으로 버퍼를 비우는(플러시) 기능을 활성화한다. 
                 * 즉, 데이터를 즉시 클라이언트로 전송한다.
                 */
                try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true)
                ) {
                    String receivedMessage;

                    // 클라이언트로부터 메시지를 읽고 다시 돌려보냄
                    /*
                     * in.readLine() : 클라이언트가 보낸 데이터를 한 줄 단위로 읽는다. 클라이언트가 메시지를 보냈다면 그 내용이
                     * receivedMessage 변수에 저장된다.
                     */
                    while ((receivedMessage = in.readLine()) != null) {
                        System.out.println("클라이언트로부터 받은 메시지: " + receivedMessage);
                        out.println(receivedMessage); // 클라이언트로 메시지 전송
                        // 서버가 받은 메시지를 그대로 클라이언트에게 다시 보낸다. (에코 기능)
                    }
                } catch (Exception e) {
                    System.err.println("클라이언트와의 통신 중 오류 발생: " + e.getMessage());
                } finally {
                    clientSocket.close(); // 연결 종료
                    System.out.println("클라이언트 연결이 종료되었습니다.");
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
            System.err.println("서버 소켓 오류 : " + e.getMessage());
        }
    }
}
