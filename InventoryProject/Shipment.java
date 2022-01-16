import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
public class Shipment
{
    private String[] shipment;
    private LocalDate shipmentDate;
    private LocalTime shipmentTime;
    
    public LocalDate getCurrentDate()
    {
        shipmentDate = LocalDate.now();
        return shipmentDate;
    }
    public LocalTime getCurrentTime()
    {
        shipmentTime = LocalTime.now();
        return shipmentTime;
    }
    public void setShipment(String[] shipment)
    {
        this.shipment = shipment;
        writeToFile();
    }
    public String[] getShipment()
    {
        return shipment;
    }
    private void writeToFile()
    {
        String list = "Item,    Date,       Time\n";
        String body = "";
        for (int x = 0; x < shipment.length; ++x)
        {
            body += shipment[x] + ", " + getCurrentDate() + ", " + getCurrentTime() + "\n";
        }
        list += body;

        JFileChooser fileSave = new JFileChooser();
        int saveOption = fileSave.showSaveDialog(null);
        if (saveOption == JFileChooser.APPROVE_OPTION)
        {
            try(FileWriter fileWrite = new FileWriter(fileSave.getSelectedFile() + ".txt"))
            {
                fileWrite.write(list);
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, "An error occurred. Try Again");
            }
        }
    }
    /*public void testShipment()
    {
        for (int x = 0; x < shipment.length; ++x)
            System.out.println(shipment[x]);
        System.out.println("----------");
    }*/
}
