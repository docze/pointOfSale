package pl.woonkievitch;

import org.apache.log4j.Logger;
import pl.woonkievitch.event.changeLang.ChangeLangEvent;
import pl.woonkievitch.event.changeLang.ChangeLangListener;
import pl.woonkievitch.utility.I18NFacade;
import pl.woonkievitch.utility.Localization.LocalizationClient;
import pl.woonkievitch.utility.Localization.LocalizationDTO;
import pl.woonkievitch.view.MultiLang.MultiLangMenu;
import pl.woonkievitch.view.MultiLang.MultiLangMenuItem;
import pl.woonkievitch.view.MainScreen;
import pl.woonkievitch.view.OrderService.OrderServiceView;
import pl.woonkievitch.view.Queue.RealizationView;
import pl.woonkievitch.view.SettingsScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ScreenManager extends JFrame implements ActionListener, ChangeLangListener{
    private static final String MAINSCREEN = "order" ;
    private static final String SETTINGS = "settings";
    private static final String REALIZATION = "realization";
    private static final String SERVICE = "service";
    public JMenuBar menuBar;
    public JMenu menu;
    public String[] menuPosition = {"view", "settings"};
    public String[][] subMenu = {{"order", "realization", "service"}, {"settings"}};

    public JPanel screen;
    private  final Logger logger = Logger.getLogger(ScreenManager.class);

    private JPanel mainScreen = new MainScreen();
    private JPanel settings = new SettingsScreen();
    private JPanel realization = new RealizationView();
    private JPanel oorderServiceView = new OrderServiceView();
    private LocalizationDTO localization;


    public ScreenManager() {
        menuBar = new JMenuBar();
        I18NFacade.addMyEventListener(this);
        for(int i = 0; i < menuPosition.length; i++){
            menu = new MultiLangMenu(menuPosition[i]);
            for(int j = 0; j < subMenu[i].length; j++){
                MultiLangMenuItem menuItem = new MultiLangMenuItem(subMenu[i][j]);
                menuItem.addActionListener(this);
                menu.add(menuItem);
            }
            menuBar.add(menu);
        }
        setJMenuBar(menuBar);
        CardLayout cardLayout = new CardLayout();
        screen = new JPanel(cardLayout);
        screen.setPreferredSize(new Dimension(600, 400));
        setLayout(cardLayout);
        screen.add(mainScreen, MAINSCREEN );
        screen.add(settings, SETTINGS );
        screen.add(realization, REALIZATION );
        screen.add(oorderServiceView, SERVICE);
        add(screen);
        try {
            localization = LocalizationClient.getLocalization();
            setTitle(String.format("%s - %s - %s",I18NFacade.getMessage("PointOfSale") ,
                    I18NFacade.getMessage(localization.getCity()), localization.getQuery()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        cardLayout.show(screen, MAINSCREEN);
        setPreferredSize(new Dimension(800, 600));
        pack();
        setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        MultiLangMenuItem item = (MultiLangMenuItem) e.getSource();
        String cardName = item.getCodeName();
        CardLayout card = (CardLayout) screen.getLayout();
        card.show(screen, cardName);
    }

    @Override
    public void langChanged(ChangeLangEvent e) {
        try {
            setTitle(String.format("%s - %s - %s",I18NFacade.getMessage("PointOfSale") ,
                    I18NFacade.getMessage(localization.getCity()), localization.getQuery()));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
    }
}
