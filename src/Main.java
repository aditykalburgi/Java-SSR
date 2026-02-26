import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;


public class Main  {

    public static void main(String [] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(7070), 0);

        server.createContext("/", (HttpExchange exchange) -> {


            String username = "Aditya";

            String html =
                    "<!DOCTYPE html>" +
                            "<html>" +
                            "<head><title>java ssr</title></head>" +
                            "<body>" +
                            "<h1>Hello " + username + "</h1>" +
                            "<p>server side rendering</p>" +
                            "</body>" +
                            "</html>";
            exchange.sendResponseHeaders(200, html.getBytes().length);

            OutputStream os = exchange.getResponseBody();
            os.write(html.getBytes(StandardCharsets.UTF_8));
            os.close();


        });

        server.start();
        System.out.println("Server started at http://localhost:7070");






    }




}
