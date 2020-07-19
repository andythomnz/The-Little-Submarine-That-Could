package entities;

import java.awt.Point;

import collision.CollisionDetector;
import display.EntityImages;
import game.Game;
import game.LevelThree;
import javafx.geometry.BoundingBox;

public class Player extends ActiveEntity {

	double health = 100;
	double fuel = 100;
	double step;
	CollisionDetector cd;
	Game g;
	boolean goingLeft = false;
	int deaths;

	public Player(Point location, Point movement, BoundingBox bounds, CollisionDetector collisionDetector, Game game) {
		super(location, movement, bounds);
		this.step = movement.getX();
		changeDirection("STOPPED");
		this.cd = collisionDetector;
		this.g = game;
		deaths = 0;
	}

	// Constructor used when loading a saved game
	public Player(Point location, double damage, double fuelLoss, BoundingBox bounds, Point movement, double health,
			double fuel, double step, int deaths) {
		super(location, damage, fuelLoss, bounds, movement);
		this.health = health;
		this.fuel = fuel;
		this.step = step;
		this.deaths = deaths;
	}

	public double getPlayerHealthStatus() {
		return health;
	}

	public double getPlayerStep() {
		return this.step;
	}

	public double getPlayerFuelStatus() {
		return fuel;
	}

	public Boolean isAlive() {
		if (health > 0) {
			return true;
		}
		return false;
	}
	/**
	 * adds to the total number of deaths
	 */
	public void die(){
		deaths++;
	}
	
	public int getDeaths(){
		return deaths;
	}
	
	public Boolean hasFuel() {
		if (fuel > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Method to inflict damage. this method is the one used to change the
	 * health status Positive Damage is when health is added, so a health
	 * top-up. Negative Damage is when health is lost, so damage inflicted
	 * through NPCs.
	 *
	 * @param inflictedDamage
	 * @return healthStatus
	 */
	public double changeHealthStatus(double damage) {
		double temp = health + damage;
		if (temp > 100) {
			health = 100;
		} else if (temp < 0) {
			health = 0;
		} else {
			health += damage;
		}
		return health;
	}

	/**
	 * Method to inflict fuel loss. This method is the one that changes the
	 * fuel. Positive fuelLoss is when fuel is added, so a fuel top-up. Negative
	 * fuelLoss is when fuel is lost through bumping into NPCs
	 *
	 * @param d
	 * @return fuelStatus
	 */
	public double changeFuelStatus(double d) {
		double temp = fuel + d;
		if (temp > 100) {
			fuel = 100;
		} else if (temp < 0) {
			fuel = 0;
		} else {
			fuel += d;
		}
		return fuel;
	}

	/**
	 * resets the player's vitals to peak condition.
	 * used when restarting a level after death.
	 * 
	 * @author maegan
	 */
	public void resetVitals() {
		fuel = 100;
		health = 100;
	}

	/**
	 * checks to see if player has reached the end of a level, and deals with what to do if it has
	 * 
	 * @author maegan
	 */
	public void checkEndOfLevel(){
		boolean endOfLevel = cd.collisionWithEndOfLevel(getBoundingBox());
				if (endOfLevel) {
					g.updateLevel();
					location.setLocation(new Point(50, 50));
				}
	}

	/**
	 * Checks that the player isn't going to collide into anything and if it is, deals with what to do
	 * 
	 * @author maegan
	 * @param potentialNewLocation
	 * @return
	 */
	public Entity checkCollisions(BoundingBox potentialNewLocation) {
		Entity e = cd.collisionWithOtherEntity(potentialNewLocation);
		if (e != null) {
			double damage = e.getDamage();
			changeHealthStatus(damage);
			double fuel = e.getFuelLoss();
			changeFuelStatus(fuel);
			if (e instanceof FuelTopup || e instanceof Spanner) {
				g.getLevel().getLevelEntities().remove(e);
			} 
			//else if (e instanceof Pufferfish) {
				//Pufferfish p = (Pufferfish) e;
				//p.puff();
			//} 
			else if (e instanceof Starfish) {
				Starfish s = (Starfish) e;
				s.stickToSub(true);
			}
			else if (e instanceof Mine) {
				Mine m = (Mine) e;
					m.explode();
			}
			else if (e instanceof TreasureChest) {
				g.winGame();
			}
		}
		return e;
	}

	/**
	 * steps player through all the checks/actions that need to happen every time the timer ticks
	 * @author maegan
	 */
	public void stepPlayer() {
		int x = movement.x + location.x;
		int y = movement.y + location.y;
		BoundingBox potentialNewLocation = new BoundingBox(x, y, EntityImages.getImage(this).getWidth(), EntityImages.getImage(this).getHeight());
		if (bounds.contains(potentialNewLocation)) {
			Entity e = checkCollisions(potentialNewLocation);
			if (e == null || e instanceof ActiveEntity) {
				move();
			}
		}
		if (movement.x != 0 || movement.y != 0) {
			changeFuelStatus(-0.1);
		}
		checkEndOfLevel();
	}

	/**
	 * player move method
	 * @author maegan
	 */
	@Override
	public void move() {
		int x = movement.x + location.x;
		int y = movement.y + location.y;

		location.setLocation(new Point(x, y));
	}

	/**
	 * Changes the movement of the player
	 * @author maegan
	 * @param direction
	 */
	public void changeDirection(String direction) {
		switch (direction) {
		case "UP":
			movement.setLocation(0, -this.step);
			break;
		case "DOWN":
			movement.setLocation(0, this.step);
			break;
		case "LEFT":
			movement.setLocation(-this.step, 0);
			goingLeft = true;
			break;
		case "RIGHT":
			movement.setLocation(this.step, 0);
			goingLeft = false;
			break;
		case "STOPPED":
			movement.setLocation(0, 0);
			break;
		}
	}
	
	/**
	 * returns boolean that determines whether it is going left or not. 
	 * used during image generation for entity
	 * @author maegan
	 * @return
	 */
	public boolean isGoingLeft(){
		return goingLeft;
	}

	public void setGame(Game game) {
		this.g = game;
		this.cd = new CollisionDetector(this.g);
	}
	
	public CollisionDetector getCD() {
		return this.cd;
	}

	@Override
	public String export() {
		String entityAndActiveEntityParts = super.export();

		String playerPart = "," + this.getPlayerHealthStatus() + "," + this.getPlayerFuelStatus() + "," + this.step + "," + this.deaths;

		return entityAndActiveEntityParts + playerPart;
	}

	/**
	 * Returns true when two Player entities are equal, false when not. Used for
	 * testing.
	 */
	@Override
	public boolean equals(Object o) {
		if (!o.getClass().getSimpleName().equals(this.getClass().getSimpleName())) {
			return false; // if objects are not of the same type, return false;
		}

		Player p = (Player) o;
		Boolean sameLocation = this.getLocation().equals(p.getLocation());
		Boolean sameDamage = this.getDamage() == p.getDamage();
		Boolean sameFuelLoss = this.getFuelLoss() == p.getFuelLoss();

		Boolean sameMovement = this.getMovement().equals(p.getMovement());
		Boolean sameBounds = this.getBounds().equals(p.getBounds());

		Boolean sameHealth = this.getPlayerHealthStatus() == p.getPlayerHealthStatus();
		Boolean sameFuel = this.getPlayerFuelStatus() == p.getPlayerFuelStatus();
		Boolean sameStep = this.getPlayerStep() == p.getPlayerStep();

		return sameLocation && sameDamage && sameFuelLoss && sameMovement && sameBounds && sameHealth && sameFuel
				&& sameStep;
	}
}