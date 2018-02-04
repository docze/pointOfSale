package pl.woonkievitch.event.changeRecipeValue;

import java.util.EventListener;

public interface ChangeRecipeValueListener extends EventListener {
    void valueChangeEvent(ChangeRecipeValueEvent e);
}
