package pl.woonkievitch.event.changeOrderStatusEvent;

import java.util.EventListener;

public interface ChangeOrderStatusListener extends EventListener{
    void orderStatusChange(ChangeOrderStatusEvent e);

}
