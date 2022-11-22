# Nanegative parser

The parser of reviews from nanegative.ru site.

## Preparations

- download Selenium WebDriver for your version of a browser

 Link: https://www.selenium.dev/documentation/webdriver/getting_started/install_drivers/

 - change path to Selenium WebDriver **at SiteParser.java**

- change path to saving reviews to PDF **at Main.java**

- change path to font for PDF file **at DataWriter.java** 

## Requarements

- Selenium

Maven dependency:

```xml
<dependency>
    <groupId>org.seleniumhq.selenium<groupId>
    <artifactId>selenium-java</artifactId>
    <version>4.6.0</version>
</dependency>
```

- IText Core

Maven dependency:

```xml
<dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>itextpdf</artifactId>
    <version>5.5.13.3</version>
</dependency>
```

## License
[MIT](https://choosealicense.com/licenses/mit/)
