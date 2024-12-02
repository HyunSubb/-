package nhn_socket.gitlab_socket_doc.퀴즈;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BroadCastingServer {
    /*
     * CopyOnWriteArrayList 는 동시성 제어가 필요한 환경에서 사용된다. 
     * 이 클래스는 여러 스레드가 동시에 접근할 수 있도록 설계되어 있으며, 특히 읽기 작업이 많은 경우에 효율적이다. 
     *  -> 다수의 스레드가 동시에 접근가능.
     */
    private static List<Socket> clientSockets = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        int port = 4321;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println(socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + " 클라이언트가 해당 서버 접속");

                // 새로운 클라이언트 소켓을 리스트에 추가
                clientSockets.add(socket);

                // 각 클라이언트 연결에 대해 새로운 스레드 생성
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void broadcastMessage(String message, Socket senderSocket) {
        for (Socket socket : clientSockets) {
            if (socket != senderSocket) { // 보낸 사람에게는 다시 보내지 않음
                try {
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                    out.println(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            // Separate threads for reading and writing
            new Thread(new ReaderThread(clientSocket)).start();
            new Thread(new WriterThread(clientSocket)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ReaderThread implements Runnable {
    private Socket socket;

    public ReaderThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"))) {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println(socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + " 클라이언트가 메시지 전송");
                System.out.println("받은 메시지: " + message);
                
                // 서버에서 받은 메시지를 다른 모든 클라이언트에게 브로드캐스트
                BroadCastingServer.broadcastMessage(message, socket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class WriterThread implements Runnable {
    private Socket socket;

    public WriterThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {
            // Example: send a message every 5 seconds
            int number = 0;
            while (true) {
                out.println("Echo: Server message " + number++);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
