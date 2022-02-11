package Scraper;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

public class Scraper {

    private static final String URL = "https://www.amazon.it/s?k=rtx+3060";

    public static void main(String[] args){

        WebClient client = new WebClient();
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);
        try{
            //retrieves the first page result of the research
            HtmlPage page = client.getPage(URL);

            //retrieves all the items on the page
            List<HtmlElement> items = page.getByXPath("//div[@class='sg-col-4-of-12 s-result-item s-asin sg-col-4-of-16 sg-col s-widget-spacing-small sg-col-4-of-20']");
            if(!items.isEmpty()){
                for(HtmlElement htmlItem : items){
                    //search the anchor tag and title for the single item by a class
                    HtmlAnchor itemAnchor = ((HtmlAnchor) htmlItem.getFirstByXPath(".//h2/a[@class='a-link-normal s-underline-text s-underline-link-text s-link-style a-text-normal']"));
                    //search the price by span class
                    HtmlElement spanPrice = ((HtmlElement) htmlItem.getFirstByXPath(".//span[@class='a-price-whole']"));
                    double itemPrice = spanPrice == null ? 0.00 : DecimalFormat.getNumberInstance(Locale.ITALIAN).parse(spanPrice.getVisibleText()).doubleValue();

                    //initialize the new item
                    Item item = new Item();

                    item.setTitle(itemAnchor.getTextContent());
                    item.setUrl(itemAnchor.getHrefAttribute());
                    item.setPrice(itemPrice);

                    //print the item on console
                    System.out.println(item);
                }
            }else{
                System.out.println("No items found");
            }
        }catch (IOException | ParseException exception){
            exception.printStackTrace();
        }
    }
}
