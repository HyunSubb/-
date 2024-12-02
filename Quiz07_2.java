import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Quiz07_2 {
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(4321);) {
            while(true) {
                /*
                 * try-with-resource 구문이 문제가 있어서 서버가 바로 끊겼던 거임. try-with-resource 구문은 블록이 끝나면 자동으로 자원을 닫는다.
                 * 이 경우 try-with-resource 블록 안에 자원이 선언되어 있기 때문에 블록이 끝나는 즉시 소켓이 닫혔다.
                 * 따라서 클라이언트가 연결되자마자 소켓이 닫혀버려서 연결이 끊기게 된 것.
                 */
                // try (Socket socket = serverSocket.accept()) {
                //     System.out.println(socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + " 클라이언트가 해당 서버 접속");
                //     new Thread(new Input(socket)).start();
                //     new Thread(new Output(socket)).start();
                    
                // } catch (Exception e) {
                // }
                    Socket socket = serverSocket.accept();
                    System.out.println(socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + " 클라이언트가 해당 서버 접속");
                    new Thread(new Input(socket)).start();
                    new Thread(new Output(socket)).start();
            }
        } catch (Exception e) {
        }
    }
}

class Input implements Runnable{
    Socket socket; 

    public Input(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));) {
            String text;
            while((text = in.readLine()) != null) {
                if(text.equals("exit")) {
                    break;
                }
                System.out.println(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("클라이언트와의 접속이 끊어집니다.");
        }
    }
}

class Output implements Runnable {
    Socket socket;

    public Output(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
            Scanner kb = new Scanner(System.in)) {
            while(true) {
                String str = kb.nextLine();
                if(str.equals("exit")) {
                    break;
                }
                out.println(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("클라이언트와의 접속이 끊어집니다.");
        }
    }

    
}