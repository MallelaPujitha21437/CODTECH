import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

/**
 * CODTECH Internship - Task 2
 * Java program that:
 * - Sends HTTP GET request to a public weather API
 * - Parses JSON response
 * - Displays weather data in a structured format
 *
 * Author: Mallela Pujitha
 */

public class WeatherFetcher {

    // Replace with your actual API key from OpenWeatherMap
    private static final String API_KEY = "00f22bbfe8193689aab2787beb5eb067";
    private static final String CITY = "Vijayawada";

    public static void main(String[] args) {
        try {
            // Build API URL
            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" 
                            + CITY + "&appid=" + API_KEY + "&units=metric";

            // Create URL and open connection
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Read response
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            StringBuilder json = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            reader.close();

            // Parse JSON
            JSONObject response = new JSONObject(json.toString());

            String cityName = response.getString("name");
            JSONObject main = response.getJSONObject("main");
            double temp = main.getDouble("temp");
            double feelsLike = main.getDouble("feels_like");
            int humidity = main.getInt("humidity");

            JSONObject weather = response.getJSONArray("weather").getJSONObject(0);
            String description = weather.getString("description");

            // Display result
            System.out.println("📍 Weather Data for: " + cityName);
            System.out.println("-----------------------------");
            System.out.println("🌡 Temperature: " + temp + "°C");
            System.out.println("🤒 Feels Like: " + feelsLike + "°C");
            System.out.println("💧 Humidity: " + humidity + "%");
            System.out.println("🌥 Description: " + description);

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}

