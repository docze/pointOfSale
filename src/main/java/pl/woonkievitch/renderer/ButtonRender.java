package pl.woonkievitch.renderer;

import com.sun.imageio.plugins.common.I18N;
import pl.woonkievitch.domain.Meal;
import pl.woonkievitch.utility.I18NFacade;
import pl.woonkievitch.view.MultiLang.MultiLangButton;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.UnsupportedEncodingException;

public class ButtonRender extends JButton implements TableCellRenderer {
    String action;
    public ButtonRender(String action) {
        setOpaque(true);
        this.action = action;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        try {
            setText(I18NFacade.getMessage(action));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return this;
    }
}
