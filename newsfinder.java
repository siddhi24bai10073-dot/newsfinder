// Name: Siddhi Dixit
// Roll No: 24BAI10073
// Project: News Finder using API

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class NewsFinder {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== News Finder ===");
        System.out.print("Enter topic: ");
        String query = sc.nextLine();

        String apiKey = "YOUR_API_KEY_HERE";

        try {
            String urlStr = "https://newsapi.org/v2/everything?q="
                    + URLEncoder.encode(query, "UTF-8")
                    + "&apiKey=" + apiKey;

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            String result = response.toString();

            System.out.println("\nTop News Articles:\n");

            int count = 0;
            int index = 0;

            while ((index = result.indexOf("\"title\":\"", index)) != -1 && count < 5) {
                int start = index + 9;
                int end = result.indexOf("\"", start);

                String title = result.substring(start, end);

                int sourceStart = result.indexOf("\"name\":\"", end) + 8;
                int sourceEnd = result.indexOf("\"", sourceStart);

                String source = result.substring(sourceStart, sourceEnd);

                System.out.println((count + 1) + ". " + title);
                System.out.println("Source: " + source + "\n");

                count++;
                index = end;
            }

            if (count == 0) {
                System.out.println("No news found.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}
