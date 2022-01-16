public class CRUD 
{
    private Edit edit = new Edit();
    private Inventory inventory = new Inventory();
    private Shipment ship = new Shipment();

    
    public CRUD(Edit edit)
    {
        this.edit = edit;
    }
    public CRUD()
    {}
    public void setEdit(boolean setEditState)
    {
        edit.setEditState(setEditState);
    }
    public boolean edit()
    {
        return edit.getEditState();
    }
    public Inventory getInventory()
    {
        return inventory;
    }
    public Shipment getShipment()
    {
        return ship;
    }
}
