package pt.uc.dei.nobugssnackbar;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import pt.uc.dei.nobugssnackbar.exceptions.MundoException;
import pt.uc.dei.nobugssnackbar.goals.Objective;
import pt.uc.dei.nobugssnackbar.suporte.CustomerDefinition;
import pt.uc.dei.nobugssnackbar.suporte.LoadImage;
import pt.uc.dei.nobugssnackbar.suporte.OrderConf;
import pt.uc.dei.nobugssnackbar.suporte.OrderItem;
import pt.uc.dei.nobugssnackbar.suporte.Scripts;

/**
 * 
 * @author Adilson Vahldick 
 */
public class Customer
{

	public static final int BAD = -1;
    public static final int INCOMPLETE = 0;
    public static final int TOTAL = 1;
    public static final int DELIVERED_THANK_YOU = 2;
	
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
	private int dUnfulfilled = 0;

	private boolean thereIsACustomer = false;
	private String place;
	private List<Order> deliveredItems = new ArrayList<>();
	private int amountPaid;
	private int pay;
	private int amountChangeExpected;
	private int amountChangeReceived;
	private Map<MoneyType, Integer> changeReceived = new HashMap<>();
	private String[] limitedChanges;

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
	    		
	    		if (def.getDest().startsWith("counter"))
	    			this.place = "counter";
	    		else
	    			this.place = "table";
	    		
	    		this.pay = def.getPay();
	    		
	    		String lc = def.getLimitedChanges(); 
	    		if (lc == null) {
	    			lc = "20, 10, 5, 2, 1";
	    		}
	    		this.limitedChanges = lc.split(",");
	    		
	    		this.orders  = def.cloneOrders();
	    		
