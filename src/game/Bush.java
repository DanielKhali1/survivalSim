package game;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Bush 
{
	double foodValue;
	
	private Rectangle bushBody = new Rectangle(60, 150);
	
	public Bush(double x, double foodValue)
	{
		this.foodValue = foodValue;
		getBushBody().setLayoutX(x);
		getBushBody().setLayoutY(650-bushBody.getHeight());
		getBushBody().setFill(Color.DARKGREEN);
	}

	public Rectangle getBushBody() {
		return bushBody;
	}

	public void setBushBody(Rectangle bushBody) {
		this.bushBody = bushBody;
	}

}
