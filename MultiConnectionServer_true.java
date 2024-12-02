import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MultiConnectionServer_true {
    public static void main(String[] args) {
        // 포트 4321 서버 개설
        try (ServerSocket serverSocket = new ServerSocket(4321);) {
            while(true) {
                // while로 계속해서 클라이언트를 받는다.
                // 4321 포트로 들어올때 까지 클라이언트를 블로킹.
                Socket socket = serverSocket.accept();
                System.out.println("해당 client 접속 : " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());

                // 4321 포트로 들어올 때 마다 독립적인 스레드 시작
                new Thread(new Client2(socket)).start();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}

class Client2 implements Runnable{
    Socket socket;

    Client2(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        // 독립적인 I/O 스레드 시작
        new Thread(new InputStream2(socket)).start();
        new Thread(new OutputStream2(socket)).start();
    }
}

class InputStream2 implements Runnable{
    Socket socket;

    InputStream2(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));) {
            
            String str;

            while((str = in.readLine()) != null) {
                if(str.equals("exit")) {
                    // 클라이언트(cmd)에서 입력했을 때
                    break;
                }
                System.out.println(str);
            }

        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            System.out.println(socket.getInetAddress().getHostAddress() + ":" + socket.getPort() +"클라이언트와의 접속이 끊어집니다.");
        }
    }
}

class OutputStream2 implements Runnable{
    Socket socket;

    OutputStream2(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (Scanner kb = new Scanner(System.in);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);) {

            while(true) {
                // 입력할 때까지 대기
                String str = kb.nextLine();

                if(str.equals("exit")) {
                // 서버(vsc)에서 입력했을 때
                    break;
                }
                out.println(str);
            }

        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            System.out.println(socket.getInetAddress().getHostAddress() + ":" + socket.getPort() +"클라이언트와의 접속이 끊어집니다.");
        }
    }
}