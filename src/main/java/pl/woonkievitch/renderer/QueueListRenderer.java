package pl.woonkievitch.renderer;

import pl.woonkievitch.view.MultiLang.MultiLangLabel;

import javax.swing.*;
import java.awt.*;

public class QueueListRenderer extends Label implements ListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Integer value1 = (Integer) value;
        JLabel label = new JLabel();
        String name = label.getFont().getName();
        int size = label.getFont().getSize();

        String text = label.getText();
        Font font = new Font(name, Font.BOLD, size);
        setForeground(Color.black);
        setFont(font);
        setText(value1+"");
        return this;
    }
}
