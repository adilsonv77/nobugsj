package pt.uc.dei.nobugssnackbar.suporte;

import java.util.HashMap;
import java.util.Map;

import pt.uc.dei.nobugssnackbar.goals.AskForDrink;
import pt.uc.dei.nobugssnackbar.goals.AskForFood;
import pt.uc.dei.nobugssnackbar.goals.AskForIceCream;
import pt.uc.dei.nobugssnackbar.goals.AskHasHunger;
import pt.uc.dei.nobugssnackbar.goals.AskHasThirsty;
import pt.uc.dei.nobugssnackbar.goals.AskWantHowManyDrinks;
import pt.uc.dei.nobugssnackbar.goals.AskWantHowManyFoods;
import pt.uc.dei.nobugssnackbar.goals.AskWantHowManyIceCream;
import pt.uc.dei.nobugssnackbar.goals.CashIn;
import pt.uc.dei.nobugssnackbar.goals.Conditional;
import pt.uc.dei.nobugssnackbar.goals.CustomDeliver;
import pt.uc.dei.nobugssnackbar.goals.Deliver;
import pt.uc.dei.nobugssnackbar.goals.DeliverGifts;
import pt.uc.dei.nobugssnackbar.goals.GiveSomeChange;
import pt.uc.dei.nobugssnackbar.goals.GiveTheWholeChange;
import pt.uc.dei.nobugssnackbar.goals.GoesToCooler;
import pt.uc.dei.nobugssnackbar.goals.GoesToCounter;
import pt.uc.dei.nobugssnackbar.goals.GoesToDisplay;
import pt.uc.dei.nobugssnackbar.goals.Objective;
import pt.uc.dei.nobugssnackbar.goals.PickUpDrink;
import pt.uc.dei.nobugssnackbar.goals.PickUpFood;
import pt.uc.dei.nobugssnackbar.goals.Talk;

public class ObjectiveConf {

	private static Map<String, Class<? extends Objective>> mapObjectives;
	private int pos;
	private String place;
	private String type;
	private String value;
	private boolean fullText;
	private String text;
	private String condition;
	private String gift;
	private String typeConv;
	private String qtd;
	private String moneyType;
	
	public int getPos() {
		return pos;
	}
	
	public void setPos(int pos) {
		this.pos = pos;
	}
	
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isFullText() {
		return fullText;
	}

	public void setFullText(boolean fullText) {
		this.fullText = fullText;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCondition() {
		return condition;
	}
	
	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getGift() {
		return gift;
	}
	
	public void setGift(String gift) {
		this.gift = gift;
	}
	
	public String getQtd() {
		return qtd;
	}

	public void setQtd(String qtd) {
		this.qtd = qtd;
	}
	
	public String getMoneyType() {
		return moneyType;
	}
	
	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}
	
	@Override
	public String toString() {
		return type + " " + pos + " " + place + " " + fullText + " " + text + " " + value;
	}

	public Objective generate() throws Exception {
		
		return mapObjectives.get(this.type).getConstructor(new Class[]{ObjectiveConf.class}).newInstance(this);

	}
	
	public String getTypeConv() {
		return typeConv;
	}

	public void setTypeConv(String typeConv) {
		this.typeConv = typeConv;
	}

	static {
		mapObjectives = new HashMap<>();
		
		mapObjectives.put("goesTo", GoesToCounter.class);
		mapObjectives.put("goesToDisplay", GoesToDisplay.class);
		mapObjectives.put("goesToCooler", GoesToCooler.class);

		mapObjectives.put("askForFood", AskForFood.class);
		mapObjectives.put("pickUpFood", PickUpFood.class);
		mapObjectives.put("askHasHunger", AskHasHunger.class);
		mapObjectives.put("askWantHowManyFoods", AskWantHowManyFoods.class);
		
		mapObjectives.put("askForDrink", AskForDrink.class);
		mapObjectives.put("pickUpDrink", PickUpDrink.class);
		mapObjectives.put("askHasThirsty", AskHasThirsty.class);
		mapObjectives.put("askWantHowManyDrinks", AskWantHowManyDrinks.class);
		
		mapObjectives.put("askForIceCream", AskForIceCream.class);
		mapObjectives.put("askWantHowManyIceCream", AskWantHowManyIceCream.class);

		mapObjectives.put("deliver", Deliver.class);
		mapObjectives.put("deliverGifts", DeliverGifts.class);
		mapObjectives.put("customDeliver", CustomDeliver.class);
		
		mapObjectives.put("talk", Talk.class);
		
		mapObjectives.put("cashIn", CashIn.class);
		mapObjectives.put("giveTheWholeChange", GiveTheWholeChange.class);
		mapObjectives.put("giveSomeChange", GiveSomeChange.class);
		
		mapObjectives.put("conditional", Conditional.class);
	}

}
