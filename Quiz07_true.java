import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Quiz07_true {
    public static void main(String[] args) {
        System.out.println("클라이언트를 대기 중입니다.");
        try(ServerSocket serverSocket = new ServerSocket(4321);) { // try-with-resource 문으로 ServerSocket 자원 자동 close
                while(true) { // 클라이언트가 서버에 계속 접속할 수 있도록 while문 안에 넣어준다.
                    Socket socket = serverSocket.accept(); // 서버에 접속하는 클라이언트들을 계속해서 블로킹 -> 서버에 접속할 때 까지 대기한다.
                    System.out.println("서버에 접속하였습니다.");
                    System.out.println("client : " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                    // 각 스레드들은 독립적으로 실행된다. 서로 영향 x
                    new Thread(new InputStream(socket)).start();
                    new Thread(new OutputStream(socket)).start();
                }
        }  catch (Exception e) {
            // TODO: handle exception
        } finally {
            System.out.println("서버를 종료합니다.");
        }
    }
}

class InputStream implements Runnable{
    Socket socket;

    InputStream(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));) {
            
            String str;

            while((str = in.readLine()) != null) {
                if(str.equals("exit")) {
                    break;
                }
                System.out.println(str);
            }

        } catch (Exception e) {
        } finally {
            System.out.println("클라이언트와의 접속이 끊겼습니다.");
        }
    }
}

class OutputStream implements Runnable{
    Socket socket;

    OutputStream(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (Scanner kb = new Scanner(System.in);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true)) {
            
            while(true) {
                String str = kb.nextLine();
                if(str.equals("exit")) {
                    break;
                }
                out.println(str);
            }

        } catch (Exception e) {
        } finally {
            System.out.println("클라이언트와의 접속이 끊겼습니다.");
        }
    }
}
