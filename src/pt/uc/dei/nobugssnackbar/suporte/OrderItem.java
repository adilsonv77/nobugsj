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
	
	@Override
	public String toString() {
		
		return qtdade  + " " + price + " " + type;
	}
	
}
