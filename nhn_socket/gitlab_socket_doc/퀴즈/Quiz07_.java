package nhn_socket.gitlab_socket_doc.퀴즈;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/*
 * 스레드를 이용해서 Quiz06에서 만든 채팅 프로그램을 비동기로 만들기.
 * server <-> client 비동기적 통신
 */
public class Quiz07_ {
    public static void main(String[] args) {
        int port = 4321;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println(socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + " 클라이언트가 해당 서버 접속");

                // 읽기 및 쓰기 스레드 시작
                new Thread(new ReaderThread(socket)).start();
                new Thread(new WriterThread(socket)).start();
            }
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
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "StandardCharsets.UTF_8"))) {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println(socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + " 클라이언트가 메시지 전송");
                System.out.println("Received: " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Reader thread closing connection.");
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
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "StandardCharsets.UTF_8"), true);
            Scanner kb = new Scanner(System.in);) {
            // int number = 0;
            // 예시로 5초마다 메시지 전송
            // while (true) {
            //     out.println("Server message" + number++);
            //     Thread.sleep(1000);
            // }
            String text = kb.nextLine();
            out.println(text);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Writer thread closing connection.");
        }
    }
}
