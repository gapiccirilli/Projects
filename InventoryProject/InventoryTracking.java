import java.awt.*;
public class InventoryTracking
{
    public static void main(String[] args)
    {
        MainFrame mainFrame = new MainFrame("Add Inventory");
        int width = 500;
        int height = 350;
        int widthPosition = getScreenDimensions()[0] / 2 - (width / 2);
        int heightPosition = getScreenDimensions()[1] / 4;

        mainFrame.setVisible(true);
        mainFrame.setBounds(widthPosition, heightPosition, width, height);

        

    }
    private static int[] getScreenDimensions()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();
        int[] screenDimensions = {width, height};
        
        return screenDimensions;
    }
}
