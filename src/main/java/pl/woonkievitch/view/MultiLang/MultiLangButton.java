package pl.woonkievitch.view.MultiLang;

import pl.woonkievitch.event.changeLang.ChangeLangEvent;
import pl.woonkievitch.event.changeLang.ChangeLangListener;
import pl.woonkievitch.utility.I18NFacade;

import javax.swing.*;
import java.io.UnsupportedEncodingException;

public class MultiLangButton extends JButton implements ChangeLangListener {
    private String codeName;
    protected String langName;

    public MultiLangButton(){
        super();
        I18NFacade.addMyEventListener(this);
    }
    public MultiLangButton(String s, ImageIcon image) {
        super();
        I18NFacade.addMyEventListener(this);
    }

    public MultiLangButton(String codeName) {
        this.codeName = codeName;
        try {
            langName = I18NFacade.getMessage(codeName);
            this.setText(langName);
            I18NFacade.addMyEventListener(this);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    protected void getNewWord(){
        try {
            langName = I18NFacade.getMessage(codeName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
        try {
            langName = I18NFacade.getMessage(codeName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void langChanged(ChangeLangEvent e) {
        getNewWord();
        setText(langName);
    }
}
