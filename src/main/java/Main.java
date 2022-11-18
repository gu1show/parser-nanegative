import model.Review;
import processing.DataWriter;
import processing.SiteParser;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("===== Welcome to the nanegative.ru parser! =====");
        System.out.println("Input a link of the page that you need to parse: ");

        Scanner scanner = new Scanner(System.in);
        String url = scanner.nextLine();

        SiteParser parser = new SiteParser(url);
        ArrayList<Review> allReviews = parser.parse();

        DataWriter writer = new DataWriter(allReviews);
        writer.saveReviewsToPdf("out/reviews.pdf");
    }
}
