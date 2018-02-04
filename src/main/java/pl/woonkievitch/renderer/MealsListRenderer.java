package pl.woonkievitch.renderer;

import pl.woonkievitch.domain.Meal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

public class MealsListRenderer  extends JLabel implements ListCellRenderer{
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Meal meal = (Meal) value;
        setOpaque(true);

        ImageIcon image = getImage(meal.getImgUrl());

        setOpaque(true);
        setIcon(image);
        String currency = "zł";
        setHorizontalTextPosition(JLabel.CENTER);
        setVerticalTextPosition(JLabel.BOTTOM);
        setHorizontalAlignment(JLabel.CENTER);
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setText("<html><div style=\"text-align:center;\">"+meal.getName()+"<br>"+String.format("%.2f", meal.getPrice())
                +" "+ currency+"</div></html>");

        if (!cellHasFocus) {
            setBackground(Color.white);
            setForeground(Color.GRAY);
            setFont(new Font("Arial", Font.PLAIN, 11));
        }else {
            setBackground(Color.decode("#42961e"));
            setForeground(Color.white);
            setFont(new Font("Arial", Font.BOLD, 14));
        }

        return this;
    }

    private ImageIcon getImage(String path){
        URL url = getClass().getClassLoader().getResource(path);
        ImageIcon imageIcon = new ImageIcon(url);
        Image img = imageIcon.getImage();
        img = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH );
        imageIcon = new ImageIcon(img);
        return imageIcon;
    }
}
