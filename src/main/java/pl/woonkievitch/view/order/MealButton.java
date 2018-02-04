package pl.woonkievitch.view.order;

import pl.woonkievitch.event.changeLang.ChangeLangEvent;
import pl.woonkievitch.view.MultiLang.MultiLangButton;

import javax.swing.*;

public class MealButton extends MultiLangButton {
    private String currency;
    private Double value;
    public MealButton(String codeName) {
        super(codeName);
    }
    public MealButton(String codeName, Double price, ImageIcon icon) {
        super();
        currency = "zł";
        value = price;
        setIcon(icon);
        setCodeName(codeName);
        String s = prepareText(langName, price);
        setText(s);
    }

    private String prepareText(String name, Double price){
        String priceText = String.format("%.2f %s", price, currency).replace(".", ",");
        String s = "<html><center>" + name + "<br>< " + priceText + "</center></html>";
        return s;
    }

    @Override
    public void langChanged(ChangeLangEvent e) {
        getNewWord();
        String s = prepareText(langName, value);
        setText(s);
    }
}
