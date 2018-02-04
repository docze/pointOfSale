package pl.woonkievitch.view;

import pl.woonkievitch.view.MultiLang.MultiLangButton;
import pl.woonkievitch.event.changeLang.ChangeLangEvent;
import pl.woonkievitch.utility.I18NFacade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class SettingsScreen extends JPanel implements ActionListener{
    public JComboBox<String> plafStyleComboBox;
    public JComboBox<Locale> languageComboBox;
    public JButton changeStyleButton;
    public JButton changeLanguageButton;

    public SettingsScreen(){
        plafStyleComboBox = new JComboBox<String>();
        languageComboBox = new JComboBox<Locale>();

        changeLanguageButton = new MultiLangButton("ChangeLang");
        changeStyleButton = new MultiLangButton("ChangeStyle");

        plafStyleComboBox.addItem("Motif");
        plafStyleComboBox.addItem("Windows");
        plafStyleComboBox.addItem("System");

        languageComboBox.addItem(Locale.getDefault());
        languageComboBox.addItem(Locale.US);

        changeStyleButton.addActionListener(this);
        changeLanguageButton.addActionListener(this);



        setPreferredSize(new Dimension(600, 400));
        add(plafStyleComboBox);
        add(changeStyleButton);
        add(languageComboBox);
        add(changeLanguageButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String text = source.getText();
        try {
            String lang = I18NFacade.getMessage("ChangeLang");
            String style =  I18NFacade.getMessage("ChangeStyle");
            if(text.contains(style)){
                changePlaf();
            }else if(text.contains(lang)){
                Locale selectedItem = (Locale) languageComboBox.getSelectedItem();
                I18NFacade.setLocale(selectedItem);
                I18NFacade.fireChangLang(new ChangeLangEvent(this));
            }
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

    }

    private void changePlaf(){
        String selectedStyle = (String) plafStyleComboBox.getSelectedItem();
        if(selectedStyle.contains("Windows")){
            selectedStyle = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        }
        else if (selectedStyle.contains("Motif")) {
            selectedStyle = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
        }

        else if (selectedStyle.contains("System")) {
            selectedStyle = UIManager.getCrossPlatformLookAndFeelClassName();
        } else {
            System.err.println("Unexpected value of LOOKANDFEEL specified: "
                    + selectedStyle);
            selectedStyle = UIManager.getCrossPlatformLookAndFeelClassName();
        }

        try {
            UIManager.setLookAndFeel(selectedStyle);
            SwingUtilities.updateComponentTreeUI(getRootPane());
        }catch (ClassNotFoundException e1) {
            System.err.println("Couldn't find class for specified look and feel:"
                    + selectedStyle);
            System.err.println("Did you include the L&F library in the class path?");
            System.err.println("Using the default look and feel.");
        }

        catch (UnsupportedLookAndFeelException e2) {
            System.err.println("Can't use the specified look and feel ("
                    + selectedStyle
                    + ") on this platform.");
            System.err.println("Using the default look and feel.");
        }

        catch (Exception e3) {
            System.err.println("Couldn't get specified look and feel ("
                    + selectedStyle
                    + "), for some reason.");
            System.err.println("Using the default look and feel.");
            e3.printStackTrace();
        }
    }

}
