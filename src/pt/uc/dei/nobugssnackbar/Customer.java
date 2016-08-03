package pt.uc.dei.nobugssnackbar;

import javax.swing.ImageIcon;

import pt.uc.dei.nobugssnackbar.suporte.LoadImage;

/**
 * 
 * @author Adilson Vahldick 
 */
public class Customer
{

    private String id;

	public Customer(String id)
    {
    	this.id = id;
    }

    public String toString()
    {
        return "Customer " + id;
    }

    public ImageIcon buildImage()
    {
        return LoadImage.getInstance().getIcon("imagens/customer"+id+".png");
    }

}