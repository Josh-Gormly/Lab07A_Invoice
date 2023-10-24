
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
public class InvoiceFrame extends JFrame
{
    JPanel mainPnl;
    JPanel entryPnl;
    JPanel addressPnl;
    JPanel productInfoPnl;
    JPanel invoicePnl;
    JPanel btnPnl;
    JButton quitBtn;
    JButton submitBtn;
    JButton clearBtn;
    JButton addBtn;
    JTextArea invoiceTA;
    JScrollPane scroller;
    JTextField customerNameField;
    JTextField cityField;
    JTextField streetAddressField;
    JTextField stateField;
    JTextField zipCodeField;
    JTextField productNameField;
    JTextField unitPriceField;
    JTextField quantityField;
    JLabel customerNameLbl;
    JLabel cityLbl;
    JLabel streetAddressLbl;
    JLabel stateLbl;
    JLabel zipCodeLbl;
    JLabel productNameLbl;
    JLabel unitPriceLbl;
    JLabel quantityLbl;
    double overallTotal = 0;

    ArrayList<LineItem> lineItems = new ArrayList<>();
    Font invoiceFont = new Font("Monospaced", Font.PLAIN, 12);

    public InvoiceFrame()
    {
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());

        createEntryPnl();
        mainPnl.add(entryPnl, BorderLayout.CENTER);
        createBtnPnl();
        mainPnl.add(btnPnl, BorderLayout.SOUTH);
        createInvoicePnl();
        mainPnl.add(invoicePnl, BorderLayout.EAST);

        add(mainPnl);
        setSize(800, 600);
        setTitle("Josh's Invoice Creator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(3*(screenWidth / 4), 3 *(screenHeight / 4));
        setLocationRelativeTo(null);
    }

