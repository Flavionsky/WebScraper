package Scraper;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Item {
    private String title;
    private String url;
    private Double price;
    private String imgUrl;

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", price=" + price + '\'' +
                ", imgUrl=" + imgUrl +
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
