import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
public class MainFrame extends JFrame implements ActionListener
{
    private CRUD functions = new CRUD();
    DataStream storedData = new DataStream(functions.getInventory());
    private String[] items = storedData.getInventory();

    JLabel inventoryLabel;
    JLabel currentShipment;
    JLabel shipments;
    JButton createButton;
    JButton editButton;
    JButton deleteButton;
    JButton viewList;
    JButton addItem;
    JButton pushShipment;
    JButton help;
    JButton remove;
    JButton save;
    JButton addTo;
    JComboBox<String> inventory;
    DefaultListModel<String> leftList;
    DefaultListModel<String> rightList;
    JList<String> currentList;
    JList<String> shipmentList;
    //panels grid to separate out components
    JPanel center;
    JPanel bottom;
    JPanel left;
    JPanel right;
    JPanel middle;
    public MainFrame(String title)
    {
        super(title);
        //frame components
        inventory = new JComboBox<>(items);
        inventoryLabel = new JLabel("Inventory: ");
        currentShipment = new JLabel("Current Shipment");
        shipments = new JLabel("Shipments");
        createButton = new JButton("Create");
        createButton.setEnabled(false);
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        deleteButton.setEnabled(false);
        viewList = new JButton("View Inventory");
        addItem = new JButton("Add");
        addItem.setEnabled(false);
        pushShipment = new JButton("Push Shipment");
        pushShipment.setEnabled(false);
        remove = new JButton("Remove Item(s)");
        remove.setEnabled(false);
        save = new JButton("Apply");
        save.setEnabled(false);
        addTo = new JButton("Add To");
        addTo.setEnabled(false);
        help = new JButton("Help");

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        middle = new JPanel(new FlowLayout());
        //container for center
        center = new JPanel(new FlowLayout(FlowLayout.TRAILING, 10, 5));
        //container for bottom buttons
        bottom = new JPanel(new FlowLayout());
        //container for current shipment list
        leftPanel();
        //container for all shipments
        rightPanel();
        //container for inventory list
        centerPanel();
        //container for buttons
        bottomPanel();
        //container for create
        middlePanel();
        //adds different functionality classes as listeners for each button
        buttonActions();
        
        add(center, BorderLayout.PAGE_START);
        add(left, BorderLayout.LINE_START);
        add(right, BorderLayout.LINE_END);
        add(bottom, BorderLayout.PAGE_END);
        add(middle, BorderLayout.CENTER);
        validate();
    }
    private void leftPanel()
    {
        left = new JPanel();
        BoxLayout leftBox = new BoxLayout(left, BoxLayout.Y_AXIS);
        left.setLayout(leftBox);
        leftList = new DefaultListModel<>();
        leftList.addElement("Add Items");
        currentList = new JList<>(leftList);
        currentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane leftScroll = new JScrollPane(currentList);

        left.add(currentShipment);
        left.add(leftScroll);
        left.add(remove);
    }
    private void rightPanel()
    {
        right = new JPanel();
        BoxLayout rightBox = new BoxLayout(right, BoxLayout.Y_AXIS);
        right.setLayout(rightBox);
        rightList = new DefaultListModel<>();
        rightList.addElement("Ship Items");
        shipmentList = new JList<>(rightList);
        JScrollPane rightScroll = new JScrollPane(shipmentList);

        right.add(shipments);
        right.add(rightScroll);
        right.add(pushShipment);
    }
    private void middlePanel()
    {
        middle.add(createButton);
    }
    private void centerPanel()
    {
        //center.add(addTo);
        center.add(inventoryLabel);
        center.add(inventory);
        center.add(addItem);
        center.add(help);
    }
    private void bottomPanel()
    {
        bottom.add(addTo);
        bottom.add(editButton);
        bottom.add(save);
        bottom.add(deleteButton);
        bottom.add(viewList);
        //bottom.add(help);
    }
    private void buttonActions()
    {
        inventory.addActionListener(this);
        createButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        viewList.addActionListener(this);
        addItem.addActionListener(this);
        pushShipment.addActionListener(this);
        remove.addActionListener(this);
        help.addActionListener(this);
        save.addActionListener(this);
        addTo.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent event)
    {
        Inventory inv = functions.getInventory();
        String value = currentList.getSelectedValue();
        Object source = event.getSource();
        if (leftList.getSize() > 1)
            createButton.setEnabled(true);

        if (source == createButton)
        {
            int index = currentList.getSelectedIndex();
            if (index == 0)
                currentList.clearSelection();
            else if (currentList.isSelectionEmpty())
            {
                currentList.setSelectedIndex(1);
            }
            else
            {
                rightList.addElement(value);
                leftList.removeElementAt(index);
                if (leftList.getSize() <= 1)
                {
                    createButton.setEnabled(false);
                }
                else
                    currentList.setSelectedIndex(index - 1);
                if (rightList.size() > 1)
                    pushShipment.setEnabled(true);
            }
            repaint();
        }
        else if (source == editButton)
        {
            editButton.setEnabled(functions.edit());
            functions.setEdit(true);
            save.setEnabled(functions.edit());
            addItem.setEnabled(functions.edit());
            remove.setEnabled(functions.edit());

            if (inventory.getSelectedIndex() == 0)
                deleteButton.setEnabled(false);
            else
                deleteButton.setEnabled(functions.edit());
        }
        else if (source == save)
        {
            editButton.setEnabled(functions.edit());
            functions.setEdit(false);
            save.setEnabled(functions.edit());
            addItem.setEnabled(functions.edit());
            deleteButton.setEnabled(functions.edit());
            remove.setEnabled(functions.edit());
        }
        else if (source == deleteButton)
        {
            //Inventory inv = functions.getInventory();
            String item = (String)inventory.getSelectedItem();
            int startIndex = item.indexOf('(');
            int endIndex = item.indexOf(')');
            String quantityStr = item.substring(startIndex + 1, endIndex);
            int quantity = Integer.parseInt(quantityStr);
            
            if (quantity == 1)
            {
                inventory.removeItemAt(inventory.getSelectedIndex());
                int deleteIndex = inv.deleteItem(item.substring(0, (startIndex - 1)));
                inv.deleteQuantity(deleteIndex, 0);
            }
            else
            {
                quantity -= 1;
                String num = Integer.toString(quantity);
                StringBuilder tempStr = new StringBuilder(item);
                tempStr.replace(startIndex + 1, endIndex, num);
                inventory.removeItemAt(inventory.getSelectedIndex());
                inventory.insertItemAt(tempStr.toString(), inventory.getSelectedIndex() + 1);
                inventory.setSelectedIndex(inventory.getSelectedIndex() + 1);

                int deleteIndex = inv.getIndex(item.substring(0, (startIndex - 1)));
                inv.deleteQuantity(deleteIndex, quantity);

            }
            storedData.writeData();
            //inv.test();
        }
        else if (source == viewList)
        {
            String[] currentInv = inv.getInventory();
            int[] currentQuant = inv.getQuantities();
            String body = "", header = "Inventory\n--------\n", list;
            for (int x = 0; x < currentInv.length; ++x)
                body += currentInv[x] + " " + currentQuant[x] + "\n";
            list = header + body;
            if (currentInv.length == 0)
                JOptionPane.showMessageDialog(null, "No Items", "Inventory", JOptionPane.INFORMATION_MESSAGE); 
            else
                JOptionPane.showMessageDialog(null, list, "Inventory", JOptionPane.INFORMATION_MESSAGE);
            //creates a popup with a current inventory list with quantity
        }
        else if (source == addItem)
        {
            //Inventory inv = functions.getInventory();
            boolean numFlag = true;
            
            String item = JOptionPane.showInputDialog(null, "Add Inventory Item", "Add Item");
            String quantity = JOptionPane.showInputDialog(null, "How many?", "0");
            //add validation for number later
            if (quantity != null)
            {
                for (int x = 0; x < quantity.length(); ++x)
                {   
                    if (!(Character.isDigit(quantity.charAt(x))))
                    {
                        numFlag = false;
                        JOptionPane.showMessageDialog(null, "You must enter a number", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                }
            }
            if (item != null && quantity != null && numFlag != false)
            {
                String itemAndQuant = item + " (" + quantity + ")";
                int x;

                for (x = 0; x < inventory.getItemCount(); ++x)
                {
                    int firstIndex = inventory.getItemAt(x).indexOf(' ');
                    int secondIndex = inventory.getItemAt(x).indexOf(')') + 1;
                    int endingIndex = secondIndex - firstIndex;

                    if (item.equals(inventory.getItemAt(x).substring(0, (inventory.getItemAt(x).length() - endingIndex))))
                    {
                        String selectedItem = inventory.getItemAt(x);
                        int startIndex = selectedItem.indexOf('(');
                        int endIndex = selectedItem.indexOf(')');
                        String quantityStr = selectedItem.substring(startIndex + 1, endIndex);
                        int selectedQuantity = Integer.parseInt(quantityStr);
                        int newQuantity = Integer.parseInt(quantity);
                        int combinedQuantity = selectedQuantity + newQuantity;

                        String num = Integer.toString(combinedQuantity);
                        StringBuilder tempStr = new StringBuilder(selectedItem);
                        tempStr.replace(startIndex + 1, endIndex, num);
                        inventory.removeItemAt(x);
                        inventory.insertItemAt(tempStr.toString(), x);
                        inventory.setSelectedIndex(x);

                        int i = inv.setInventoryItem(selectedItem.substring(0, (startIndex - 1)));
                        if (i != -1)
                            inv.setQuantity(i, num);
                        else
                            inv.setQuantity(num);
                        break;
                    }
                }
                if (x == inventory.getItemCount())
                {
                    inv.setInventoryItem(item);
                    inv.setQuantity(quantity);
                    inventory.addItem(itemAndQuant);
                    inventory.setSelectedIndex(x);
                }
                storedData.writeData();
            }
        }
        else if (source == addTo)
        {
            int index = inventory.getSelectedIndex();
            String item = inv.getCurrentlySelectedItem(index - 1);
            int quantity = inv.getCurrentlySelectedQuantity(index - 1);

            leftList.addElement(item);
            if (quantity == 1)
            {
                inv.deleteItem(item);
                inv.deleteQuantity(index - 1, quantity - 1);
                inventory.removeItemAt(index);
                inventory.setSelectedIndex(index - 1);
            }
            else
            {
                inv.deleteQuantity(index - 1, quantity - 1);
                inventory.removeItemAt(index);
                inventory.insertItemAt(item + " (" + (quantity - 1) + ")", index);
                inventory.setSelectedIndex(index);
            }
            storedData.writeData();
            //X retrieve index from combobox of currently selected
            //X use index to retrieve values from Inventory and Quantity ArrayLists
            //X add item + quantity into current shipment list
            //X delete amount added from current inventory. Create a delete class to refactor code
        }
        else if (source == pushShipment)
        {
            Shipment shipment = functions.getShipment();
            Object[] shipObjs = rightList.toArray();
            String[] shipStrs = new String[shipObjs.length - 1];
            for (int x = 0, y = 1; x < shipStrs.length; ++x, ++y)
                shipStrs[x] = (String)shipObjs[y];
            shipment.setShipment(shipStrs);

            rightList.removeRange(1, rightList.size() - 1);
            pushShipment.setEnabled(false);
            //shipment.testShipment();
            //stores shipment in shipment object inside CRUD and then another method writes it to a CSV file
        }
        else if (source == help)
        {
            String message = "Note - In order to use \"Remove Items\", \"Delete\" and \"Add\",\n"
            + "you must select the \"Edit\" Button, then click \"Apply\".\n\n"
            + "Inventory - This drop-down displays your current Inventory. When you\n"
            + "add new items, put items in your Current Shipment or remove items from your\n\n"
            + "Current Shipment, your Inventory drop-down reflects it.\n\n"
            + "Add - This button opens two dialogs to add new items to your inventory.\n"
            + "The first is the item itself, and the second is the quantity. If you are adding\n"
            + "another item to an existing item stock, you must type it exactly the same as it\n"
            + "appears in the inventory.\n\n"
            + "Create - This button will move the currently selected item from the Current Shipment,\n"
            + "to Shipment, where the shipment can be created.\n\n"
            + "Add To - This button adds items from your inventory to the Current Shipment.\n\n"
            + "Remove Item(s) - This button removes the selected item from Current Shipment and\n\n"
            + "places it back into your inventory.\n"
            + "Edit - Allows the use of Remove Items, Delete and Add.\n\n"
            + "Apply - Apply's changes and renders certain buttons unusable until Edit is selected again.\n\n"
            + "Delete - This button deletes one item at a time from your inventory.\n\n"
            + "View Inventory - This button displays a full Inventory list.\n\n"
            + "Push Shipment - This button creates a shipment of items from the Shipment list and\n"
            + "exports the list to its own CSV file for record\n\n"
            + "Note - Whenever you exit your application with items in your inventory, you can\n"
            + "restore with the previous inventory from a text file, written to\n"
            + "and read from automatically each time there is any change.";
            JOptionPane.showMessageDialog(null, message, "Help", JOptionPane.QUESTION_MESSAGE);
            //creates popup with instructions
        }
        else if (source == remove)
        {
            int num = currentList.getSelectedIndex();
            if (num == 0)
                currentList.clearSelection();

            if (!currentList.isSelectionEmpty())
            {
                String currentItem = currentList.getSelectedValue();
                int index = inv.setInventoryItem(currentItem);
                int comboIndex = inventory.getSelectedIndex();

                if (index == -1)
                {
                    inv.setQuantity(index, Integer.toString(1));
                    String concatStr = currentItem + " (1)";
                    inventory.removeItemAt(comboIndex);
                    inventory.insertItemAt(concatStr, comboIndex);
                }
                else
                {
                    int quantity = inv.getCurrentlySelectedQuantity(index) + 1;
                    inv.setQuantity(index, Integer.toString(quantity));

                    String concatStr = currentItem + " (" + quantity + ")";
                    inventory.removeItemAt(comboIndex);
                    inventory.insertItemAt(concatStr, comboIndex);
                }
                inventory.setSelectedIndex(comboIndex);
                int listIndex = currentList.getSelectedIndex();
                leftList.removeElementAt(listIndex);

                if (leftList.getSize() <= 1)
                    createButton.setEnabled(false);
                else
                    currentList.setSelectedIndex(listIndex - 1);

                storedData.writeData();
            }
            
            //inv.test();
        }
        else if (source == inventory)
        {
            if (inventory.getSelectedIndex() == 0)
            {
                deleteButton.setEnabled(false);
                addTo.setEnabled(false);
            }
            else
            {  
                deleteButton.setEnabled(functions.edit());
                addTo.setEnabled(true);
            }

        }
    }
}
