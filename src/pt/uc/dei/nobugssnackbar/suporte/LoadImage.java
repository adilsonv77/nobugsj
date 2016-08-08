package pt.uc.dei.nobugssnackbar.suporte;

import java.awt.Image;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 
 * @author Adilson Vahldick
 *
 */
public class LoadImage
{

    private LoadImage()
    {
    }

    public static LoadImage getInstance()
    {
        if(loadImage == null)
            loadImage = new LoadImage();
        return loadImage;
    }

    public ImageIcon getIcon(String iconName)
    {
        ClassLoader classLoader = getClass().getClassLoader();
        ImageIcon icon = null;
        URL res = classLoader.getResource(iconName);
        if(res != null)
            icon = new ImageIcon(res);
        else
            icon = new ImageIcon(iconName);
        return icon;
    }

    private static LoadImage loadImage;

	public Image getImage(String imageName) throws Exception {
	    	
		ClassLoader classLoader = getClass().getClassLoader();
		Image img = null;
        URL res = classLoader.getResource(imageName);
        if(res != null)
            img = ImageIO.read(res);
        else
            img = ImageIO.read(new File(imageName));
        return img;
	}
}