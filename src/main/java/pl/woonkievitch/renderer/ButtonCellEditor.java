package pl.woonkievitch.renderer;

import pl.woonkievitch.dao.impl.OrderDAOImpl;
import pl.woonkievitch.event.changeOrderList.ChangeOrderListEvent;
import pl.woonkievitch.utility.ChangeOrderListFacade;
import pl.woonkievitch.utility.I18NFacade;
import pl.woonkievitch.utility.Order;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.util.EventObject;

import static pl.woonkievitch.SetupInt.PICK_UP;
import static pl.woonkievitch.SetupInt.REMOVE_MEAL;
import static pl.woonkievitch.SetupInt.REMOVE_ORDER;

public class ButtonCellEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    final String REMOVE = "remove";
    String value;
    JButton button;
    String action;
    public ButtonCellEditor(String action) {
        try {
            this.action = action;
            button = new JButton(I18NFacade.getMessage(action));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        button.setActionCommand(action);
        button.addActionListener(this);
        button.setOpaque(true);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.value = value.toString();
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return true;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(REMOVE_MEAL)){
            Order.getInstance().remove(value);
            ChangeOrderListFacade.fireChangLang(new ChangeOrderListEvent(this));
        }else if(e.getActionCommand().equals(REMOVE_ORDER)){
            OrderDAOImpl orderDAO = new OrderDAOImpl();
            orderDAO.remove(Integer.parseInt(value));
        }else if(e.getActionCommand().equals(PICK_UP)){
            OrderDAOImpl orderDAO = new OrderDAOImpl();
            orderDAO.toPickUp(Integer.parseInt(value));
        }
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return true;
    }
}
