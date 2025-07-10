
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable, KeyListener{

  //dimensions of window
  public static final int GAME_WIDTH = 1000;
  public static final int GAME_HEIGHT = 750;

  public Thread gameThread;
  public Image image;
  public Graphics graphics;

  public MouseHandler mouseH;
  public TileMaker tileM;
  public GUI gui;
  //how big the tiles are
  public final int ogTileSize=50;
  public int tileSize=ogTileSize;

  //transformation for the map
  public double[] iHatMap={1.0, 0.5};
  public double[] jHatMap={-1 ,0.5};

  //transformation for the cursor
  public double[] iHatMouse={0.5 ,-0.5 };
  public double[] jHatMouse={1, 1};

  //map dimentions in tiles
  public int gridX=50;
  public int gridY=50;

  //tile positions
  public int[][][] points=new int[gridX][gridY][2];//0=x 1=y

  //map offset
  public int InitialStartPointX=0;
  public int InitialStartPointY=10*tileSize*0;

  public int startPointX=InitialStartPointX;
  public int startPointY=InitialStartPointY;

  //mouse position
  public int mouseX;
  public int mouseY;

  //tile type selected
  public int selectedTileType=0;
  public int selectedTileDepth=0;

  //tile hovered
  int[] squareX;
  int[] squareY;


  public GamePanel(){
    this.setFocusable(true); //make everything in this class appear on the screen
    this.addKeyListener(this); //start listening for keyboard input
    this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

    mouseH=new MouseHandler(this);
    this.addMouseListener(mouseH);
    this.addMouseMotionListener(mouseH);
    this.addMouseWheelListener(mouseH);

    tileM=new TileMaker(this);

    gui=new GUI(this);

    tileM.makePoints();

    gameThread = new Thread(this);
    gameThread.start();
  }

  public void untransform(int[] vec){
    double[] vec2 = new double[vec.length];

    for (int i = 0; i < vec.length; i++) {
        vec2[i] = (double) vec[i];
    }
    vec[0] = (int)(vec2[0]*iHatMouse[0]+vec2[1]*jHatMouse[0]);
    vec[1] = (int)(vec2[0]*iHatMouse[1]+vec2[1]*jHatMouse[1]);
    
    
  }

  public void paint(Graphics g){

    image = createImage(GAME_WIDTH, GAME_HEIGHT);
    Graphics2D g2D = (Graphics2D)g; 
    g.drawImage(image, 0, 0, this);
    
    tileM.drawGrid(g2D);
    tileM.drawTiles(g2D);
    tileM.drawSelection(g2D);
    gui.draw(g2D);

  }

  

  

  public void run(){
    long lastTime = System.nanoTime();
    double amountOfTicks = 60;
    double ns = 1000000000/amountOfTicks;
    double delta = 0;
    long now;
    long timer = 0;
    int drawCount = 0;

    while(true){ 
      now = System.nanoTime();
      delta = delta + (now-lastTime)/ns;
      timer+=(now-lastTime);
      lastTime = now;
      if(delta >= 1){
        repaint();
        delta--;
        drawCount++;
      }
      if(timer>=1000000000){
        System.out.println("FPS: "+drawCount);
        drawCount = 0;
        timer = 0;
      }
    }
  }

    public void mouseClicked() {      
      if(mouseX>=0&&mouseY>=0&&mouseX<gridX&&mouseY<gridY){
        tileM.tiles[mouseX][mouseY] = tileM.TileIds.get(selectedTileType);
        tileM.tiles[mouseX][mouseY].depth = selectedTileDepth;
      }
    }

  

  public void keyPressed(KeyEvent e){
    int code = e.getKeyCode();
    if(code == KeyEvent.VK_UP){
      selectedTileDepth--;
    }
    if(code == KeyEvent.VK_DOWN){
      selectedTileDepth++;
    }
    if(code == KeyEvent.VK_LEFT){
      if(selectedTileType>0){
        selectedTileType--;
      }else{
        selectedTileType=tileM.TileIds.size()-1;
      }
    }
    if(code == KeyEvent.VK_RIGHT){
      if (selectedTileType<tileM.TileIds.size()-1) {      
        selectedTileType++;
      }else if(selectedTileType==tileM.TileIds.size()-1){
        selectedTileType=0;
      }
    }
    
  }

  public void keyReleased(KeyEvent e){
  }

  public void keyTyped(KeyEvent e){
  }
}