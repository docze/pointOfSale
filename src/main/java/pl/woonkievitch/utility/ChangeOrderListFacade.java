package pl.woonkievitch.utility;

import pl.woonkievitch.event.changeOrderList.ChangeOrderListEvent;
import pl.woonkievitch.event.changeOrderList.ChangeOrderListListener;

import javax.swing.event.EventListenerList;

public class ChangeOrderListFacade {
    static EventListenerList listenerList = new EventListenerList();

    static public void addListener(ChangeOrderListListener listener){
        listenerList.add(ChangeOrderListListener.class, listener);
    }
    public static void fireChangLang(ChangeOrderListEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
            if (listeners[i] == ChangeOrderListListener.class) {
                ((ChangeOrderListListener) listeners[i+1]).changeOrderListEvent(evt);
            }
        }
    }
}
