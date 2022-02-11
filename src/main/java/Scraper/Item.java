package Scraper;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Item {
    private String title;
    private String url;
    private Double price;

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", price=" + price +
                '}';
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
