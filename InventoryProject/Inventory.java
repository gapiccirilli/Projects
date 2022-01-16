import java.util.*;
public class Inventory
{
    private ArrayList<String> quantity = new ArrayList<>();
    private ArrayList<String> inventory = new ArrayList<>();
    
    public int setInventoryItem(String currentItem)
    {
        int index = inventory.lastIndexOf(currentItem);

        if (index == -1)
            inventory.add(currentItem);
        return index;
    }
    public void setInventory(String[] items)
    {
        for (String item : items)
            inventory.add(item);
    }
    public void setQuantity(int[] quants)
    {
        for (int x = 0; x < quants.length; ++x)
        {
            quantity.add(Integer.toString(quants[x]));
        }
    }
    public void setQuantity(String currentQuantity)
    {
        quantity.add(currentQuantity);
    }
    public void setQuantity(int index, String currentQuantity)
    {
        if (index == -1)
            quantity.set(0, currentQuantity);
        else  
            quantity.set(index, currentQuantity);
    }
    public String getCurrentlySelectedItem(int index)
    {
        return inventory.get(index);
    }
    public int getCurrentlySelectedQuantity(int index)
    {
        return Integer.parseInt(quantity.get(index));
    }
    public String getLastItem()
    {
        return inventory.get(inventory.size() - 1);
    }
    public int getLastQuantity()
    {
        String num = quantity.get(quantity.size() - 1);
        return Integer.parseInt(num);
    }
    public String getFirstItem()
    {
        return inventory.get(0);
    }
    public int getFirstQuantity()
    {
        String num = quantity.get(0);
        return Integer.parseInt(num);
    }
    public int deleteItem(String item)
    {
        //return index for quantity list
        int index = inventory.lastIndexOf(item);
        inventory.remove(item);
        return index;
    }
    public void deleteQuantity(int index, int newQuantity)
    {
        String num = Integer.toString(newQuantity);
        if (newQuantity == 0)
            quantity.remove(index);
        else
            quantity.set(index, num);
    }
    public int getIndex(String item)
    {
        return inventory.lastIndexOf(item);
    }
    public String[] getInventory()
    {
        String[] items = new String[inventory.size()];

        for (int x = 0; x < items.length; ++x)
        {
            items[x] = inventory.get(x);
        }
        return items;
    }
    public int[] getQuantities()
    {
        int[] quants = new int[quantity.size()];

        for (int x = 0; x < quants.length; ++x)
        {
            quants[x] = Integer.parseInt(quantity.get(x));
        }
        return quants;
    }
    public void test()
    {
        Object[] array = inventory.toArray();
        Object[] array2 = quantity.toArray();

        for (int x = 0; x < array.length; ++x)
            System.out.println(array[x] + " " + array2[x]);
        System.out.println("---------------");
    }
}
