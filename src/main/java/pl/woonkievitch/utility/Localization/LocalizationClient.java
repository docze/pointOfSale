package pl.woonkievitch.utility.Localization;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

public class LocalizationClient {
    private static String jsonUrl = "http://ip-api.com/json";
    public static LocalizationDTO getLocalization() throws IOException {
        URL url = new URL(jsonUrl);
        InputStreamReader reader = new InputStreamReader(url.openStream());
        BufferedReader bufferedReader = new BufferedReader(reader);

        String response = bufferedReader.lines().collect(Collectors.joining());
        Gson g = new Gson();

        LocalizationDTO localizationDTO = g.fromJson(response, LocalizationDTO.class);
        return localizationDTO;
    }
}
