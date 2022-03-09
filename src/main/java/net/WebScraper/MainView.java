package net.WebScraper;

import Scraper.Item;
import Scraper.ScraperController;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.Locale;

@Route("")
public class MainView extends VerticalLayout {

    public MainView() {

        TextField taskField = new TextField();
        ScraperController scraper = new ScraperController();
        final String URL = "https://www.amazon.it";
        final String UrlSearch = "/s?k=";
        Grid<Item> grid = new Grid<>();

        grid.addColumn(Item::getTitle).setHeader("Titolo");
        grid.addColumn(new ComponentRenderer<>(item -> {
            Image image = new Image(item.getImgUrl(),
                    item.getTitle());
            return image;
        })).setWidth("200px").setHeader("Immagine");
        grid.addColumn(new NumberRenderer<>(
                Item::getPrice, "%(,.2f €",
                Locale.ITALIAN, "Non definito")
        ).setHeader("Prezzo")
                .setFlexGrow(0)
                .setWidth("150px")
                .setResizable(false);
        grid.addColumn(TemplateRenderer.<Item> of("<a href='"+ URL + "[[item.url]]"+ "'>Apri Offerta</a>")
                                .withProperty("url", Item::getUrl),
                        "url").setHeader("Link");
        Button addButton = new Button("Cerca");
        addButton.addClickListener(click -> {
            grid.setItems(scraper.webScraper(taskField.getValue(), URL+UrlSearch));
        });
        addButton.addClickShortcut(Key.ENTER);

        add(
                new H1("Web Scraper"),
                new HorizontalLayout(
                        taskField,
                        addButton
                ),
                grid
        );
    }
}