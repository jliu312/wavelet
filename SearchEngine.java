import java.io.IOException;
import java.net.URI;
import java.util.*;

class Handler implements URLHandler {
    Set<String> strings = new HashSet<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("use /add?s= to add a new string to the list, /search?s= to search for all strings that contain that substring.");
        }
        if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                strings.add(parameters[1]);
                return String.format("Added %s", parameters[1]);
            }
        } else if (url.getPath().contains("/search")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                return searchForString(parameters[1]).toString();
            }
        } 
        return "404 Not Found!";
    }

    public List<String> searchForString(String keyword) {
        ArrayList<String> matches = new ArrayList<>();
        for(String s : strings) {
            if(s.contains(keyword)) {
                matches.add(s);
            }
        }
        return matches;
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
