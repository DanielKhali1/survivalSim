package game;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Water 
{
	
	double waterValue;
	
	private Rectangle waterBody = new Rectangle(200, 200);
	
	public Water(double x, double foodValue)
	{
		this.waterValue = foodValue;
		getWaterBody().setLayoutX(x);
		getWaterBody().setLayoutY(650);
		getWaterBody().setFill(Color.DARKBLUE);
	}

	public Rectangle getWaterBody() {
		return waterBody;
	}

	public void setWaterBody(Rectangle bushBody) {
		this.waterBody = bushBody;
	}

}
