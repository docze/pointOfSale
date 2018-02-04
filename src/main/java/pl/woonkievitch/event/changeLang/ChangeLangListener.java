package pl.woonkievitch.event.changeLang;

import java.util.EventListener;

public interface ChangeLangListener extends EventListener {
    void langChanged(ChangeLangEvent e);
}
