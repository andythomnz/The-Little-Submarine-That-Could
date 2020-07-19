package display;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.*;

/**
 * Entity Images Class
 * Author @Amy Wilson & Andrew Thompson
 * This library is used to create an Image from each png and also to manipulate the images.
 */
public class EntityImages {
	
	public static BufferedImage rotateImage(BufferedImage image, boolean goingLeft){
		double rad = goingLeft ? Math.PI : 0;
		int height = image.getHeight();
		int width = image.getWidth();
		BufferedImage newImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = newImage.createGraphics();
		g.rotate(rad, width/2, height/2);	
		g.drawImage(image,0,0, null);
		g.dispose();
		if(goingLeft){
			return flipImage(newImage);
		}
		return newImage;
	}
	
	public static BufferedImage flipImage(BufferedImage image){
		AffineTransform transform = AffineTransform.getScaleInstance(1, -1);
		transform.translate(0, -image.getHeight());
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		image = op.filter(image, null);
		return image;
	}
	
	public static BufferedImage getImage(Entity e) {
		
		if(e instanceof Player){
			Player p = (Player) e;
			return rotateImage(Images.PLAYER.img, p.isGoingLeft());
		}
		else if(e instanceof Shark){
			Shark s = (Shark) e;
			return rotateImage(Images.SHARK.img, s.isGoingLeft());
		}
		else if (e instanceof Rocks){
			Rocks r = (Rocks) e;
			return Images.valueOf(r.getImgName()).img;
		}
		else if (e instanceof Pufferfish){
			Pufferfish p = (Pufferfish) e;
			return p.isPuffed() ? Images.valueOf("PUFFERFISH_PUFFED").img : Images.valueOf(e.getClass().getSimpleName().toUpperCase()).img;
		}
		else if (e instanceof Mine){
			Mine m = (Mine) e;
			return m.hasExploded() ? Images.valueOf("EXPLODED").img : Images.valueOf(e.getClass().getSimpleName().toUpperCase()).img;
		}
		else {
			return Images.valueOf(e.getClass().getSimpleName().toUpperCase()).img;
		}
	}
	
	
	public enum Images {
		STARFISH("Starfish.png"), 
		JELLYFISH("Jellyfish.png"),
		TRASH("Trash1.png"),
		PLAYER("Submarine.png"),
		MINE("Mine.png"),
		CHAIN("Chain.png"),
		EXPLODED("explosion.png"),
		PUFFERFISH("Fish.png"),
		PUFFERFISH_PUFFED("FishPuffed.png"),
		SHARK("Shark.png"),
		FUELTOPUP("Fuel.png"),
		SPANNER("Spanner.png"),
		BG_1("Bg1.png"),
		BG_2("Bg2.png"),
		BG_3("Bg3.png"),
		TREASURECHEST("TreasureChest.png"),
		GROUND_1("Ground1.png"),
		GROUND_2("Ground2.png"),
		GROUND_3("Ground3.png"),
		ROCKS_1_LEFT("Rocks-level1-left.png"),
		ROCKS_1_BOTTOM("Rocks-level1-bottom.png"),
		ROCKS_2_LEFT("Rocks-level2-left.png"),
		ROCKS_2_BOTTOM("Rocks-level2-bottom.png"),
		ROCKS_3_LEFT("Rocks-level3-left.png"),
		ROCKS_3_BOTTOM("Rocks-level3-bottom.png");
		
		
		  
		public BufferedImage img;
		  
		Images(String resourceName) {
		    try{ 
		    img = ImageIO.read(getClass().getResource("images/" + resourceName));
		    	}
		    
		    catch (IOException e){ 
		    	throw new Error(e); 
		    	}
		}
		
	}


}

