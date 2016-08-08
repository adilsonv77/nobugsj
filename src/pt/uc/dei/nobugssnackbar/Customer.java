package pt.uc.dei.nobugssnackbar;

import java.awt.Graphics;
import java.awt.Image;

import pt.uc.dei.nobugssnackbar.suporte.CustomerDefinition;
import pt.uc.dei.nobugssnackbar.suporte.LoadImage;

/**
 * 
 * @author Adilson Vahldick 
 */
public class Customer
{

    private String id;
	private int initialX;
	private int initialY;
	private Image image;

	public Customer(CustomerDefinition def)
    {
    	
		this.id = def.getId();
		try {
			buildImage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (def.getInit().startsWith("counter")) {
			switch (Integer.parseInt(def.getInit().substring(7, 8))) {
				case 1: this.initialX = 44; this.initialY = 196;
						break;
				case 2: this.initialX = 44; this.initialY = 276;
						break;
				case 3: this.initialX = 44; this.initialY = 356;
						break;
			}
		}
		
    }

	public void paint(Graphics g) {
		g.drawImage(image, initialX, initialY, null);
		
	}
	
    public String toString()
    {
        return "Customer " + id;
    }

    public Image buildImage() throws Exception
    {
    	if (this.image == null)
    		this.image = LoadImage.getInstance().getImage("imagens/customer"+id+".png");
        return this.image;
    }

}