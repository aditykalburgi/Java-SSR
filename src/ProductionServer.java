import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;




public class ProductionServer {

    public static void main(String args[]) throws IOException{

        HttpServer server = HttpServer.create(new InetSocketAddress(6060),0);

        server.createContext("/", ProductionServer::handleRequest);

        ThreadPoolExecutor Executor = new ThreadPoolExecutor(
                4,
                10,
            60L, TimeUnit.SECONDS,// idel timeout
                new ArrayBlockingQueue<>(100),//request
                new ThreadPoolExecutor.AbortPolicy()
        );

        server.setExecutor(Executor);
        server.start();


        System.out.println("Server running at http://localhost:6060");


    }
    private static void handleRequest(HttpExchange exchange) throws IOException{

        String response =
                "<html>" +
                        "<body>" +
                        "<h1>Thread: " + Thread.currentThread().getName() + "</h1>" +
                        "</body>" +
                        "</html>";


        exchange.getResponseHeaders().set("Content - type","text/html");

        exchange.sendResponseHeaders(200,response.getBytes().length);

            OutputStream os = exchange.getResponseBody();
             os.write(response.getBytes(StandardCharsets.UTF_8));
             os.close();


    }


}
