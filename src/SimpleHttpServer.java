import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import com.sun.net.httpserver.HttpExchange;

import com.sun.net.httpserver.*;

public class SimpleHttpServer {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.setExecutor(Executors.newFixedThreadPool(10));

        HttpContext context = server.createContext("/", new RootHandler());

        context.getFilters().add(new LoggingFilter());

        server.start();

        System.out.println("Server started at http://localhost:8080");

    }
}

    class RootHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            if (!exchange.getRequestMethod().equalsIgnoreCase("GEt")) {
                sendResponse(exchange, 405, "Method Not Allowed");
                return;

            }


            String method = exchange.getRequestMethod();
            String uri = exchange.getRequestURI().toString();

            String response = "Server is running \n"

                    + "Method:" + method + "\n"
                    + "URI :" + uri;

            sendResponse(exchange, 200, response);
        }

        private void sendResponse(HttpExchange exchange, int status, String response)
                throws IOException {
            byte[] bytes = response.getBytes(StandardCharsets.UTF_8);

            exchange.sendResponseHeaders(status, bytes.length);

            OutputStream os = exchange.getResponseBody();
            os.write( bytes);
            os.close();
        }
    }

    class LoggingFilter extends Filter{

    @Override
        public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        long startTime = System.currentTimeMillis();

        System.out.println(" incoming request");
        System.out.println("method :" + exchange.getRequestMethod());
        System.out.println("URI :" + exchange.getRequestURI());
        System.out.println("Client : " + exchange.getRemoteAddress());

        chain.doFilter(exchange);

        long endTime = System.currentTimeMillis();

        System.out.println("response time :" + (endTime - startTime) + "ms");
        System.out.println("------------------------------");

    }

@Override
        public String description(){
        return "long request details and response time";

    }



    }





