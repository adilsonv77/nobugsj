package pt.uc.dei.nobugssnackbar.suporte;

public class OrderItem {

	private int qtdade;
	private int price;
	private String type;
	
	public int getQtdade() {
		return qtdade;
	}
	
	public void setQtdade(int qtdade) {
		this.qtdade = qtdade;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	private boolean delivered;

	public boolean isDelivered() {
		return delivered;
	}

	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}
	
	public OrderItem clone() {
		OrderItem ret = new OrderItem();
		
		ret.setDelivered(delivered);
		ret.setPrice(price);
		ret.setQtdade(qtdade);
		ret.setType(type);
		
		return ret;
	}
	
	@Override
	public String toString() {
		
		return qtdade  + " " + price + " " + type;
	}
	
}
