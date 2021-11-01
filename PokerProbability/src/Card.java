
public class Card {
	private String color = ""; // color can be diamond, heart, club or spade 
	private String value = ""; // value can be As, 2, 3, 4, ...., Jack, Queen, or King 
	
	
	public Card(String color, String value) {
		this.value = value ; 
		this.color = color; 
	}
	
	
	
	
	@Override
	public String toString() {
		return "Card [color=" + color + ", value=" + value + "]";
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
