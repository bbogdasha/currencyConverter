# Currency converter

The application allows you to get the exchange rate of two currencies. The application has a small functionality and one note. Free API provided service [free.currencyconverterapi.com](https://free.currencyconverterapi.com).
The free service has some limitations: Free version API only allows up to 1 year earlier.

### Tech

* API;
* JSON;
* JavaFX;
* FXML;
* Java 8. Lambda.

### Functions

* Selected date;
* Selected currencies;
* Note;
* Dispay about limitations free API.

### Result

**1.** At startup, the application receives a complete list of all [currencies](https://free.currconv.com/api/v7/currencies?apiKey=f749eb821b99ad4004ac) (166).

**2.** A request is sent to the service:

```java
outputRate = GetUrl.getUrlContent("https://free.currconv.com/api/v7/convert?apiKey=" + apiKey + "&q=" +
                        choiceBoxMain.getValue() + "_" + choiceBoxConvert.getValue() + "," +
                        choiceBoxConvert.getValue() + "_" + choiceBoxMain.getValue() +
                        "&compact=ultra&date=" + dateFormatter.format(chronoDate));
```
as a link (default values):

```xml
"https://free.currconv.com/api/v7/convert?apiKey=*************&q=USD_UAH,UAH_USD&compact=ultra&date=2020-07-01"
```

**3.** An illustrative example of an application:

![Gif](https://github.com/bbogdasha/currencyConverter/blob/master/gif/converter.gif)
