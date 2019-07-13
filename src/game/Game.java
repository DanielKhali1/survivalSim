package game;

import java.util.LinkedList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.animation.*;

public class Game extends Application
{
	Rectangle floor = new Rectangle(1280, 70);
	Pane pane = new Pane();
	Scene scene = new Scene(pane, 1280, 720);
	
	SquishBot bot = new SquishBot();
	Water lake = new Water(Math.random()*(scene.getWidth()-200), 0.4);

	
	LinkedList<Bush> bushes = new LinkedList<Bush>();
	final double BERRYFOODVALUE = 0.4;

	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	public void start(Stage stage) 
	{
		pane.setStyle("-fx-background-color: 'lightblue'");
		
		floor.setFill(Color.GREEN);
		floor.setLayoutX(0);
		floor.setLayoutY(650);
		pane.getChildren().add(floor);
		
		//spawn in lake
		pane.getChildren().add(lake.getWaterBody());		
		
		//spawn 3 bushes
		for(int i = 0; i < 3; i++)
			spawnBush();
		
		//spawn squishbot
		pane.getChildren().add(bot.getBody());
		pane.getChildren().add(bot.getCurrentAction());
		pane.getChildren().add(bot.getNeeds());
		bot.getNeeds().setLayoutY(40);
		
		Timeline timeline = new Timeline();
		KeyFrame key = new KeyFrame( Duration.millis(40), action -> 
		{
			bot.action(pane);
			bot.updateNeeds();
			stickTextToBot();
			
			bot.setOverFood(false);
			
			for(int i = 0; i < bushes.size(); i++)
			{
				if(bot.getBody().getLayoutX() > bushes.get(i).getBushBody().getLayoutX() && bot.getBody().getLayoutX() < bushes.get(i).getBushBody().getLayoutX()+60)
				{
					bot.setOverFood(true);
				}
			}
			
			if(bot.getBody().getLayoutX() > lake.getWaterBody().getLayoutX() && bot.getBody().getLayoutX() < lake.getWaterBody().getLayoutX()+200)
			{
				bot.setOverWater(true);
			}
			else
			{
				bot.setOverWater(false);

			}
		});
		timeline.getKeyFrames().add(key);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
		
		stage.setScene(scene);
		stage.setTitle("Sim");
		stage.show();
	}
	
	public void spawnBush()
	{
		double bushLocation = Math.random()*scene.getWidth();

		while(bushLocation > lake.getWaterBody().getLayoutX() && bushLocation < lake.getWaterBody().getLayoutX()+200)
		{
			System.out.println("was used");
			bushLocation = Math.random()*scene.getWidth();
		}
		
		bushes.add(new Bush(bushLocation, BERRYFOODVALUE));
		pane.getChildren().add(bushes.get(bushes.size()-1).getBushBody());
	}
	
	public void stickTextToBot()
	{
		bot.getCurrentAction().setLayoutX(bot.getBody().getLayoutX()-10);
	}
}