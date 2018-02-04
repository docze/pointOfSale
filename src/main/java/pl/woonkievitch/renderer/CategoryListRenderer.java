package pl.woonkievitch.renderer;

import pl.woonkievitch.Exception.IncorrectStringPathException;
import pl.woonkievitch.domain.Category;
import pl.woonkievitch.event.changeLang.ChangeLangEvent;
import pl.woonkievitch.event.changeLang.ChangeLangListener;
import pl.woonkievitch.utility.I18NFacade;
import pl.woonkievitch.utility.ImageUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.UnsupportedEncodingException;


public class CategoryListRenderer extends JLabel implements ListCellRenderer, ChangeLangListener{
    private Font font = new Font("helvitica", Font.BOLD, 24);
    private Category category;

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        I18NFacade.addMyEventListener(this);
        this.category = (Category) value;
        ImageIcon image = null;
        try {
            image = ImageUtil.getInstance().getImage(category.getImgUrl(), 40, 40);
        } catch (IncorrectStringPathException e) {
            e.printStackTrace();
        }

        setOpaque(true);
        setIcon(image);
        String currency = "zł";
        setHorizontalTextPosition(JLabel.CENTER);
        setVerticalTextPosition(JLabel.BOTTOM);
        setHorizontalAlignment(JLabel.CENTER);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        try {
            setText(I18NFacade.getMessage(category.getName()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (!isSelected) {
            setBackground(Color.white);
            setForeground(Color.GRAY);
            setFont(new Font("Arial", Font.PLAIN, 11));
        }else {
            setBackground(Color.cyan);
            setForeground(Color.BLACK);
            setFont(new Font("Arial", Font.PLAIN, 14));
        }
        return this;
    }


    @Override
    public void langChanged(ChangeLangEvent e) {
        try {
            setText(I18NFacade.getMessage(category.getName()));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
    }
}
