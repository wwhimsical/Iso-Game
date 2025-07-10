import java.awt.Font;
import java.awt.Graphics2D;

public class GUI {
    GamePanel gp;
    Font arial_40;
    Font pixelFont;

    public GUI(GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 20);
    }

    public void draw(Graphics2D g2d){        
        g2d.drawString("Depth Selected: "+gp.selectedTileDepth, 50, 110);
        g2d.drawString("Tile Type Selected: "+gp.tileM.TileIds.get(gp.selectedTileType).name+", "+gp.selectedTileType, 40, 140);
        g2d.drawImage(null, 0, 0, 0, 0, null); 
        g2d.drawImage(gp.tileM.TileIds.get(gp.selectedTileType).image, 40, 200, gp.tileM.TileIds.get(gp.selectedTileType).width, gp.tileM.TileIds.get(gp.selectedTileType).height, null);
    }
}

