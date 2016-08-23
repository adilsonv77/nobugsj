package pt.uc.dei.nobugssnackbar;

public enum MoneyType {

	MOEDA1(1), MOEDA2(2), NOTA5(5), NOTA10(10), NOTA20(20);

	private int num;

	MoneyType(int num) { this.num = num; }
	
	public int getNum() {
		return num;
	}
	
	public static MoneyType numToMoneyType(int num) {
		switch (num) {
			case 1: return MOEDA1;
			case 2: return MOEDA2;
			case 5: return NOTA5;
			case 10: return NOTA10;
			default : return NOTA20;
		}
	}
	
	
	
}
