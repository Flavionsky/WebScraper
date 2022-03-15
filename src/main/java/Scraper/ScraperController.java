package Scraper;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Controller
public class ScraperController{

    public List<Item> webScraper(String searchString, String Url){

        searchString = searchString.replace(' ', '+');

        String searchUrl = Url + searchString;

        WebClient client = new WebClient();
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);

        ArrayList<Item> listItems = new ArrayList<>();

        try{
            //retrieves the first page result of the research
            HtmlPage page = client.getPage(searchUrl);

            //retrieves all the items on the page
            List<HtmlElement> items = page.getByXPath("//div[@class='sg-col-4-of-12 s-result-item s-asin sg-col-4-of-16 sg-col s-widget-spacing-small sg-col-4-of-20']");
            if(!items.isEmpty()){
                for(HtmlElement htmlItem : items){
                    //search the anchor tag and title for the single item by a class
                    HtmlAnchor itemAnchor = ((HtmlAnchor) htmlItem.getFirstByXPath(".//h2/a[@class='a-link-normal s-underline-text s-underline-link-text s-link-style a-text-normal']"));
                    //search the image url by img class
                    HtmlImage itemImgUrl = ((HtmlImage) htmlItem.getFirstByXPath(".//img[@class='s-image']"));
                    //search the price by span class
                    HtmlElement spanPrice = ((HtmlElement) htmlItem.getFirstByXPath(".//span[@class='a-price-whole']"));
                    double itemPrice = spanPrice == null ? 0.00 : DecimalFormat.getNumberInstance(Locale.ITALIAN).parse(spanPrice.getVisibleText()).doubleValue();

                    if(itemPrice != 0.00){
                        //initialize the new item
                        Item item = new Item();

                        item.setTitle(itemAnchor.getTextContent());
                        item.setUrl(itemAnchor.getHrefAttribute());
                        item.setPrice(itemPrice);
                        item.setImgUrl(itemImgUrl.getSrc());

                        //print the item on console
                        System.out.println(item);

                        listItems.add(item);
                    }
                }
                listItems.sort(Comparator.comparing(Item::getPrice));

                if(listItems.size() >= 10){
                    ArrayList<Item> listItemsLess = new ArrayList<>();
                    for(int i = 0; i < 10; i++){
                        listItemsLess.add(listItems.get(i));
                    }
                    return listItemsLess;
                }
                return listItems;
            }else{
                System.out.println("No items found");
            }
        }catch (IOException | ParseException exception){
            exception.printStackTrace();
        }
        return listItems;
    }
}
