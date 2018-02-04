package pl.woonkievitch.utility;

import pl.woonkievitch.event.changeLang.ChangeLangEvent;
import pl.woonkievitch.event.changeLang.ChangeLangListener;

import javax.swing.event.EventListenerList;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.List;

public class I18NFacade {
    private static EventListenerList listenerList = new EventListenerList();
    private static List<Locale> locales = new ArrayList<>();
    private static ResourceBundle resourceBundle;
    private static Locale lang;

    public static void init(){
        lang = Locale.getDefault();
        resourceBundle = ResourceBundle.getBundle("lang", lang);
    }

    public static void addMyEventListener(ChangeLangListener listener) {
        listenerList.add(ChangeLangListener.class, listener);
    }
    public static void removeMyEventListener(ChangeLangListener listener) {
        listenerList.remove(ChangeLangListener.class, listener);
    }

    public static void fireChangLang(ChangeLangEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
            if (listeners[i] == ChangeLangListener.class) {
                ((ChangeLangListener) listeners[i+1]).langChanged(evt);
            }
        }
    }

    public static void setLocale(Locale  locale) {
        if(locale != null){
            resourceBundle = ResourceBundle.getBundle("lang", locale);
        }
    }

    public static String getMessage(String key) throws UnsupportedEncodingException {
        String msg = new String(resourceBundle.getString(key).getBytes("ISO-8859-1"), "UTF-8");
        return msg;
    }
}
