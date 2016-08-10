package pt.uc.dei.nobugssnackbar;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import pt.uc.dei.nobugssnackbar.suporte.CustomerDefinition;
import pt.uc.dei.nobugssnackbar.suporte.LoadImage;
import pt.uc.dei.nobugssnackbar.suporte.OrderConf;
import pt.uc.dei.nobugssnackbar.suporte.OrderItem;

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
	private int curX;
	private int curY;
	private List<OrderConf> orders;
	private int curOrder;
	private int index;
	private int fUnfulfilled = 0;
	private boolean thereIsACustomer = false;

	public Customer(CustomerDefinition def, int index)
    {
		try {
			this.index = index;
	    	if (def == null) {
	    		this.image = LoadImage.getInstance().getImage("imagens/banco.png");
	    		defineInitialXY(index+1);
	    	} else {
	    		this.thereIsACustomer = true;
	    		this.id = def.getId();
    			buildImage();
	    		if (def.getInit().startsWith("counter")) {
	    			defineInitialXY(Integer.parseInt(def.getInit().substring(7, 8)));
	    		}
	    		this.orders  = def.getOrders();
	    		
	    		this.curOrder = 0;
	    		
	    	}
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.curX = this.initialX;
		this.curY = this.initialY;
    }
	
	public boolean isThereIsACustomer() {
		return thereIsACustomer;
	}

	private void defineInitialXY(int idxC) {
		switch (idxC) {
			case 1: this.initialX = 44; this.initialY = 200;
					break;
			case 2: this.initialX = 44; this.initialY = 280;
					break;
			case 3: this.initialX = 44; this.initialY = 360;
					break;
		}
	}

	public void paint(Graphics g) throws Exception {
		
		g.drawImage(image, curX, curY, null);
		drawOrders(g);
		
	}
	
    private void drawOrders(Graphics g) throws Exception {
    	
    	if (orders == null)
    		return;
    	
    	OrderConf order = orders.get(curOrder);
    	
    	int idxBaloon, startX, startFoodX = 0;
    	if (this.index % 2 == 0) {
    		
    		idxBaloon = 0;
    		startX = curX - 32;
    		
    	} else {
    		
    		idxBaloon = 3;
    		startX = curX + 32;
    		startFoodX = 2;
    				
    	}
    	
    	int startY = curY - 30;
    	
    	switch (order.getItems().size()) {
    		case 1 :
    			g.drawImage(baloons.get(idxBaloon), startX, startY, null);
    			g.drawImage(LoadImage.getInstance().getImage("imagens/$$"+order.getItems().get(0).getType()+".png"), startX+startFoodX+5, startY+5, 20, 20, null);
    			break;

    		case 2 :
    			g.drawImage(baloons.get(idxBaloon+1), startX, startY, null);
				for (int i = 0;i < 2;i++) {
					g.drawImage(LoadImage.getInstance().getImage("imagens/$$"+order.getItems().get(i).getType()+".png"), startX+startFoodX+6, startY+2+(18*i), 18, 18, null);
				}
    			break;
    			
    		case 3 :
    			g.drawImage(baloons.get(idxBaloon+2), startX, startY, null);
				if (idxBaloon == 0) 
					startX += 3;
				else
					startX += 10;

				for (int i = 0;i < 3;i++) {
					g.drawImage(LoadImage.getInstance().getImage("imagens/$$"+order.getItems().get(i).getType()+".png"), startX+startFoodX+5, startY+2+(16*i), 16, 16, null);
				}
    			break;
    	}
    	
    	
		
	}

	public String toString()
    {
        return "Customer " + id;
    }

	private static List<Image> baloons; 
	
    public Image buildImage() throws Exception
    {
    	if (this.image == null) 
    		this.image = LoadImage.getInstance().getImage("imagens/customer"+id+".png");
    	
    	
    	if (baloons == null) {
    		baloons = new ArrayList<>();
    		
    		baloons.add( LoadImage.getInstance().getImage("imagens/baloon_1.png") );
    		baloons.add( LoadImage.getInstance().getImage("imagens/baloon_2.png") );
    		baloons.add( LoadImage.getInstance().getImage("imagens/baloon_3.png") );

    		baloons.add( LoadImage.getInstance().getImage("imagens/baloonr_1.png") );
    		baloons.add( LoadImage.getInstance().getImage("imagens/baloonr_2.png") );
    		baloons.add( LoadImage.getInstance().getImage("imagens/baloonr_3.png") );
    	}
    	
        return this.image;
    }

	public Order askForFood() {
		
		List<OrderItem> foods = this.orders.get(this.curOrder).getFoods();
		
    	if (this.fUnfulfilled >= foods.size())
    		return null;

    	for (OrderItem oi:foods)
    		if (!oi.isDelivered()) {
    	    	return new Order("order", "$$" + oi.getType(), "food", this.index, "counter");
    		}
    	
    	return null;
	}

	public boolean hasHunger() {
		List<OrderItem> foods = this.orders.get(this.curOrder).getFoods();
		
    	return (this.fUnfulfilled < foods.size());
	}

}