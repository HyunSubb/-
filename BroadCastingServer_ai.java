import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class BroadCastingServer_ai {
    /*
     * CopyOnWriteArrayList 는 동시성 제어가 필요한 환경에서 사용된다. 
     * 이 클래스는 [여러 스레드가 동시에 접근할 수 있도록 설계]되어 있으며, 특히 읽기 작업이 많은 경우에 효율적이다. 
     *  -> 다수의 스레드가 동시에 접근가능.
     */
    private static List<Socket> list = new CopyOnWriteArrayList<>(); // 여러 스레드에서 동시에 접근이 가능하다.

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(4321)) {
            // port 4321인 서버 생성
            while(true) {
                // 클라이언트 블로킹 -> 클라이언트가 서버에 들어올 때까지 계속 대기
                Socket socket = serverSocket.accept();
                // 서버에 클라이언트가 접속함.
                System.out.println("client : " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + " 접속");
                // 리스트에 해당 소켓 저장
                list.add(socket);
                // 클라이언트가 서버에 들어올 때마다 독립적으로 해당 클라이언트를 동작시킨다.
                new Thread(new Clinet3(socket)).start();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static void resend(String str) {
        for(Socket socket : list) {
            // list에 저장되어 있는 socket을 하나씩 꺼낸다.
            try (PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);){
                // 받은 메시지 클라이언트로 재전달
                out.println("server [" + socket.getLocalAddress().getHostAddress() + ":" + socket.getLocalPort() +"] (재전송 메시지) : " + str);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
}

class Client3 implements Runnable {
    Socket socket;
    PrintWriter out;

    Client3(Socket socket) {
        this.socket = socket;
        try {
            this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        new Thread(new InputStream3(socket, out)).start();
        new Thread(new OutputStream3(socket)).start();
    }
}

class InputStream3 implements Runnable {
    Socket socket;
    PrintWriter out;

    InputStream3(Socket socket, PrintWriter out) {
        this.socket = socket;
        this.out = out;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));) {
            String str;
            while ((str = in.readLine()) != null) {
                if (str.equals("exit")) {
                    break;
                }
                System.out.println("client [" + socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + "] : " + str);
                BroadCastingServer2.resend(str); // 받은 메시지를 클라이언트로 다시 전송한다.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class OutputStream3 implements Runnable{
    Socket socket;

    OutputStream3(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (Scanner kb = new Scanner(System.in);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);) {
            
            while(true) {
                String str = kb.nextLine();
                if(str.equals("exit")) {
                    break;
                }
                out.println("server [" + socket.getLocalAddress().getHostAddress() + ":" + socket.getLocalPort() +"] : " + str);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
