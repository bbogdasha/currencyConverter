package com.bogdan;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.net.URL;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button getHelp;

    @FXML
    private DatePicker getAllDate;

    @FXML
    private Text setRate;

    @FXML
    private Text setInverse;

    @FXML
    private ComboBox<String> choiceBoxMain;

    @FXML
    private Button getData;

    @FXML
    private ComboBox<String> choiceBoxConvert;

    @FXML
    private Text setInfo;

    @FXML
    void initialize() {

        final String apiKey = "f749eb821b99ad4004ac";

        getHelp.setTooltip(new Tooltip("Select date: \nFree version API only allows up to 1 year earlier."));

        String outputCurrencyList = GetUrl.getUrlContent("https://free.currconv.com/api/v7/currencies?apiKey=" + apiKey);
        JSONObject object = new JSONObject(outputCurrencyList);
        JSONObject results = object.getJSONObject("results");
        Iterator<String> keys = results.keys();
        Set<String> currency = new HashSet<>();
        while(keys.hasNext()) {
            currency.add(keys.next());
        }

        choiceBoxMain.getItems().addAll(currency);
        choiceBoxMain.setValue("USD");
        choiceBoxConvert.getItems().addAll(currency);
        choiceBoxConvert.setValue("UAH");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        getAllDate.setValue(LocalDate.parse(dateFormatter.format(LocalDate.now())));


        getData.setOnAction(event -> {

            setInfo.setText("");

            LocalDate isDate = getAllDate.getValue();
            ChronoLocalDate chronoDate = ((isDate != null) ? getAllDate.getChronology().date(isDate) : null);

            String outputRate = "";
            if (chronoDate != null) {
                outputRate = GetUrl.getUrlContent("https://free.currconv.com/api/v7/convert?apiKey=" + apiKey + "&q=" +
                        choiceBoxMain.getValue() + "_" + choiceBoxConvert.getValue() + "," +
                        choiceBoxConvert.getValue() + "_" + choiceBoxMain.getValue() +
                        "&compact=ultra&date=" + dateFormatter.format(chronoDate));
            }

            if (!outputRate.isEmpty()) {
                JSONObject tmp = new JSONObject(outputRate);
                JSONObject main = tmp.getJSONObject(choiceBoxMain.getValue() + "_" + choiceBoxConvert.getValue());
                JSONObject inverse = tmp.getJSONObject(choiceBoxConvert.getValue() + "_" + choiceBoxMain.getValue());
                double getMain = main.getDouble(dateFormatter.format(chronoDate));
                double getInverse = inverse.getDouble(dateFormatter.format(chronoDate));
                setRate.setText("Rate: " + getMain);
                setInverse.setText("Inverse: " + getInverse);
            }else {
                setInfo.setText("Free version API only allows up to 1 year earlier.");
                setRate.setText("Rate: ");
                setInverse.setText("Inverse: ");
            }
        });
    }
}