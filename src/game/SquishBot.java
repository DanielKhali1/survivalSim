package game;
import javafx.scene.shape.Circle;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;

public class SquishBot 
{
	//status
	private double sleep;
	private double water;
	private double food;
	private double health;
	private double shelterHealth;
	
	//emotion
	private double aggression;
	private double hapiness;
	
	//GameObject
	private Circle body = new Circle(40);
	
	boolean shelter = false;
	boolean buildingShelter = false;
	boolean timerSet = false;
	
	boolean right;
	
	boolean decisionMade = false;
	
	private boolean overWater = false;
	private boolean overFood = false;
	private boolean sleeping = false;
	
	boolean drinking = false;
	boolean eating = false;
	
	double targetLocation = 500;
	
	int timer = 0;
	
	
	
	Rectangle shelterBody = new Rectangle(200, 200);
	//Text
	private Text currentAction = new Text("IDLE");
	private Text needs = new Text("sleep: " + sleep + "\nwater: " + water + "\nfood: " + food + "\nhealth: " + health + "\nshelterHealth " + shelterHealth);
	
	public SquishBot()
	{
		setSleep(1);
		setWater(1);
		setFood(1);
		setHealth(1);
		setShelterHealth(1);
		
		setAggression(0);
		setHapiness(0);
		
		getBody().setLayoutY(650-40);
		getBody().setLayoutX(1280/2);
		body.setFill(Color.RED);
		
		currentAction.setLayoutX(getBody().getLayoutX()-10);
		currentAction.setLayoutY(getBody().getLayoutY()-50);
		
		shelterBody.setFill(Color.GRAY);
	}
	
	public void action(Pane pane)
	{
		if(!shelter)
		{
			if(!shelter && !buildingShelter)
			{	
				currentAction.setText("Building Shelter..");
				targetLocation = Math.random() * 1200;
				buildingShelter = true;
			}
			else if(buildingShelter)
			{
				if(Math.abs(body.getLayoutX() - targetLocation) < 4)
				{
					//start a timer
					if(!timerSet)
					{
						timer = 100;
						timerSet = true;
					}
					
					if(timer <= 0)
					{
						shelter = true;
						shelterBody.setLayoutX(body.getLayoutX());
						shelterBody.setLayoutY(650-200);
						pane.getChildren().add(0,shelterBody);
						timerSet = false;
						buildingShelter = false;
						shelterHealth = 1;
					}
				}
				else
				{
					if(body.getLayoutX() < targetLocation)
					{
						moveRight();
					}
					else 
					{
						moveLeft();
					}
				}
			}			
		}
		else if(water < 0.6 || drinking)
		{
			currentAction.setText("Searching For Water");

			if(!decisionMade)
			{
				boolean coinFlip = (int)(Math.random()+0.5) > 0;
				
				if(coinFlip)
				{
					right = false;
				}
				else
				{
					right = true;
				}
				decisionMade = true;
			}
			else
			{
				if(overWater)
				{
					drinking = true;
					currentAction.setText("Drinking");
					drink();
					
					if(water > 1)
					{
						drinking = false;
						decisionMade = false;
					}
					
				}
				else if(right)
				{
					moveRight();
					if(body.getLayoutX() > 1280)
					{
						right = false;
					}
				}
				else
				{
					moveLeft();
					if(body.getLayoutX() < 0)
					{
						right = true;
					}
				}
			}
		}
		
		else if(food < 0.7 || eating)
		{
			currentAction.setText("Searching For Food");

			if(!decisionMade)
			{
				boolean coinFlip = (int)(Math.random()+0.5) > 0;
				
				if(coinFlip)
				{
					right = false;
				}
				else
				{
					right = true;
				}
				decisionMade = true;
			}
			else
			{
				if(isOverFood())
				{
					eating = true;
					currentAction.setText("Eating Berries");
					eat();
					
					if(food > 1)
					{
						eating = false;
						decisionMade = false;
					}
					
				}
				else if(right)
				{
					moveRight();
					if(body.getLayoutX() > 1280)
					{
						right = false;
					}
				}
				else
				{
					moveLeft();
					if(body.getLayoutX() < 0)
					{
						right = true;
					}
				}
			}
		}
		else if(sleep < 0.35 || sleeping)
		{
			if(body.getLayoutX() > shelterBody.getLayoutX()+20 && body.getLayoutX() < shelterBody.getLayoutX()+100)
			{
				currentAction.setText("Z z z");
				sleeping = true;
				sleep += 0.002;
				
				if(sleep > 1)
				{
					sleeping = false;
				}
			}
				
			else if(body.getLayoutX() < shelterBody.getLayoutX()+100)
			{
				moveRight();
			}
			else 
			{
				moveLeft();
			}
		}
		
		else 
		{
			currentAction.setText("Idle");

		}
		
		timer -= 1;
		if(!sleeping)
		{
			sleep -= 0.0001;
			water -= 0.001;
			food -= 0.0004;
			if(shelter)
				shelterHealth -= 0.0002;
		}
		if(shelterHealth < 0)
		{
			shelter = false;
			pane.getChildren().remove(shelterBody);
		}
	}
	
	public void updateNeeds()
	{
		getNeeds().setText("sleep: " + sleep + "\nwater: " + water + "\nfood: " + food + "\nhealth: " + health + "\nshelterHealth " + shelterHealth);

	}
	
	public void moveRight()
	{
		body.setLayoutX(body.getLayoutX()+5);
	}
	
	public void moveLeft()
	{
		body.setLayoutX(body.getLayoutX()-5);
	}
	
	public void searchForFood()
	{
		
	}
	
	public void searchForWater()
	{
		
	}
	
	public void drink()
	{
		water += 0.005;
	}
	
	public void eat()
	{
		food += 0.005;
	}
	
	public void buildShelter()
	{
		
	}
	
	public void sleep()
	{
		sleep += 0.05;
	}
	
	public void attack()
	{
		
	}
	
	public void hide()
	{
		
	}

	public double getSleep() {
		return sleep;
	}

	public void setSleep(double sleep) {
		this.sleep = sleep;
	}

	public double getWater() {
		return water;
	}

	public void setWater(double water) {
		this.water = water;
	}

	public double getFood() {
		return food;
	}

	public void setFood(double food) {
		this.food = food;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public double getAggression() {
		return aggression;
	}

	public void setAggression(double aggression) {
		this.aggression = aggression;
	}

	public double getHapiness() {
		return hapiness;
	}

	public void setHapiness(double hapiness) {
		this.hapiness = hapiness;
	}

	public Circle getBody() {
		return body;
	}

	public void setBody(Circle body) {
		this.body = body;
	}

	public Text getCurrentAction() {
		return currentAction;
	}

	public void setCurrentAction(Text currentAction) {
		this.currentAction = currentAction;
	}

	public double getShelterHealth() {
		return shelterHealth;
	}

	public void setShelterHealth(double shelterHealth) {
		this.shelterHealth = shelterHealth;
	}

	public Text getNeeds() {
		return needs;
	}

	public void setNeeds(Text needs) {
		this.needs = needs;
	}

	public boolean isOverWater() {
		return overWater;
	}

	public void setOverWater(boolean overWater) {
		this.overWater = overWater;
	}

	public boolean isOverFood() {
		return overFood;
	}

	public void setOverFood(boolean overFood) {
		this.overFood = overFood;
	}

}
