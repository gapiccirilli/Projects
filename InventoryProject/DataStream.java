import java.nio.file.*;
import java.io.*;
import static java.nio.file.StandardOpenOption.*;
public class DataStream 
{
    private String[] inventoryAndQuant;
    private Inventory currentInventory;

    public DataStream(Inventory inv)
    {
        currentInventory = inv;
    }
    public String[] getInventory()
    {
        readData();
        return inventoryAndQuant;
    }
    public void writeData()
    {
        String str = "";
        for(int x = 0; x < currentInventory.getInventory().length; ++x)
        {
            str += currentInventory.getInventory()[x] + " (" + currentInventory.getQuantities()[x] + "),";
        }
        Path path = Paths.get("shipments" + FileSystems.getDefault().getSeparator() + "current_inventory");
        try
        {
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(path, TRUNCATE_EXISTING));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            writer.write(str, 0, str.length());
            writer.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void readData()
    {
        String str = "";
        Path path = Paths.get("shipments" + FileSystems.getDefault().getSeparator() + "current_inventory");
        try
        {
            InputStream input = new BufferedInputStream(Files.newInputStream(path));
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String[] array, invArray;
            str = reader.readLine();
            if (str != null)
            {
                array = str.split(",");
                invArray = new String[array.length + 1];
                invArray[0] = "-Inventory-";
                for (int x = 1; x < invArray.length; ++x)
                {
                    invArray[x] = array[x - 1];
                    //for testing: System.out.println(invArray[x]);
                }
                populateInventory(invArray);
                inventoryAndQuant = invArray;
            }
            else
            {
                String[] placeHolder = {"-Inventory-"};
                inventoryAndQuant = placeHolder;
            }
            reader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void populateInventory(String[] inventory)
    {
        int firstIndex, lastIndex;
        String[] items = new String[inventory.length];
        int[] quantities = new int[inventory.length];
        
        //ArrayList<String> itemsArray = new ArrayList<>();
        //ArrayList<String> quantsArray = new ArrayList<>();
        for (int x = 1; x < items.length; ++x)
        {
            StringBuilder item = new StringBuilder(inventory[x]);
            firstIndex = item.indexOf(" ");

            items[x - 1] = item.substring(0, firstIndex);
            firstIndex = item.indexOf("(");
            lastIndex = item.indexOf(")");
            quantities[x - 1] = Integer.parseInt(item.substring(firstIndex + 1, lastIndex));
            
            //all that is left is to add these 2 arrays to the inventory object using setQuantity and setInventory methods 
            //or however I wrote those methods as
        }
        currentInventory.setInventory(items);
        currentInventory.setQuantity(quantities);
    }
}
