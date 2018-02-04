package pl.woonkievitch.view.Queue;

import pl.woonkievitch.Exception.OrdersNotFoundException;
import pl.woonkievitch.dao.impl.OrderDAOImpl;
import pl.woonkievitch.domain.Orders;
import pl.woonkievitch.event.changeOrderStatusEvent.ChangeOrderStatusEvent;
import pl.woonkievitch.event.changeOrderStatusEvent.ChangeOrderStatusListener;
import pl.woonkievitch.utility.Order;
import pl.woonkievitch.view.MultiLang.MultiLangLabel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static pl.woonkievitch.SetupInt.*;

public class RealizationView extends JPanel implements ChangeOrderStatusListener {
    JScrollPane queuePane;
    JScrollPane pickUpPane;
    MultiLangLabel pickUpLabel;
    MultiLangLabel queueLabel;
    JList queueList;
    JList pickUpList;
    DefaultListModel<Integer> pickUpModelList;
    DefaultListModel<Integer> queueModelList;
    OrderDAOImpl orderDAO;

    public RealizationView() {
        pickUpLabel = new MultiLangLabel("pickUp");
        queueLabel = new MultiLangLabel("queue");
        pickUpModelList = new DefaultListModel<Integer>();
        queueModelList = new DefaultListModel<Integer>();
        queueList = new JList(queueModelList);
        pickUpList = new JList(pickUpModelList);

        queuePane = new JScrollPane(queueList);
        pickUpPane = new JScrollPane(pickUpList);
        orderDAO = new OrderDAOImpl();
        Order.getInstance().addStatusListener(this);
        pickUpLabel.setBackground(Color.green);
        pickUpLabel.setForeground(Color.green);
        queueLabel.setForeground(Color.gray);
        makeList(REALIZATION);
        makeList(PICK_UP);

        GridBagConstraints c = new GridBagConstraints();
        setLayout(new GridBagLayout());

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx=0;
        c.gridy=0;
        c.weightx=0.5;
        c.weighty=0.1;
        add(queueLabel, c);

        c.gridx=1;
        c.gridy=0;
        c.weightx=0.5;
        c.weighty=0.1;
        add(pickUpLabel, c);

        c.gridx=0;
        c.gridy=1;
        c.weightx=0.5;
        c.weighty=1;
        c.ipady=500;
        add(queuePane, c);

        c.gridx=1;
        c.gridy=1;
        c.weightx=0.5;
        c.weighty=1;
        c.ipady=500;
        add(pickUpPane, c);
    }

    private void makeList(String status){
        List<Orders> tmpList = null;
        try {
            tmpList = orderDAO.findByStatus(status);
        } catch (OrdersNotFoundException e) {
            e.printStackTrace();
        }
        DefaultListModel<Integer> tmpModel;
        if(status == REALIZATION){
            tmpModel = queueModelList;
        }else{
            tmpModel = pickUpModelList;
        }
        tmpModel.clear();
        for(Orders orders: tmpList){
            tmpModel.addElement(orders.getId());
        }
        //pickUpModelList.addElement(1);
    }



    @Override
    public void orderStatusChange(ChangeOrderStatusEvent e) {
        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                makeList(REALIZATION);
                makeList(PICK_UP);
                return null;
            }

            @Override
            protected void done() {
                super.done();
                revalidate();
                repaint();
            }
        };
        worker.execute();

    }
}