	    		this.curOrder = 0;
	    		
	    	}
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.curX = this.initialX;
		this.curY = this.initialY;
    }

	public String getPlace() {
		return place;
	}
	
	public void nextOrder() {
		this.dUnfulfilled = 0;
		this.fUnfulfilled = 0;

		this.deliveredItems.clear();
		
		this.amountPaid = 0;
		this.amountChangeReceived = 0;
		this.changeReceived.clear();
		
		this.curOrder++;
	}
	
	public int getPos() { return this.index + 1; }
	
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

    	try {
    	
	    	switch (order.getItems().size() - (dUnfulfilled + fUnfulfilled)) {
	    		case 1 :
	    			g.drawImage(baloons.get(idxBaloon), startX, startY, null);
	    			g.drawImage(LoadImage.getInstance().getImage("imagens/$$"+order.findUndeliveredItem(0).getType()+".png"), startX+startFoodX+5, startY+5, 20, 20, null);
	    			break;
	
	    		case 2 :
	    			g.drawImage(baloons.get(idxBaloon+1), startX, startY, null);
					for (int i = 0;i < 2;i++) {
						g.drawImage(LoadImage.getInstance().getImage("imagens/$$"+order.findUndeliveredItem(i).getType()+".png"), startX+startFoodX+6, startY+2+(18*i), 18, 18, null);
					}
	    			break;
	    			
	    		case 3 :
	    			g.drawImage(baloons.get(idxBaloon+2), startX, startY, null);
					if (idxBaloon == 0) 
						startX += 3;
					else
						startX += 10;
	
					for (int i = 0;i < 3;i++) {
						g.drawImage(LoadImage.getInstance().getImage("imagens/$$"+order.findUndeliveredItem(i).getType()+".png"), startX+startFoodX+5, startY+2+(16*i), 16, 16, null);
					}
	    			break;
	    	}
    	} catch (Exception  ex) {
    		// como a GUI � outra thread, ela pode se atrasar para repintar
    		// isso acontece quando a velocidade do programa � muito alta
    		// eu posso repensar como as coisas acontecem... mas vou gastar mais tempo para isso, e como est� j� resolve
    		
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
    	    	return new Order("order", "$$" + oi.getType(), "food", this.index, this.place);
    		}
    	
    	return null;
	}

	public int getFoodDelivered() {
		return this.fUnfulfilled;
	}
	
	public int getDrinksDelivered() {
		return this.dUnfulfilled;
	}
	
	public List<Order> getDeliveredItems() {
		return deliveredItems;
	}
	
	public boolean hasHunger() {
		List<OrderItem> foods = this.orders.get(this.curOrder).getFoods();
		
    	return (this.fUnfulfilled < foods.size());
	}

	public Order askForDrink() {
		List<OrderItem> drinks = this.orders.get(this.curOrder).getDrinks();
		
    	if (this.dUnfulfilled >= drinks.size())
    		return null;

    	for (OrderItem oi:drinks)
    		if (!oi.isDelivered()) {
    	    	return new Order("order", "$$" + oi.getType(), "drink", this.index, this.place);
    		}
    	
    	return null;
	}

	public boolean hasThirsty() {
		List<OrderItem> drinks = this.orders.get(this.curOrder).getDrinks();
		
    	return (this.dUnfulfilled < drinks.size());
	}

	public int deliver(Order item) {
		
		int happy = BAD;
		
		if (item.getCustPlace() == null) {
			
			int ret = this.hasMerit(item);
			if (ret == 2) {
				
				happy = DELIVERED_THANK_YOU;

			} 
	
		} else {
			
			OrderConf order = this.orders.get(this.curOrder);
			
			if (item.getTypeOrder().equals("item") &&
					(item.getFoodOrDrink().equals("drink") && dUnfulfilled < order.getDrinks().size()) ||
					(item.getFoodOrDrink().equals("food") && fUnfulfilled < order.getFoods().size()) ) {

				if (item.getCustPosition()-1 == this.index && item.getCustPlace().equals(this.place))  {
					
					OrderItem d = null;
					List<OrderItem> l = (item.getFoodOrDrink().equals("drink")?order.getDrinks():order.getFoods());
					
					for (OrderItem i:l)
						if (!i.isDelivered() && item.getItem().equals("$$" + i.getType())) {
							d = i;
							break;
						}
					
					if (d != null) {
						
						if (item.getFoodOrDrink().equals("drink"))
							this.dUnfulfilled++;
						else
							this.fUnfulfilled++;
						
						d.setDelivered(true);
						
						happy = (this.fullDelivered()?TOTAL:INCOMPLETE);
					} else {
						throw new MundoException("Tentou entregar um produto que o cliente n�o pediu.");
					}
					
				} else {
					throw new MundoException("Tentou entregar um produto que o cliente n�o pediu.");
				}
				
			}
		}
		
		if (happy != -1) {
			this.deliveredItems.add(item);
		}
		
		return happy;
	}

	private int hasMerit(Order item) {
		int ret = 0;
		
		Map<String, String> params = new HashMap<>();
		params.put("pos", (this.index+1)+"");
		params.put("place", this.place);
		
		try {
		
			Objective obj = NoBugsVisual.search("deliverGifts", params);
			if (obj != null) { // i dont have the merit
				Object value = Scripts.eval(obj.getConf().getValue());
				if (value.toString().length() > 0) 
					if (!value.equals(item.getItem()))
						ret = 1; // i expected a diferent gift
					else
						ret = 2;
						
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return ret;
	}

	private boolean fullDelivered() {
		return (this.dUnfulfilled == getDrinks().size()) && (this.fUnfulfilled == getFoods().size());
	}

	public List<OrderItem> getFoods() {
		if (this.orders == null)
			return new ArrayList<>();

		return this.orders.get(this.curOrder).getFoods();
	}
	
	public List<OrderItem> getDrinks() {
		if (this.orders == null)
			return new ArrayList<>();
		
		return this.orders.get(this.curOrder).getDrinks();
	}
	
	public int askWantHowManyFoods() {
		return getFoods().size();
	}

	public int askWantHowManyDrinks() {
		return getDrinks().size();
	}

	public int askHowMuchInFoodsIfSell() {
		List<OrderItem> foods = getFoods();
		
		int total = 0;
		for (OrderItem f:foods)
			total += f.getPrice();
		
		return total;
	}

	public int askHowMuchInDrinksIfSell() {
		List<OrderItem> drinks = getDrinks();
		
		int total = 0;
		for (OrderItem f:drinks)
			total += f.getPrice();
		
		return total;
	}

	public int askWantHowManyIceCream() {
		List<OrderItem> foods = getFoods();

		int c = 0;
		for (int i=0; i<foods.size(); i++) {
			OrderItem food = foods.get(i);
			if (food.getType().startsWith("icecreamof"))
				c++;
			
		}
		
		return c;
	}

	public Order askForIceCream() {
		List<OrderItem> foods = getFoods();
		
    	if (this.fUnfulfilled >= foods.size())
    		return null;

    	for (OrderItem oi:foods)
    		if (!oi.isDelivered() && oi.getType().startsWith("icecreamof")) {
    	    	return new Order("order", "$$" + oi.getType(), "food", this.index, this.place);
    		}
    	
    	return null;
	}

	public int cashIn(int orderValue) {
		
		int amountPayable = this.askHowMuchInDrinksIfSell() + this.askHowMuchInFoodsIfSell();
		
		// the customer has already paid
		if (this.amountPaid != 0) {
			throw new MundoException("O cliente j� pagou o pedido.");
		}
		
		// Both values are not the same
		if (amountPayable != orderValue) {
			throw new MundoException("A conta do valor a pagar est� incorreta.");
		}
		
		if (this.pay == 0) {
			
			int[] selectMoney = {10, 20};
			if (amountPayable > 10)
				selectMoney = new int[]{20};
			
			int idx = (new Random()).nextInt(selectMoney.length);
			this.amountPaid = selectMoney[idx];
		} else
			this.amountPaid = (this.pay);
		
		this.amountChangeExpected = this.amountPaid - amountPayable;
		
		return this.amountPaid;
	}

	public boolean hasReceivedItem(String foodOrDrink, String itemId) {
		if (foodOrDrink == null || foodOrDrink.equals("food")) {
			
			List<OrderItem> foods = getFoods();
			for (OrderItem item: foods)
				if (item.isDelivered() && item.getType().equals(itemId))
					return true;
			
		}
		
		if (foodOrDrink == null || foodOrDrink.equals("drink")) {
			
			List<OrderItem> drinks = getDrinks();
			
			for (OrderItem item: drinks)
				if (item.isDelivered() && item.getType().equals(itemId))
					return true;
		}
		
		return false;
	}

	public boolean isPaid() {
		return amountPaid > 0;
	}

	public int getAmountPaid() {
		return amountPaid;
	}

	public boolean giveChange(int howMany, MoneyType moneyType) {
		
		if (!this.isPaid()) {
			throw new MundoException("Voc� est� tentando dar ao cliente troco, mas ele nem te pagou ainda.");
		}
		
		if (moneyType == null)
			throw new MundoException("Informe alguma tipo de moeda a ser dada como troco.");
		
		int totalChange = moneyType.getNum() * howMany;
		
		if (this.amountChangeReceived + totalChange > this.amountChangeExpected) {
			throw new MundoException("Voc� est� tentando dar ao cliente mais troco do que o necess�rio.");
		}
		
		this.amountChangeReceived += totalChange;
		Integer qtd = this.changeReceived.get(moneyType);
		
		this.changeReceived.put(moneyType, (qtd == null?0:qtd) + howMany);
		
		return this.amountChangeExpected == this.amountChangeReceived;
	}

	public boolean isChangeReceived() {
		return this.amountChangeExpected == this.amountChangeReceived;
	}

	public boolean isTheBestChange() {
		
		int v = this.amountChangeExpected;
		String[] keys = this.limitedChanges;
		for (String key:keys) {
			
			int keyInt = Integer.parseInt(key.trim());
			int n = (v / keyInt);
			
			
			Integer foundType = changeReceived.get(MoneyType.numToMoneyType(keyInt));
			
			boolean mistake = !(n == 0 || foundType > 0 && foundType == n);
			if (mistake) return false;
			
			v -= n * keyInt;
			
		}
	
		return true;
		
	}

	public boolean receivedChange(int moneyType, int qtd) {
		
		return changeReceived.get(MoneyType.numToMoneyType(moneyType)) == qtd;
	}

	public boolean hasReceivedGift(String typeOfGift) {
		
		for (Order di:this.deliveredItems)
			if (di.getCustPlace() == null && di.getItem().equals(typeOfGift))
				return true;
		
		return false;
	}

	public int totalOfMoneyDelivered() {
		int ret = 0;
		
		for(OrderItem f:getFoods())
			if (f.isDelivered())
				ret += f.getPrice();
		
		for(OrderItem d:getDrinks())
			if (d.isDelivered())
				ret += d.getPrice();
		
		return ret;
	}

}