    private void createBtnPnl()
    {
        btnPnl = new JPanel();
        btnPnl.setLayout(new GridLayout(1,4));

        addBtn = new JButton("Add Item");
        addBtn.addActionListener((ActionEvent ae) -> addItem(productNameField.getText(),
                unitPriceField.getText(),quantityField.getText()));
        submitBtn = new JButton("Submit");
        submitBtn.addActionListener((ActionEvent ae) -> submit());
        clearBtn = new JButton("Clear");
        clearBtn.addActionListener((ActionEvent ae) -> clear());
        quitBtn = new JButton("Quit");
        quitBtn.addActionListener((ActionEvent ae) ->
        {
        int choice = JOptionPane.showConfirmDialog(quitBtn, "Do you want to quit?", "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION)
        {
            System.exit(0);}});
        btnPnl.add(addBtn);
        btnPnl.add(submitBtn);
        btnPnl.add(clearBtn);
        btnPnl.add(quitBtn);
    }

    private void createInvoicePnl()
    {
        invoicePnl = new JPanel();
        invoiceTA = new JTextArea(36,50);
        invoiceTA.setEditable(false);
        invoiceTA.setFont(invoiceFont);
        invoiceTA.append("                   Invoice                  \n");
        invoiceTA.append("_________________________________________________\n");
        invoiceTA.append(String.format("%-20s%6s%10s%10s", "Item", "Quantity", "Price($)", "Total($)" + "\n"));
        scroller = new JScrollPane(invoiceTA);
        invoicePnl.add(scroller);
    }
    private void createEntryPnl()
    {
        entryPnl = new JPanel();
        entryPnl.setLayout(new GridLayout(2,1));

        addressPnl = new JPanel();
        addressPnl.setLayout(new GridLayout(5,2));
        addressPnl.setBorder(new TitledBorder(new EtchedBorder(), "Enter The Customer's Infromation Here"));

        productInfoPnl = new JPanel();
        productInfoPnl.setLayout(new GridLayout(3,2));
        productInfoPnl.setBorder(new TitledBorder(new EtchedBorder(), "Enter the Product's Informationn Here"));

        customerNameLbl = new JLabel("Customer Name:  ");
        streetAddressLbl = new JLabel("Street Address:  ");
        cityLbl = new JLabel("City:  ");
        stateLbl = new JLabel("State:  ");
        zipCodeLbl = new JLabel("Zip-Code:  ");
        customerNameField = new JTextField();
        streetAddressField = new JTextField();
        cityField = new JTextField();
        stateField = new JTextField();
        zipCodeField = new JTextField();

        addressPnl.add(customerNameLbl);
        addressPnl.add(customerNameField);
        addressPnl.add(streetAddressLbl);
        addressPnl.add(streetAddressField);
        addressPnl.add(cityLbl);
        addressPnl.add(cityField);
        addressPnl.add(stateLbl);
        addressPnl.add(stateField);
        addressPnl.add(zipCodeLbl);
        addressPnl.add(zipCodeField);

        productNameLbl = new JLabel("Product Name:  ");
        unitPriceLbl = new JLabel("Unit Price:  ");
        quantityLbl = new JLabel("Quantity:  ");
        productNameField = new JTextField();
        unitPriceField = new JTextField();
        quantityField = new JTextField();

        productInfoPnl.add(productNameLbl);
        productInfoPnl.add(productNameField);
        productInfoPnl.add(unitPriceLbl);
        productInfoPnl.add(unitPriceField);
        productInfoPnl.add(quantityLbl);
        productInfoPnl.add(quantityField);

        entryPnl.add(addressPnl);
        entryPnl.add(productInfoPnl);
    }
    private void submit()
    {
        invoiceTA.setText(" ");
        invoiceTA.append("                Invoice               \n");
        invoiceTA.append("---------------------------------\n");
        invoiceTA.append("|" + String.format("%-30s", customerNameField.getText()) + "|\n");
        invoiceTA.append("|" + String.format("%-30s", streetAddressField.getText()) + "|\n");
        invoiceTA.append("|" + String.format("%-30s", cityField.getText() + ", " + stateField.getText() + " " + zipCodeField.getText()) + "|\n");
        invoiceTA.append("---------------------------------\n");
        invoiceTA.append(String.format("%-20s%6s%10s%10s", "Item", "Quantity", "Price($)", "Total($)") + "\n");
        invoiceTA.append("_________________________________________________\n");

        for (LineItem i : lineItems)
        {
            invoiceTA.append(i.toString());
            overallTotal += i.calculatedTotal;
        }
        invoiceTA.append("_________________________________________________\n");
        invoiceTA.append(String.format("%-5s%.2f", "Amount Due: $", overallTotal));
        customerNameField.setText("");
        customerNameField.setText("");
        streetAddressField.setText("");
        cityField.setText("");
        stateField.setText("");
        zipCodeField.setText("");
        customerNameField.setEditable(false);
        streetAddressField.setEditable(false);
        cityField.setEditable(false);
        stateField.setEditable(false);
        zipCodeField.setEditable(false);
        productNameField.setEditable(false);
        unitPriceField.setEditable(false);
        quantityField.setEditable(false);

    }
    private void clear ()
    {
        invoiceTA.setText("");
        invoiceTA.append("                             Invoice                 \n");
        invoiceTA.append("============================================\n");
        invoiceTA.append(String.format("%-20s%6s%10s%10s", "Item", "Quantity", "Price($)", "Total($)") + "\n");
        customerNameField.setText("");
        streetAddressField.setText("");
        cityField.setText("");
        stateField.setText("");
        zipCodeField.setText("");
        productNameField.setText(" ");
        unitPriceField.setText(" ");
        quantityField.setText(" ");
        customerNameField.setEditable(true);
        streetAddressField.setEditable(true);
        cityField.setEditable(true);
        stateField.setEditable(true);
        zipCodeField.setEditable(true);
        productNameField.setEditable(true);
        unitPriceField.setEditable(true);
        quantityField.setEditable(true);
        overallTotal = 0;
        lineItems.clear();
    }
    private void addItem(String productName, String unitPrice, String quantity)
    {
        try
        {
            double unitPriceConverted = Double.parseDouble(unitPrice);
            try
            {
                int quantityConverted = Integer.parseInt(quantity);
                LineItem newItem = new LineItem(productName, unitPriceConverted, quantityConverted);
                lineItems.add(newItem);
                invoiceTA.append(newItem.toString());
                productNameField.setText("");
                unitPriceField.setText("");
                quantityField.setText("");
            }catch(NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(null, "Quantity is not the correct format");
            }
        }catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(null, "Unit Price is not the correct format");
        }
    }
}

