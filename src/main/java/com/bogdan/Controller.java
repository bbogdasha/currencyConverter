package com.bogdan;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker getAllDate;

    @FXML
    private Text setRate;

    @FXML
    private ChoiceBox<String> choiceBoxMain;

    @FXML
    private Button getData;

    @FXML
    private ChoiceBox<?> choiceBoxConvert;

    @FXML
    void initialize() {

        List<String> cur = new ArrayList<>();
        cur.add("USD");
        cur.add("AZN");
        cur.add("BYN");
        cur.add("CAD");
        cur.add("CHF");
        choiceBoxMain.getItems().addAll(cur);
        choiceBoxMain.setValue("USD");

        getData.setOnAction(event -> {

            LocalDate isDate = getAllDate.getValue();
            ChronoLocalDate chronoDate = ((isDate != null) ? getAllDate.getChronology().date(isDate) : null);
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            String output = "";
            if (chronoDate != null) {
                output = getUrlContent("https://api.exchangeratesapi.io/" + dateFormatter.format(chronoDate) + "?base=" + choiceBoxMain.getValue());
            }

//            if (!output.isEmpty()) {
//                JSONObject object = new JSONObject(output);
//                JSONArray arr = object.getJSONArray("exchangeRate");
//
//                double getSale = 0;
//
//                JSONObject block = arr.getJSONObject(i);
//                if (block.toString().contains(choiceBoxMain.getValue())) {
//                    if (block.toString().contains("saleRate") && block.toString().contains("purchaseRate")) {
//                        getSale = block.getDouble("saleRate");
//                    } else {
//                        setRate.setText("Sale: " + getSale);
//                    }
//                }
//
//                setRate.setText("Sale: " + getSale);
//            }

//            if (!output.isEmpty()) {
//                JSONObject object = new JSONObject(output);
//                JSONArray arr = object.getJSONArray("exchangeRate");
//
//                double getSale = 0;
//                double getPurchase = 0;
//
//                for (int i = 0; i < arr.length(); i++) {
//                    JSONObject block = arr.getJSONObject(i);
//                    if (block.toString().contains(choiceBox.getValue())) {
//                        if (block.toString().contains("saleRate") && block.toString().contains("purchaseRate")) {
//                            getSale = block.getDouble("saleRate");
//                            getPurchase = block.getDouble("purchaseRate");
//                        } else {
//                            setSale.setText("Sale: " + getSale);
//                            setPurchase.setText("Purchase: " + getPurchase);
//                        }
//                        break;
//                    }
//                }
//                setSale.setText("Sale: " + getSale);
//                setPurchase.setText("Purchase: " + getPurchase);
//            }
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