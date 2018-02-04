package pl.woonkievitch.utility;

import pl.woonkievitch.Exception.IncorrectStringPathException;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImageUtil {
    private static ImageUtil ourInstance = new ImageUtil();

    public static ImageUtil getInstance() {
        return ourInstance;
    }

    private ImageUtil() {
    }

    public ImageIcon getImage(String path) throws IncorrectStringPathException {
        if(path!= null){
            URL url = getClass().getClassLoader().getResource(path);
            ImageIcon imageIcon = new ImageIcon(url);
            return imageIcon;
        }else{
            throw new IncorrectStringPathException();
        }

    }

    public ImageIcon getImage(String path, int width, int height) throws IncorrectStringPathException {
        if(path!= null){
        URL url = getClass().getClassLoader().getResource(path);
        ImageIcon imageIcon = new ImageIcon(url);
        return scaleImage(imageIcon, width, height);
        }else{
            throw new IncorrectStringPathException();
        }
    }

    public ImageIcon scaleImage(ImageIcon img, int width, int height){
        Image tmp = img.getImage();
        tmp = tmp.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(tmp);
    }
}
