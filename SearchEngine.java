import java.io.IOException;
import java.net.URI;
import java.util.*;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    Vector<String> stringVector = new Vector<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return "Welcome\n";
        }else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                     stringVector.add(parameters[1]);
                }
                return "added";
            }
            else if(url.getPath().contains("/search")){
                Vector<String> newStringVector = new Vector<>();
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    String search = parameters[1];
                     for(String x : stringVector){
                        if(x.contains(search)){
                            newStringVector.add(x);
                        }
                     }
                     return newStringVector.toString();
                }
            }
            return "404 Not Found!";
        }
    }
}

public class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
