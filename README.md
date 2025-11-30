# CurrencyConverter

A Java 20.0.1 command-line currency converter using **ExchangeRate API**
and **Gson 2.10.1**.\
Supports real-time rates, robust input validation, formatted output with
thousand separators, and a dynamic menu system.

## 🔧 Technologies

-   **Java 20.0.1**
-   **Gson 2.10.1**
-   **ExchangeRate-API**
-   Runs on **IntelliJ IDEA**, using environment variable `API_KEY`
    configured in the Run Configuration.

## 📦 Project Structure

    .
    ├── CurrencyConverter.iml
    └── src
        ├── AppConfig.java
        ├── Currency.java
        ├── CurrencyConversionService.java
        ├── CurrencyConverterApp.java
        ├── CurrencyInfo.java
        ├── CurrencyLoader.java
        ├── CurrencyUtils.java
        ├── MenuController.java
        ├── MenuGenerator.java
        ├── MenuOption.java
        └── PaginationUtil.java

## ⚙️ Requirements

-   Java 20.0.1\
-   gson-2.10.1.jar\
-   Environment variable `API_KEY` set in IntelliJ or your shell

## ▶️ Running

``` bash
export API_KEY="your_api_key"
javac -cp "lib/gson-2.10.1.jar" src/*.java
java -cp "src:lib/gson-2.10.1.jar" CurrencyConverterApp
```

## 📌 Example Output

    45215.78 BRL → 221,787.92 ZWL

## 🤝 Contributing

1.  Fork the repo\
2.  Create a branch\
3.  Commit and push\
4.  Open a Pull Request

## 📄 License

MIT License
