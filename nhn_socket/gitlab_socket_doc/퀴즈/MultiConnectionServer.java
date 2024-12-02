package nhn_socket.gitlab_socket_doc.퀴즈;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MultiConnectionServer {
    public static void main(String[] args) {
        int port = 4321;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println(socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + " 클라이언트가 해당 서버 접속");

                // 각 클라이언트 연결에 대해 새로운 스레드 생성
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    // Thread 내부에 Thread가 동작하는 구조.
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
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            Scanner kb = new Scanner(System.in)) {
            // Example: send a message every 5 seconds
            // int number = 0;
            // while (true) {
            //     out.println("Echo: Server message " + number++);
            //     Thread.sleep(1000);
            // }
            while(true) {
                String str = kb.nextLine();
                out.println(str);
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
