package pl.woonkievitch.view.OrderService;

import pl.woonkievitch.dao.OrderDAO;
import pl.woonkievitch.dao.impl.OrderDAOImpl;
import pl.woonkievitch.domain.Meal;
import pl.woonkievitch.domain.Orders;
import pl.woonkievitch.event.changeOrderStatusEvent.ChangeOrderStatusEvent;
import pl.woonkievitch.event.changeOrderStatusEvent.ChangeOrderStatusListener;
import pl.woonkievitch.renderer.ButtonCellEditor;
import pl.woonkievitch.renderer.ButtonRender;
import pl.woonkievitch.utility.Order;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import static pl.woonkievitch.SetupInt.PICK_UP;
import static pl.woonkievitch.SetupInt.REMOVE_ORDER;

public class OrderServiceView extends JPanel implements ChangeOrderStatusListener {
    String[] columnNames;
    public OrderServiceView() {
        setLayout(new BorderLayout());
        Order.getInstance().addStatusListener(this);
        columnNames = new String[]{"", "", ""};
        Object[][] data =  prepareDataTable();
        createTable(data);

    }

    private Object[][] prepareDataTable() {
        OrderDAO orderDAO = new OrderDAOImpl();
        List<Orders> ordersList = unique(orderDAO.findAll());

        Object[][] data = new Object[ordersList.size()][3];
        int i = 0;
        for(Orders orders:ordersList){
            data[i][0] = orders.getId();
            data[i][1] = orders.getId();
            data[i][2] = orders.getId();
            i++;
        }
        return data;
    }
    private void createTable(Object[][] data){
        JTable table = new JTable(data, columnNames);
        table.getColumnModel().getColumn(1).setCellEditor( new ButtonCellEditor(PICK_UP));
        table.getColumnModel().getColumn(1).setCellRenderer(new ButtonRender(PICK_UP));
        table.getColumnModel().getColumn(2).setCellEditor( new ButtonCellEditor(REMOVE_ORDER));
        table.getColumnModel().getColumn(2).setCellRenderer(new ButtonRender(REMOVE_ORDER));
        setPreferredSize(new Dimension(800, 400));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(table);

        add(scrollPane, BorderLayout.CENTER);
        setBackground(Color.white);
    }
    private List<Orders> unique(List<Orders> orders){
        Orders[] objects =  orders.toArray(new Orders[orders.size()]);
        List<Orders> ordersList = new LinkedList<>();
        int count;
        int tmp;
        for(int i = 0; i < orders.size(); i++){
            if(objects[i]!=null){
                tmp = objects[i].getId();
                for(int j = i+1; j < orders.size() && objects[i]!=null;j++){
                    if(tmp == objects[j].getId()){
                        objects[j] = null;
                    }
                }
                ordersList.add(objects[i]);
            }

        }
        return  ordersList;
    }


    @Override
    public void orderStatusChange(ChangeOrderStatusEvent e) {
        new SwingWorker<Void, Void>(){
            @Override
            protected Void doInBackground() throws Exception {
                Object[][] objects = prepareDataTable();
                remove(0);
                createTable(objects);
                revalidate();
                repaint();
                return null;
            }
        }.execute();
    }
}
