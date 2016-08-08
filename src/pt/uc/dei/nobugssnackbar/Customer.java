package pt.uc.dei.nobugssnackbar;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import pt.uc.dei.nobugssnackbar.suporte.CustomerDefinition;
import pt.uc.dei.nobugssnackbar.suporte.LoadImage;
import pt.uc.dei.nobugssnackbar.suporte.Order;

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
	private List<Order> orders;
	private int curOrder;
	private int index;

	public Customer(CustomerDefinition def, int index)
    {
		try {
			this.index = index;
	    	if (def == null) {
	    		this.image = LoadImage.getInstance().getImage("imagens/banco.png");
	    		defineInitialXY(index+1);
	    	} else {
	    		
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

	private void defineInitialXY(int idxC) {
		switch (idxC) {
			case 1: this.initialX = 44; this.initialY = 196;
					break;
			case 2: this.initialX = 44; this.initialY = 276;
					break;
			case 3: this.initialX = 44; this.initialY = 356;
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
    	
    	Order order = orders.get(curOrder);
    	
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
    	
    	
/*
 * Baloon.draw = function(ctx, x, y, orders, left) {
		switch (orders.length) {
			case 1: {
				ctx.drawImage(PreloadImgs.get(baloon1), startX, startY);
				ctx.drawImage(orders[0], startX+startFoodX+5, startY+5, 20, 20);
				break;
			}
			case 2: {
				ctx.drawImage(PreloadImgs.get(baloon2), startX, startY);
				for (var i = 0;i < orders.length;i++) {
					ctx.drawImage(orders[i], startX+startFoodX+6, startY+2+(18*i), 18, 18);
				}
				break;
			}
			case 3: {
				ctx.drawImage(PreloadImgs.get(baloon3), startX, startY);
				if (left) 
					startX += 3;
				else
					startX += 10;

				for (var i = 0;i < orders.length;i++) {
					ctx.drawImage(orders[i], startX+startFoodX+5, startY+2+(16*i), 16, 16);
				}
				break;
			}
		}
	}
};		// TODO Auto-generated method stub
 */
		
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

}