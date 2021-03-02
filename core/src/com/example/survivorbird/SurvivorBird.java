package com.example.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import java.util.Random;
import sun.font.Type1Font;

public class SurvivorBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture enemy1;
	Texture enemy2;
	Texture enemy3;
	float birdX = 0;
	float birdY = 0;
	int gameState = 0;
	float velocity = 0;
	float gravity = 0.3f;
	float enemyVelocity;
	Random random;
	int score = 0;
	int scoredEnemy=0;
	BitmapFont font;
	BitmapFont font2;
	BitmapFont font3;
	int highScore=0;
	BitmapFont font4;
	Circle birdCircle;
	ShapeRenderer shapeRenderer;
	int numberOfEnemies = 4;
	float[] enemyX = new float[numberOfEnemies];
	float[] enemyOffset = new float[numberOfEnemies];
	float[] enemyOffset2 = new float[numberOfEnemies];
	float[] enemyOffset3 = new float[numberOfEnemies];
	float distance = 0;
	Circle[] enemyCircle;
	Circle[] enemyCircle2;
	Circle[] enemyCircle3;


	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");
		enemy1 = new Texture("sprite1.png");
		enemy2 = new Texture("sprite1.png");
		enemy3 = new Texture("sprite1.png");
		distance = Gdx.graphics.getWidth() / 2;
		random = new Random();
		shapeRenderer = new ShapeRenderer();
		birdX = Gdx.graphics.getWidth() /4;
		birdY = Gdx.graphics.getHeight() / 2;
		birdCircle = new Circle();
		enemyCircle = new Circle[numberOfEnemies];
		enemyCircle2 = new Circle[numberOfEnemies];
		enemyCircle3 = new Circle[numberOfEnemies];

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4,4);
		
		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(5,5);

		font3 = new BitmapFont();
		font3.setColor(Color.WHITE);
		font3.getData().setScale(6,6);

		font4 = new BitmapFont();
		font4.setColor(Color.WHITE);
		font4.getData().setScale(4,4);

		for(int i=0; i<numberOfEnemies;i++){

			enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()-200);
			enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()-200);
			enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()-200);

			enemyX[i] = Gdx.graphics.getWidth() - enemy1.getWidth() / 2 + i * distance;

			enemyCircle[i] = new Circle();
			enemyCircle2[i] = new Circle();
			enemyCircle3[i] = new Circle();
		}
	}

	
	@Override
	public void render () {

		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		font3.draw(batch,"Tap to Start!",150,Gdx.graphics.getHeight() / 2.5f );

		Preferences prefs = Gdx.app.getPreferences("game preferences");
		highScore = prefs.getInteger("highscore",0);
		font4.draw(batch,"Highscore : " + highScore ,150,Gdx.graphics.getHeight() / 3.5f);

		if(gameState ==1){
			font3.dispose();
			font4.dispose();
			batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

			if(enemyX[scoredEnemy] < Gdx.graphics.getWidth() /4){
				score++;
				if(scoredEnemy < numberOfEnemies-1){
					scoredEnemy++;
				} else{
					scoredEnemy = 0;
				}
			}

			if(Gdx.input.justTouched()){
				velocity = -7.5f;
			}

			for(int i=0; i<numberOfEnemies;i++){

				if(enemyX[i] < -enemy1.getWidth()){
					enemyX[i] = enemyX[i] + numberOfEnemies * distance;
					enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()-200);
					enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()-200);
					enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()-200);
				}
				else{
					if(score <5 ){
						enemyVelocity = 4;
						enemyX[i] = enemyX[i]- enemyVelocity;
					}
					else if(score >= 5 && score < 10){
						enemyVelocity = 5;
						enemyX[i] = enemyX[i]- enemyVelocity;
					}
					else if(score >= 10 && score < 15){
						enemyVelocity = 6;
						enemyX[i] = enemyX[i]- enemyVelocity;
					}
					else if(score >= 15 && score < 20){
						enemyVelocity = 7;
						enemyX[i] = enemyX[i]- enemyVelocity;
					}
					else if(score >= 20 && score < 25){
						enemyVelocity = 9;
						enemyX[i] = enemyX[i]- enemyVelocity;
					}
					else if(score >= 25 && score< 35){
						enemyVelocity = 12;
						enemyX[i] = enemyX[i]- enemyVelocity;
					}
					else if(score >= 35 && score < 45){
						enemyVelocity = 16;
						enemyX[i] = enemyX[i]- enemyVelocity;
					}
					else if(score >= 45 && score < 55){
						enemyVelocity = 21;
						enemyX[i] = enemyX[i]- enemyVelocity;
					}
					else if(score >= 55){
						enemyVelocity = 26;
						enemyX[i] = enemyX[i]- enemyVelocity;
					}
				}

				batch.draw(enemy1,enemyX[i],Gdx.graphics.getHeight() / 2 + enemyOffset[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				batch.draw(enemy2,enemyX[i],Gdx.graphics.getHeight() / 2 + enemyOffset2[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				batch.draw(enemy3,enemyX[i],Gdx.graphics.getHeight() / 2 + enemyOffset3[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);

				enemyCircle[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight() / 2 + enemyOffset[i] + Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/30 );
				enemyCircle2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight() / 2 + enemyOffset2[i] + Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/30 );
				enemyCircle3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight() / 2 + enemyOffset3[i] + Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/30 );
			}


			if(birdY > 0 && birdY  < Gdx.graphics.getHeight()){
				velocity = velocity + gravity;
				birdY = birdY - velocity;
			}else{
				gameState=2;
			}

			prefs.putInteger("score",score);
			if(score >= highScore){
				highScore = score;
				prefs.putInteger("highscore",highScore);
			}
			else{
				prefs.putInteger("highscore",highScore);
			}
			prefs.flush();


		}


		else if (gameState ==0){
			if(Gdx.input.justTouched()){
				gameState = 1;
			}
		}
		else if(gameState==2){
			batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			font.draw(batch,"Game Over! Tap to play again. Your score is : " + score,100,Gdx.graphics.getHeight() / 2 );

			if(Gdx.input.justTouched()){
				gameState = 1;

				birdY = Gdx.graphics.getHeight() / 2;

				for(int i=0; i<numberOfEnemies;i++){

					enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()-200);
					enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()-200);
					enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()-200);

					enemyX[i] = Gdx.graphics.getWidth() - enemy1.getWidth() / 2 + i * distance;

					enemyCircle[i] = new Circle();
					enemyCircle2[i] = new Circle();
					enemyCircle3[i] = new Circle();
				}
				velocity = 0;
				score=0;
				scoredEnemy=0;
			}
		}

		batch.draw(bird,birdX,birdY,Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
		font.draw(batch,String.valueOf(score),100,200);

		batch.end();

		birdCircle.set(birdX + 5 + Gdx.graphics.getWidth()/30,birdY  + Gdx.graphics.getHeight()/20,(Gdx.graphics.getWidth()/30) - 30 );

		/*
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);
		*/

		for(int i=0;i<numberOfEnemies;i++){
			/*
			shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight() / 2 + enemyOffset[i] + Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/30);
			shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight() / 2 + enemyOffset2[i] + Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/30);
			shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight() / 2 + enemyOffset3[i] + Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/30);
			*/

			if(Intersector.overlaps(birdCircle,enemyCircle[i]) || Intersector.overlaps(birdCircle,enemyCircle2[i]) || Intersector.overlaps(birdCircle,enemyCircle3[i])){
				gameState = 2;
			}
		}
		//shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
	}
}