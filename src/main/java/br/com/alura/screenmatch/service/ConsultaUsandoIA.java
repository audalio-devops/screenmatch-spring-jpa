package br.com.alura.screenmatch.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class ConsultaUsandoIA {
    public static String obterTraducao(String texto) {
        OpenAiService service = new OpenAiService(System.getenv("OPENAI_APIKEY"));


        CompletionRequest requisicao = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt("traduza para o português o texto: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();


        var resposta = service.createCompletion(requisicao);
        return resposta.getChoices().get(0).getText();
    }

    public static String obterTraducaoGoogleAPI(String texto) {
        // Create a LanguageServiceClient object.
        String langTo = "pt-br";
        String langFrom = "en";
        String urlStr = null;
        try {
            urlStr = "https://script.google.com/macros/s/AKfycbzQ4YjqZngrUmzz7YdEji0mjUQI_ukQjN7rXnIFCs7OGnCI59TnPh2e0xsdxJAROsDt/exec" +
                    "?q=" + URLEncoder.encode(texto, "UTF-8") +
                    "&target=" + langTo +
                    "&source=" + langFrom;

            URL url = new URL(urlStr);
            StringBuilder response = new StringBuilder();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {

                response.append(inputLine);
            }
            in.close();
            return response.toString();

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
