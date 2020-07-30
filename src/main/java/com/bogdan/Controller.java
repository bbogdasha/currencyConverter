package com.bogdan;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField day;

    @FXML
    private TextField month;

    @FXML
    private TextField year;

    @FXML
    private Button getDate;

    @FXML
    private Text setSale;

    @FXML
    private Text setPurchase;

    @FXML
    void initialize() {
        getDate.setOnAction(event -> {
            String output = getUrlContent("https://api.privatbank.ua/p24api/exchange_rates?json&date=" + day.getText().trim() + "." + month.getText().trim() + "." + year.getText().trim());
            System.out.println(output);

            if (!output.isEmpty()) {
                JSONObject object = new JSONObject(output);
                setSale.setText("Sale: " + object.getJSONArray("exchangeRate"));


                List<String> result = new ArrayList<>();
                JSONArray arr = object.getJSONArray("exchangeRate");
                for (int i = 0; i < arr.length(); i++) {
                    String url = arr.getJSONObject(i).toString();
                    result.add(url);
                    if (url.contains("USD")) {
                        System.out.println(result.get(i));
                    }
                }


                //setPurchase.setText("Purchase: " + object.getJSONObject("exchangeRate").getString("purchaseRateNB"));
            }
        });
    }

    private static String getUrlContent(String urlAddress) {

        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(urlAddress);
            URLConnection urlConnection = url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String str;

            while ((str = br.readLine()) != null) {
                content.append(str + "\n");
            }
            br.close();
        } catch (Exception e) {
            System.out.println("No information available on this date");
        }
        return content.toString();
    }
}