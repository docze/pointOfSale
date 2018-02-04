package pl.woonkievitch.event.changeOrderList;

import java.util.EventListener;

public interface ChangeOrderListListener extends EventListener {
    void changeOrderListEvent(ChangeOrderListEvent e);
}
