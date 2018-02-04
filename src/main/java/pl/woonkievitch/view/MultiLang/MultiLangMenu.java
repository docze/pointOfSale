package pl.woonkievitch.view.MultiLang;

import pl.woonkievitch.event.changeLang.ChangeLangEvent;
import pl.woonkievitch.event.changeLang.ChangeLangListener;
import pl.woonkievitch.utility.I18NFacade;

import javax.swing.*;
import java.io.UnsupportedEncodingException;

public class MultiLangMenu extends JMenu implements ChangeLangListener {
    private String codeName;
    public MultiLangMenu() {
        super();
        I18NFacade.addMyEventListener(this);
    }

    public MultiLangMenu(String codeName) {
        this.codeName = codeName;
        try {
            this.setText(I18NFacade.getMessage(codeName));
            I18NFacade.addMyEventListener(this);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    @Override
    public void langChanged(ChangeLangEvent e) {
        try {
            setText(I18NFacade.getMessage(codeName));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
    }
}
