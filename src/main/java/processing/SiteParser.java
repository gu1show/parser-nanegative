package processing;

import model.Review;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class SiteParser {
    private final String url;

    public SiteParser(String url) {
        this.url = url;
    }

    public ArrayList<Review> parse() {
        System.setProperty("webdriver.chrome.driver", "selenium/chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        webDriver.get(url);

        WebElement paginator = null;
        try {
            paginator = webDriver.findElement(By.className("pagination-holder"));
        } catch (Exception ignored) {}

        int numberOfPages = 1;
        if (paginator != null) numberOfPages = getNumberPages(paginator);

        ArrayList<Review> allReviews = new ArrayList<>();
        for (int i = 1; i <= numberOfPages; i++) {
            if (i != 1) {
                webDriver.get(url + "?page=" + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            WebElement blockOfReviews = webDriver.findElement(By.className("reviewers-block"));
            List<WebElement> listOfReviews = blockOfReviews.findElements(By.className("reviewers-box"));

            ArrayList<Review> reviewsOnThePage = getReviews(listOfReviews);
            allReviews.addAll(reviewsOnThePage);
        }

        webDriver.quit();

        return allReviews;
    }

    private int getNumberPages(WebElement paginator) {
        WebElement allPagesClass = paginator.findElement(By.className("all-pages"));
        String messageAboutNumberOfPages = allPagesClass.getText();

        int indexLastSpace = messageAboutNumberOfPages.lastIndexOf(" ");
        if (indexLastSpace > -1)
            return Integer.parseInt(messageAboutNumberOfPages.substring(indexLastSpace + 1));
        else
            return 1;
    }

    private ArrayList<Review> getReviews(List<WebElement> listOfReviews) {
        ArrayList<Review> reviews = new ArrayList<>();

        for (WebElement review : listOfReviews) {
            WebElement reviewer = review.findElement(By.className("name"));
            List<WebElement> spanTags = reviewer.findElements(By.tagName("span"));
            int score = getScore(spanTags);

            List<WebElement> textBlock = review.findElements(By.tagName("tr"));
            String benefits = "", disadvantages = "", comment = "";
            for (int i = 0; i < textBlock.size(); i++) {
                WebElement block = textBlock.get(i);
                List<WebElement> nodes = block.findElements(By.tagName("td"));

                if (i == 0) benefits = getTextFromNode(nodes);
                else if (i == 1) disadvantages = getTextFromNode(nodes);
                else comment = getTextFromNode(nodes);
            }

            reviews.add(new Review(score, benefits, disadvantages, comment));
        }

        return reviews;
    }

    private int getScore(List<WebElement> spanTags) {
        int i = 0, score = 0;
        boolean isFound = false;
        while ((i < spanTags.size()) && (!isFound)) {
            WebElement element = spanTags.get(i);
            if (element.getAttribute("itemprop").equals("ratingValue")) {
                score = Integer.parseInt(element.getText());
                isFound = true;
            }

            i++;
        }

        return score;
    }

    private String getTextFromNode(List<WebElement> nodes) {
        StringBuilder text = new StringBuilder();
        for (int j = 1; j < nodes.size(); j++)
            text.append(nodes.get(j).getText());

        return text.toString();
    }
}
