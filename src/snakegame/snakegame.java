/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import jaco.mp3.player.MP3Player;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;


import javax.swing.BorderFactory;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import static java.lang.System.gc;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.JFrame;
/**
 *
 * @author Kostakis
 */
public class snakegame extends javax.swing.JFrame {
                 
        //dimiourgei ta songs kai ta sounds fx
        public static final String SONG = "src\\snakegame\\snakesong.mp3";
        MP3Player mp3player = new MP3Player(new File(SONG));
        public static final String SONG2 = "src\\snakegame\\applesound.mp3";
        MP3Player mp3player2 = new MP3Player(new File(SONG2));
        public static final String SONG3 = "src\\snakegame\\snakesound.mp3";
        MP3Player mp3player3 = new MP3Player(new File(SONG3));
        public static final String SONG4 = "src\\snakegame\\snakewall.mp3";
        MP3Player mp3player4 = new MP3Player(new File(SONG4));
        //dimiourgi to jframe p tha trexei to fidaki
        public JFrame window;
        int HIGH_SCORE = 0;
        int HIGH_SCORE1 = 0;
        String username = "";
        
    public class Apple { //dimiourgi to milo me random sintetagmenes
	private int x;
	private int y;
	
	public Apple(snakegame.Snake snake) {
		this.apple_spawn(snake);
	}
	
	public void apple_spawn(snakegame.Snake snake) {
		boolean onSnake = true;
		while(onSnake) {
			onSnake = false;
			
			x = (int)(Math.random() * snakegame.Game.WIDTH - 1);
			y = (int)(Math.random() * snakegame.Game.HEIGHT - 1);
			
			for(Rectangle r : snake.getSnake()){
				if(r.x == x && r.y == y) {
					onSnake = true;
				}
			}
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
    
    public class Game 
        implements KeyListener{
	private snakegame.Snake snake;
	private Apple apple;
	private snakegame.Graphics graphics;

	
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	public static final int DIM = 20;
	
	public Game() {
		window = new JFrame();
		
		snake = new snakegame.Snake();
		
		apple = new Apple(snake);
		
		graphics = new snakegame.Graphics(this);
		
		window.add(graphics);
		
		window.setTitle("Snake");
		window.setSize(WIDTH * DIM + 2, HEIGHT * DIM + DIM + 4);
		window.setVisible(true);
                window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setResizable(false);
                window.setSize(615, 638);
	}
	
	public void start() {  //trexei to fidaki
		graphics.state = "RUNNING";
	}
	
	public void update() {  //otan trwei to milo megalwnei an vrei wall xanei alliws sinexizi
		if(graphics.state == "RUNNING") {
			if(appleCollision()) {
				snake.grow();
				apple.apple_spawn(snake);
			}
			else if(wallCollision() || snakeCollision()) {
				graphics.state = "END";
			}
			else {
				snake.move();
			}
		}
	}
	
	private boolean wallCollision() {  //otan to fidaki xtipaei sto wall
		if(snake.getX() < 0 || snake.getX() >= WIDTH * DIM 
				|| snake.getY() < 0|| snake.getY() >= HEIGHT * DIM) {
                    if (Soundfxbutton.isSelected()){
                        mp3player4.stop();
                    }
                    else {
                        mp3player4.play();
                    }
			return true;
		}
		return false;
	}
	
	private boolean appleCollision() {   //otan to fidaki trwei to milo
		if(snake.getX() == apple.getX() * DIM && snake.getY() == apple.getY() * DIM) {
			return true;
		}
		return false;
	}
	
	private boolean snakeCollision() {  //to megethos apo to fidaki
		for(int i = 1; i < snake.getSnake().size(); i++) {
			if(snake.getX() == snake.getSnake().get(i).x &&
					snake.getY() == snake.getSnake().get(i).y) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void keyTyped(KeyEvent e) {	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int keyCode = e.getKeyCode();
                //elenxei an to soundfx is on kai analoga trexei ta diafora sounds an einai on
		if (Soundfxbutton.isSelected()){                               
                    if(graphics.state == "RUNNING") {
                            if(keyCode == KeyEvent.VK_W && snake.getMove() != "DOWN") {
                                    snake.up();
                                    mp3player3.stop();
                            }

                            if(keyCode == KeyEvent.VK_S && snake.getMove() != "UP") {
                                    snake.down();
                                    mp3player3.stop();
                            }

                            if(keyCode == KeyEvent.VK_A && snake.getMove() != "RIGHT") {
                                    snake.left();
                                    mp3player3.stop();
                            }

                            if(keyCode == KeyEvent.VK_D && snake.getMove() != "LEFT") {
                                    snake.right();
                                    mp3player3.stop();
                            }
                    }
                    else {
                            this.start();
                    }
                }
                else {
                    if(graphics.state == "RUNNING") {
                            if(keyCode == KeyEvent.VK_W && snake.getMove() != "DOWN") {
                                    snake.up();
                                    mp3player3.play();
                            }

                            if(keyCode == KeyEvent.VK_S && snake.getMove() != "UP") {
                                    snake.down();
                                    mp3player3.play();
                            }

                            if(keyCode == KeyEvent.VK_A && snake.getMove() != "RIGHT") {
                                    snake.left();
                                    mp3player3.play();
                            }

                            if(keyCode == KeyEvent.VK_D && snake.getMove() != "LEFT") {
                                    snake.right();
                                    mp3player3.play();
                            }
                    }
                    else {
                            this.start();
                    }
                }
	}

	@Override
	public void keyReleased(KeyEvent e) {	}

	public snakegame.Snake getSnake() {
		return snake;
	}

	public void setSnake(snakegame.Snake snake) {
		this.snake = snake;
	}

	public Apple getApple() {
		return apple;
	}

	public void setApple(Apple apple) {
		this.apple = apple;
	}

	public JFrame getWindow() {
		return window;
	}

	
	
}
    
        
        public class Graphics
        extends JPanel
        implements ActionListener{
	private Timer timer = new Timer(75,this);
	public String state;
	
	private snakegame.Snake snake;
	private Apple apple;
	private Game game;
	
	public Graphics(Game g) {
		timer.start();
		state = "RUNNING";
		
		game = g;
		snake = g.getSnake();
		apple = g.getApple();
		
		//add a keyListner 
		this.addKeyListener(g);
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
	}
	
	public void paintComponent(java.awt.Graphics g) {
		int score= 0;
		Graphics2D gc = (Graphics2D) g;
                //allazei ta xrwmata apo to Game analoga pio colorblind option is on
		if(state == "RUNNING") { 
                    if (Protanopiabutton.isSelected()){
                        gc.setColor(new Color(204,204,0));
                    }
                    else if (Deuteranopiabutton.isSelected()){
                        gc.setColor(new Color(230, 173, 0));
                    }
                    else if (Tritanopiabutton.isSelected()){
                        gc.setColor(new Color(85, 195, 185));
                    }
                    else if (Colorblindoff.isSelected()){
                        gc.setColor(new Color(0,204,0));
                    }
                    else 
                        gc.setColor(new Color(0,204,0));
                    gc.fillRect(0, 0, Game.WIDTH * Game.DIM + 5, Game.HEIGHT * Game.DIM + 5);
                    gc.setColor(Color.black);
                    gc.setFont(new Font("Tahoma",Font.BOLD,20));
                    score = snake.getSnake().size() - 1;
                    gc.drawString("Score: " + score ,Game.WIDTH - 15,Game.HEIGHT - 5);
                    //allazei to xrwma apo to milo analoga pio colorblind option is on
                    if (Protanopiabutton.isSelected()){
                        gc.setColor(new Color(153,153,0));
                    }
                    else if (Deuteranopiabutton.isSelected()){
                        gc.setColor(new Color(153, 102, 0));
                    }
                    else if (Tritanopiabutton.isSelected()){
                        gc.setColor(new Color(255, 51, 51));
                    }
                    else if (Colorblindoff.isSelected()){
                        gc.setColor(new Color(255,0,0));
                    }
                    else 
                        gc.setColor(new Color(255,0,0));
		
                    
                    gc.fillRect(apple.getX() * Game.DIM, apple.getY() * Game.DIM, Game.DIM, Game.DIM);
                    
                    gc.setColor(Color.black);
                    for(Rectangle r : snake.getSnake()) {
                    	gc.fill(r);
                    }
		}
		else {
                    
                    window.setVisible(false);
                    
                    GameOver.setSize(450,350);
                    GameOver.setLocationRelativeTo(null);
                    GameOver.setVisible(true);
                    score = snake.getSnake().size() - 1;
                    //elenxei an easy mode is on kai vgazei antistixo high score
                    if (Easyradiobutton.isSelected()){
                        if(score > HIGH_SCORE1){
                        HIGH_SCORE1 = score;
                        }
                    }
                    else {
                        if(score > HIGH_SCORE){
                        HIGH_SCORE = score;
                        }
                    }                    
                    if (Easyradiobutton.isSelected()){
                        if (username.length() == 1 ) {
                            Highscore.setText("Easy High Score: " + HIGH_SCORE1);
                            Score.setText("Easy Score: " + score);
                            usernametext.setText("     " + username);
                        }
                        else if (username.length() == 2 ) {
                            Highscore.setText("Easy High Score: " + HIGH_SCORE1);
                            Score.setText("Easy Score: " + score);
                            usernametext.setText("    " + username);
                        }
                        else if (username.length() == 3 ) {
                            Highscore.setText("Easy High Score: " + HIGH_SCORE1);
                            Score.setText("Easy Score: " + score);
                            usernametext.setText("   " + username);
                        }
                        else if (username.length() == 4 ) {
                            Highscore.setText("Easy High Score: " + HIGH_SCORE1);
                            Score.setText("Easy Score: " + score);
                            usernametext.setText("  " + username);
                        }
                        else {
                            Highscore.setText("Easy High Score: " + HIGH_SCORE1);
                            Score.setText("Easy Score: " + score);
                            usernametext.setText(username);
                        }
                    }
                    else{
                        if (username.length() == 1){
                            Highscore.setText("Hard High Score: " + HIGH_SCORE);
                            Score.setText("Hard Score: " + score);
                            usernametext.setText("     " + username);
                        }
                        else if (username.length() == 2){
                            Highscore.setText("Hard High Score: " + HIGH_SCORE);
                            Score.setText("Hard Score: " + score);
                            usernametext.setText("    " + username);
                        }
                        else if (username.length() == 3){
                            Highscore.setText("Hard High Score: " + HIGH_SCORE);
                            Score.setText("Hard Score: " + score);
                            usernametext.setText("   " + username);
                        }
                        else if (username.length() == 4){
                            Highscore.setText("Hard High Score: " + HIGH_SCORE);
                            Score.setText("Hard Score: " + score);
                            usernametext.setText("  " + username);
                        }
                        else {
                            Highscore.setText("Hard High Score: " + HIGH_SCORE);
                            Score.setText("Hard Score: " + score);
                            usernametext.setText(username);
                        }
                    }
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		game.update();
	}
	
}
    
        public class Snake {
    
        private ArrayList<Rectangle> snake;
	private int w = Game.WIDTH;
	private int h = Game.HEIGHT;
	private int d = Game.DIM;
	
	private String move; //NOTHING, UP, DOWN, LEFT, RIGHT
	
	public Snake() {  //pou vgenei to fidaki otan trexei to programma
		snake = new ArrayList<>();
		
		Rectangle temp = new Rectangle(Game.DIM, Game.DIM);
		temp.setLocation(Game.WIDTH / 2 * Game.DIM, Game.HEIGHT / 2 * Game.DIM);
		snake.add(temp);
		
		move = "NOTHING";
	}
	
	public void move() {
            //allazei to speed apo to snake analoga pio radiobutton is selected
            if (Hardradiobutton.isSelected()){
		if(move != "NOTHING") {
			Rectangle first = snake.get(0);
			
			Rectangle temp = new Rectangle(Game.DIM, Game.DIM);
			
			if(move == "UP") {
				temp.setLocation(first.x, first.y - Game.DIM);
			}
			else if(move == "DOWN") {
				temp.setLocation(first.x, first.y + Game.DIM);
			}
			else if(move == "LEFT") {
				temp.setLocation(first.x - Game.DIM, first.y);
			}
			else{
				temp.setLocation(first.x + Game.DIM, first.y);
			}
			
			snake.add(0, temp);
			snake.remove(snake.size()-1);
                }
            }
            else if (Easyradiobutton.isSelected()){
                if(move != "NOTHING") {
			Rectangle first = snake.get(0);
			
			Rectangle temp = new Rectangle(Game.DIM, Game.DIM);
			
			if(move == "UP") {
				temp.setLocation(first.x, first.y - Game.DIM/2);
			}
			else if(move == "DOWN") {
				temp.setLocation(first.x, first.y + Game.DIM/2);
			}
			else if(move == "LEFT") {
				temp.setLocation(first.x - Game.DIM/2, first.y);
			}
			else{
				temp.setLocation(first.x + Game.DIM/2, first.y);
			}
			
			snake.add(0, temp);
			snake.remove(snake.size()-1);
                }
      
            }
        }
	
	public void grow() {
		Rectangle first = snake.get(0);
		
		Rectangle temp = new Rectangle(Game.DIM, Game.DIM);
                //an to soundfx einai on pezei to antistixo sound otan kounietai to snake
                if (Soundfxbutton.isSelected()){                          	
                    if(move == "UP") {
                            temp.setLocation(first.x, first.y - Game.DIM);
                            mp3player2.stop();
                    }
                    else if(move == "DOWN") {
                            temp.setLocation(first.x, first.y + Game.DIM);
                            mp3player2.stop();
                    }
                    else if(move == "LEFT") {
                            temp.setLocation(first.x - Game.DIM, first.y);
                            mp3player2.stop();
                    }
                    else{
                            temp.setLocation(first.x + Game.DIM, first.y);
                            mp3player2.stop();
                    }

                    snake.add(0, temp);
                }
                else {
                    if(move == "UP") {
                            temp.setLocation(first.x, first.y - Game.DIM);
                            mp3player2.play();
                    }
                    else if(move == "DOWN") {
                            temp.setLocation(first.x, first.y + Game.DIM);
                            mp3player2.play();
                    }
                    else if(move == "LEFT") {
                            temp.setLocation(first.x - Game.DIM, first.y);
                            mp3player2.play();
                    }
                    else{
                            temp.setLocation(first.x + Game.DIM, first.y);
                            mp3player2.play();
                    }

                    snake.add(0, temp);
                }
        }

	public ArrayList<Rectangle> getSnake() {
		return snake;
	}
	
	public int getX() {
		return snake.get(0).x;
	}
	
	public int getY() {
		return snake.get(0).y ;
	}
	
	public String getMove() {
		return move;
	}
	
	public void up() {
		move = "UP";
	}
	public void down() {
		move = "DOWN";
	}
	public void left() {
		move = "LEFT";
	}
	public void right() {
		move = "RIGHT";
	}
        }
    

    /**
     * Creates new form snakegame
     */
    public snakegame() {
        initComponents();
        Soundonlabel.setVisible(true);
        Soundofflabel.setVisible(false);
        //dinei to default xrwma se ola ta frames
        getContentPane().setBackground(new java.awt.Color(0, 204, 0));
        Settingsmenu.getContentPane().setBackground(new java.awt.Color(0, 204, 0));
        mainmenu.getContentPane().setBackground(new java.awt.Color(0, 204, 0));
        Usernamemenu.getContentPane().setBackground(new java.awt.Color(0, 204, 0));
        GameOver.getContentPane().setBackground(new java.awt.Color(0, 204, 0));
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Settingsmenu = new javax.swing.JFrame();
        Hardradiobutton = new javax.swing.JRadioButton();
        ColorBlindlabel = new javax.swing.JLabel();
        Crossbutton = new javax.swing.JButton();
        Soundonlabel = new javax.swing.JLabel();
        Soundbutton = new javax.swing.JToggleButton();
        Soundofflabel = new javax.swing.JLabel();
        Soundfxbutton = new javax.swing.JToggleButton();
        Soundfxlabel = new javax.swing.JLabel();
        SnakegameText = new javax.swing.JLabel();
        Protanopiabutton = new javax.swing.JRadioButton();
        Level = new javax.swing.JLabel();
        Deuteranopiabutton = new javax.swing.JRadioButton();
        Easyradiobutton = new javax.swing.JRadioButton();
        Tritanopiabutton = new javax.swing.JRadioButton();
        Colorblindoff = new javax.swing.JRadioButton();
        Usernamemenu = new javax.swing.JFrame();
        labelSnake = new javax.swing.JLabel();
        Enterusernametext = new javax.swing.JTextField();
        labelEnterusername = new javax.swing.JLabel();
        mainmenu = new javax.swing.JFrame();
        settingsButton = new javax.swing.JButton();
        labelSnake1 = new javax.swing.JLabel();
        StartGamelabel = new javax.swing.JLabel();
        Changeusernamelabel = new javax.swing.JLabel();
        Exitgamelabel = new javax.swing.JLabel();
        Bgroupcolorblind = new javax.swing.ButtonGroup();
        Bgroup = new javax.swing.ButtonGroup();
        jLabel5 = new javax.swing.JLabel();
        GameOver = new javax.swing.JFrame();
        Gameover = new javax.swing.JLabel();
        Score = new javax.swing.JLabel();
        Highscore = new javax.swing.JLabel();
        Playagainlabel = new javax.swing.JLabel();
        Mainmenulabel = new javax.swing.JLabel();
        usernametext = new javax.swing.JLabel();
        Welcome = new javax.swing.JLabel();
        SnakeGamewelcometitle = new javax.swing.JLabel();
        Continuebutton = new javax.swing.JButton();
        apple = new javax.swing.JLabel();
        snake = new javax.swing.JLabel();

        Settingsmenu.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        Settingsmenu.setTitle("Snake");
        Settingsmenu.setResizable(false);

        Bgroup.add(Hardradiobutton);
        Hardradiobutton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Hardradiobutton.setSelected(true);
        Hardradiobutton.setText("Hard");
        Hardradiobutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        ColorBlindlabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ColorBlindlabel.setText("ColorBlind is OFF");

        Crossbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/snakegame/rsz_1close.png"))); // NOI18N
        Crossbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Crossbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CrossbuttonActionPerformed(evt);
            }
        });

        Soundonlabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Soundonlabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/snakegame/rsz_sound_on.png"))); // NOI18N
        Soundonlabel.setText("Music is ON");

        Soundbutton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Soundbutton.setText("Music");
        Soundbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Soundbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SoundbuttonActionPerformed(evt);
            }
        });

        Soundofflabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Soundofflabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/snakegame/rsz_sound_off.png"))); // NOI18N
        Soundofflabel.setText("Music is OFF");

        Soundfxbutton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Soundfxbutton.setText("Sound FX");
        Soundfxbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Soundfxbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SoundfxbuttonActionPerformed(evt);
            }
        });

        Soundfxlabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Soundfxlabel.setText("Sound FX is ON");

        SnakegameText.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        SnakegameText.setText("Settings");

        Bgroupcolorblind.add(Protanopiabutton);
        Protanopiabutton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Protanopiabutton.setText("Protanopia");
        Protanopiabutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Protanopiabutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProtanopiabuttonActionPerformed(evt);
            }
        });

        Level.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Level.setText("Level :");

        Bgroupcolorblind.add(Deuteranopiabutton);
        Deuteranopiabutton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Deuteranopiabutton.setText("Deuteranopia");
        Deuteranopiabutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Deuteranopiabutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeuteranopiabuttonActionPerformed(evt);
            }
        });

        Bgroup.add(Easyradiobutton);
        Easyradiobutton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Easyradiobutton.setText("Easy");
        Easyradiobutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        Bgroupcolorblind.add(Tritanopiabutton);
        Tritanopiabutton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Tritanopiabutton.setText("Tritanopia");
        Tritanopiabutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Tritanopiabutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TritanopiabuttonActionPerformed(evt);
            }
        });

        Bgroupcolorblind.add(Colorblindoff);
        Colorblindoff.setSelected(true);
        Colorblindoff.setText("Colorblind OFF");
        Colorblindoff.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Colorblindoff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ColorblindoffActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SettingsmenuLayout = new javax.swing.GroupLayout(Settingsmenu.getContentPane());
        Settingsmenu.getContentPane().setLayout(SettingsmenuLayout);
        SettingsmenuLayout.setHorizontalGroup(
            SettingsmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SettingsmenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SettingsmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SettingsmenuLayout.createSequentialGroup()
                        .addGap(0, 23, Short.MAX_VALUE)
                        .addComponent(Level)
                        .addGap(62, 62, 62)
                        .addGroup(SettingsmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(SettingsmenuLayout.createSequentialGroup()
                                .addComponent(SnakegameText)
                                .addGap(109, 109, 109)
                                .addComponent(Crossbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(SettingsmenuLayout.createSequentialGroup()
                                .addComponent(Easyradiobutton)
                                .addGap(27, 27, 27)
                                .addComponent(Hardradiobutton)
                                .addGap(113, 113, 113))))
                    .addGroup(SettingsmenuLayout.createSequentialGroup()
                        .addGroup(SettingsmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Soundbutton)
                            .addGroup(SettingsmenuLayout.createSequentialGroup()
                                .addGroup(SettingsmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Soundfxbutton)
                                    .addGroup(SettingsmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(Colorblindoff)
                                        .addComponent(ColorBlindlabel)))
                                .addGap(35, 35, 35)
                                .addGroup(SettingsmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Protanopiabutton)
                                    .addComponent(Soundfxlabel)
                                    .addGroup(SettingsmenuLayout.createSequentialGroup()
                                        .addComponent(Soundonlabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Soundofflabel))
                                    .addComponent(Deuteranopiabutton)
                                    .addComponent(Tritanopiabutton))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        SettingsmenuLayout.setVerticalGroup(
            SettingsmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SettingsmenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SettingsmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Crossbutton)
                    .addComponent(SnakegameText))
                .addGap(32, 32, 32)
                .addGroup(SettingsmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Level)
                    .addComponent(Easyradiobutton)
                    .addComponent(Hardradiobutton))
                .addGap(18, 18, 18)
                .addGroup(SettingsmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Soundonlabel)
                    .addComponent(Soundbutton)
                    .addComponent(Soundofflabel))
                .addGap(18, 18, 18)
                .addGroup(SettingsmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Soundfxbutton)
                    .addComponent(Soundfxlabel))
                .addGap(18, 18, 18)
                .addGroup(SettingsmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Protanopiabutton)
                    .addComponent(ColorBlindlabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SettingsmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Deuteranopiabutton)
                    .addComponent(Colorblindoff))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Tritanopiabutton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Usernamemenu.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        Usernamemenu.setTitle("Snake ");
        Usernamemenu.setResizable(false);

        labelSnake.setFont(new java.awt.Font("Tahoma", 0, 60)); // NOI18N
        labelSnake.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelSnake.setText("SNAKE ");

        Enterusernametext.setBackground(new java.awt.Color(0, 204, 0));
        Enterusernametext.setFont(new java.awt.Font("Microsoft YaHei", 1, 24)); // NOI18N
        Enterusernametext.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Enterusernametext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnterusernametextActionPerformed(evt);
            }
        });

        labelEnterusername.setFont(new java.awt.Font("Microsoft Tai Le", 1, 21)); // NOI18N
        labelEnterusername.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelEnterusername.setText("Enter Username:");

        javax.swing.GroupLayout UsernamemenuLayout = new javax.swing.GroupLayout(Usernamemenu.getContentPane());
        Usernamemenu.getContentPane().setLayout(UsernamemenuLayout);
        UsernamemenuLayout.setHorizontalGroup(
            UsernamemenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UsernamemenuLayout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addGroup(UsernamemenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelSnake)
                    .addGroup(UsernamemenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelEnterusername, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(UsernamemenuLayout.createSequentialGroup()
                            .addGap(19, 19, 19)
                            .addComponent(Enterusernametext, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(102, Short.MAX_VALUE))
        );
        UsernamemenuLayout.setVerticalGroup(
            UsernamemenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UsernamemenuLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(labelSnake, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(labelEnterusername)
                .addGap(18, 18, 18)
                .addComponent(Enterusernametext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(86, Short.MAX_VALUE))
        );

        mainmenu.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        mainmenu.setTitle("Snake ");
        mainmenu.setResizable(false);

        settingsButton.setBorder(BorderFactory.createEmptyBorder());
        settingsButton.setContentAreaFilled(false);
        settingsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/snakegame/rsz_4dab.png"))); // NOI18N
        settingsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        settingsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                settingsButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                settingsButtonMouseExited(evt);
            }
        });
        settingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsButtonActionPerformed(evt);
            }
        });

        labelSnake1.setFont(new java.awt.Font("Tahoma", 0, 60)); // NOI18N
        labelSnake1.setText("SNAKE");

        StartGamelabel.setFont(new java.awt.Font("Tahoma", 0, 26)); // NOI18N
        StartGamelabel.setText("StartGame");
        StartGamelabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        StartGamelabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                StartGamelabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                StartGamelabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                StartGamelabelMouseExited(evt);
            }
        });

        Changeusernamelabel.setFont(new java.awt.Font("Tahoma", 0, 26)); // NOI18N
        Changeusernamelabel.setText("Change Username");
        Changeusernamelabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Changeusernamelabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ChangeusernamelabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ChangeusernamelabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ChangeusernamelabelMouseExited(evt);
            }
        });

        Exitgamelabel.setFont(new java.awt.Font("Tahoma", 0, 26)); // NOI18N
        Exitgamelabel.setText("Exit Game");
        Exitgamelabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ExitgamelabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ExitgamelabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ExitgamelabelMouseExited(evt);
            }
        });

        javax.swing.GroupLayout mainmenuLayout = new javax.swing.GroupLayout(mainmenu.getContentPane());
        mainmenu.getContentPane().setLayout(mainmenuLayout);
        mainmenuLayout.setHorizontalGroup(
            mainmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainmenuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Exitgamelabel)
                .addGap(55, 55, 55)
                .addComponent(settingsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
            .addGroup(mainmenuLayout.createSequentialGroup()
                .addGroup(mainmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainmenuLayout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addComponent(labelSnake1))
                    .addGroup(mainmenuLayout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(StartGamelabel))
                    .addGroup(mainmenuLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(Changeusernamelabel)))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        mainmenuLayout.setVerticalGroup(
            mainmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainmenuLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(labelSnake1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(StartGamelabel)
                .addGroup(mainmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainmenuLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(settingsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))
                    .addGroup(mainmenuLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(Changeusernamelabel)
                        .addGap(18, 18, 18)
                        .addComponent(Exitgamelabel)
                        .addContainerGap(59, Short.MAX_VALUE))))
        );

        jLabel5.setText("jLabel5");

        GameOver.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        GameOver.setTitle("Snake");
        GameOver.setResizable(false);

        Gameover.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        Gameover.setText("Game Over");

        Score.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Score.setText("Score: ");

        Highscore.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Highscore.setText("High Score: ");

        Playagainlabel.setFont(new java.awt.Font("Tahoma", 0, 26)); // NOI18N
        Playagainlabel.setText("Play Again");
        Playagainlabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Playagainlabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PlayagainlabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PlayagainlabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PlayagainlabelMouseExited(evt);
            }
        });

        Mainmenulabel.setFont(new java.awt.Font("Tahoma", 0, 26)); // NOI18N
        Mainmenulabel.setText("Main Menu");
        Mainmenulabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Mainmenulabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MainmenulabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                MainmenulabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                MainmenulabelMouseExited(evt);
            }
        });

        usernametext.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout GameOverLayout = new javax.swing.GroupLayout(GameOver.getContentPane());
        GameOver.getContentPane().setLayout(GameOverLayout);
        GameOverLayout.setHorizontalGroup(
            GameOverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GameOverLayout.createSequentialGroup()
                .addGroup(GameOverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GameOverLayout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addGroup(GameOverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(GameOverLayout.createSequentialGroup()
                                .addComponent(Score)
                                .addGap(38, 38, 38)
                                .addComponent(Highscore))
                            .addGroup(GameOverLayout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addComponent(Mainmenulabel))))
                    .addGroup(GameOverLayout.createSequentialGroup()
                        .addGap(182, 182, 182)
                        .addComponent(usernametext))
                    .addGroup(GameOverLayout.createSequentialGroup()
                        .addGap(153, 153, 153)
                        .addComponent(Playagainlabel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, GameOverLayout.createSequentialGroup()
                .addGap(0, 91, Short.MAX_VALUE)
                .addComponent(Gameover)
                .addGap(91, 91, 91))
        );
        GameOverLayout.setVerticalGroup(
            GameOverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GameOverLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(Gameover)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernametext)
                .addGap(32, 32, 32)
                .addGroup(GameOverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Score)
                    .addComponent(Highscore))
                .addGap(27, 27, 27)
                .addComponent(Playagainlabel)
                .addGap(18, 18, 18)
                .addComponent(Mainmenulabel)
                .addContainerGap(71, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Snake ");
        setBackground(new java.awt.Color(0, 240, 0));
        setResizable(false);

        Welcome.setFont(new java.awt.Font("Tahoma", 0, 26)); // NOI18N
        Welcome.setText("Welcome to ");

        SnakeGamewelcometitle.setFont(new java.awt.Font("Tahoma", 1, 40)); // NOI18N
        SnakeGamewelcometitle.setText("Snake Game");

        Continuebutton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Continuebutton.setText("Continue");
        Continuebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ContinuebuttonActionPerformed(evt);
            }
        });

        apple.setIcon(new javax.swing.ImageIcon(getClass().getResource("/snakegame/rsz_121_apple-512.png"))); // NOI18N

        snake.setIcon(new javax.swing.ImageIcon(getClass().getResource("/snakegame/rsz_snake.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(snake)
                .addGap(41, 41, 41)
                .addComponent(Welcome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(apple)
                .addGap(26, 26, 26))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(SnakeGamewelcometitle)
                .addGap(67, 67, 67))
            .addGroup(layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(Continuebutton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(Welcome)
                        .addGap(39, 39, 39))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(apple)
                            .addComponent(snake))
                        .addGap(27, 27, 27)))
                .addComponent(SnakeGamewelcometitle)
                .addGap(31, 31, 31)
                .addComponent(Continuebutton)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void CrossbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CrossbuttonActionPerformed
        //kleinei to settingsmenu kai anigi to mainmenu
        Settingsmenu.dispose();
        mainmenu.setSize(400, 340);
        mainmenu.setLocationRelativeTo(null);
        mainmenu.setVisible(true);
        settingsButton.setBorder(null);
    }//GEN-LAST:event_CrossbuttonActionPerformed

    private void SoundbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SoundbuttonActionPerformed
        //kleinei-anigi to background sound
        if (Soundbutton.isSelected()){
            Soundonlabel.setVisible(false);
            Soundofflabel.setVisible(true);
            mp3player.stop();
        }
        else {
            Soundonlabel.setVisible(true);
            Soundofflabel.setVisible(false);
            mp3player.play();
            mp3player.setRepeat(true);
        }
    }//GEN-LAST:event_SoundbuttonActionPerformed

    private void SoundfxbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SoundfxbuttonActionPerformed
        //kleinei-anigi to soundfx gia to snake
        if (Soundfxbutton.isSelected()){
            Soundfxlabel.setText("Sound FX is OFF");
        }
        else {
            Soundfxlabel.setText("Sound FX is ON");
        }
    }//GEN-LAST:event_SoundfxbuttonActionPerformed

    private void ProtanopiabuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProtanopiabuttonActionPerformed
        //allazei ta xrwmata gia protanopia colorblind
        if (Protanopiabutton.isSelected()){
            Settingsmenu.getContentPane().setBackground(new java.awt.Color(204, 204, 0));
            mainmenu.getContentPane().setBackground(new java.awt.Color(204, 204, 0));
            Usernamemenu.getContentPane().setBackground(new java.awt.Color(204, 204, 0));
            Enterusernametext.setBackground(new java.awt.Color(204, 204, 0));
            GameOver.getContentPane().setBackground(new java.awt.Color(204, 204, 0));
        }
        if (Protanopiabutton.isSelected()){
            ColorBlindlabel.setText("ColorBlind is ON");
        }
    }//GEN-LAST:event_ProtanopiabuttonActionPerformed

    private void DeuteranopiabuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeuteranopiabuttonActionPerformed
        //allazei ta xrwmata gia deuteranopia colorblind
        if (Deuteranopiabutton.isSelected()){
            Settingsmenu.getContentPane().setBackground(new java.awt.Color(230, 173, 0));
            mainmenu.getContentPane().setBackground(new java.awt.Color(230, 173, 0));
            Usernamemenu.getContentPane().setBackground(new java.awt.Color(230, 173, 0));
            Enterusernametext.setBackground(new java.awt.Color(230, 173, 0));
            GameOver.getContentPane().setBackground(new java.awt.Color(230, 170, 0));
        }
        ColorBlindlabel.setText("ColorBlind is ON");
    }//GEN-LAST:event_DeuteranopiabuttonActionPerformed

    private void TritanopiabuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TritanopiabuttonActionPerformed
        //allazei ta xrwmata gia tritanopia colorblind
        if (Tritanopiabutton.isSelected()){
            Settingsmenu.getContentPane().setBackground(new java.awt.Color(85, 195, 185));
            mainmenu.getContentPane().setBackground(new java.awt.Color(85, 195, 185));
            Usernamemenu.getContentPane().setBackground(new java.awt.Color(85, 195, 185));
            Enterusernametext.setBackground(new java.awt.Color(85, 195, 185));
            GameOver.getContentPane().setBackground(new java.awt.Color(85, 195, 185));
        }
        ColorBlindlabel.setText("ColorBlind is ON");
    }//GEN-LAST:event_TritanopiabuttonActionPerformed

    private void EnterusernametextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnterusernametextActionPerformed
        //klinei to startgame kai anigi to mainmenu
        Usernamemenu.dispose();
        username = Enterusernametext.getText();
        mainmenu.setSize(400, 340);
        mainmenu.setLocationRelativeTo(null);
        mainmenu.setVisible(true);
    }//GEN-LAST:event_EnterusernametextActionPerformed

    private void settingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsButtonActionPerformed
        // kleinei to mainmenu kai anigi to settingsmenu
        mainmenu.dispose();
        Settingsmenu.setSize(400, 340);
        Settingsmenu.setLocationRelativeTo(null);
        Settingsmenu.setVisible(true);
    }//GEN-LAST:event_settingsButtonActionPerformed

    private void ColorblindoffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ColorblindoffActionPerformed
        //kleinei-anigi to colorblind
        if (Colorblindoff.isSelected()){
            Settingsmenu.getContentPane().setBackground(new java.awt.Color(0, 204, 0));
            mainmenu.getContentPane().setBackground(new java.awt.Color(0, 204, 0));
            Usernamemenu.getContentPane().setBackground(new java.awt.Color(0, 204, 0));
            GameOver.getContentPane().setBackground(new java.awt.Color(0, 204, 0));
        }
        if (Colorblindoff.isSelected()){
            ColorBlindlabel.setText("ColorBlind is OFF");
        }
    }//GEN-LAST:event_ColorblindoffActionPerformed

    private void ContinuebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ContinuebuttonActionPerformed
        //kleinei to arxiko jframe kai anigi to startgame
        dispose();
        Usernamemenu.setSize(400,300);
        Usernamemenu.setLocationRelativeTo(null);
        Usernamemenu.setVisible(true);
        mp3player.play();
        mp3player.setRepeat(true);
    }//GEN-LAST:event_ContinuebuttonActionPerformed

    private void StartGamelabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StartGamelabelMouseClicked
        //kleinei to mainmenu kai trexei to game
        mainmenu.dispose();
        Game game = new Game();
    }//GEN-LAST:event_StartGamelabelMouseClicked

    private void ChangeusernamelabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ChangeusernamelabelMouseClicked
        //kleinei to mainmenu kai anigi to startgame
        mainmenu.dispose();
        Enterusernametext.setText("");
        Usernamemenu.setSize(400, 300);
        Usernamemenu.setLocationRelativeTo(null);
        Usernamemenu.setVisible(true);
    }//GEN-LAST:event_ChangeusernamelabelMouseClicked

    private void PlayagainlabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlayagainlabelMouseClicked
        //kleinei to gameover jframe kai ksanatrexei to game
        GameOver.dispose();
        Game game = new Game();
        
    }//GEN-LAST:event_PlayagainlabelMouseClicked

    private void StartGamelabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StartGamelabelMouseEntered
        //allazei tin gramatoseira sto mouseEntered
        StartGamelabel.setFont(new java.awt.Font("Tahoma", 0, 27));
    }//GEN-LAST:event_StartGamelabelMouseEntered

    private void StartGamelabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StartGamelabelMouseExited
        //allazei tin gramatoseira sto mouseExited
        StartGamelabel.setFont(new java.awt.Font("Tahoma", 0, 26));
    }//GEN-LAST:event_StartGamelabelMouseExited

    private void ChangeusernamelabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ChangeusernamelabelMouseEntered
        //allazei tin gramatoseira sto mouseEntered
        Changeusernamelabel.setFont(new java.awt.Font("Tahoma", 0, 27));
    }//GEN-LAST:event_ChangeusernamelabelMouseEntered

    private void ChangeusernamelabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ChangeusernamelabelMouseExited
        //allazei tin gramatoseira sto mouseExited
        Changeusernamelabel.setFont(new java.awt.Font("Tahoma", 0, 26));
    }//GEN-LAST:event_ChangeusernamelabelMouseExited

    private void settingsButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsButtonMouseEntered
        //allazei to size tou button sto mouseEntered
        settingsButton.setSize(65, 45);
    }//GEN-LAST:event_settingsButtonMouseEntered

    private void settingsButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsButtonMouseExited
        //allazei to size tou button sto mouseExited
        settingsButton.setSize(58,40);
    }//GEN-LAST:event_settingsButtonMouseExited

    private void MainmenulabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MainmenulabelMouseClicked
        //kleinei to gameover jframe k anigi to mainmenu
        GameOver.dispose();
        mainmenu.setSize(400, 340);
        mainmenu.setLocationRelativeTo(null);
        mainmenu.setVisible(true);
    }//GEN-LAST:event_MainmenulabelMouseClicked

    private void PlayagainlabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlayagainlabelMouseEntered
        //allazei tin gramatoseira sto mouseEntered
        Playagainlabel.setFont(new java.awt.Font("Tahoma", 0, 27));
    }//GEN-LAST:event_PlayagainlabelMouseEntered

    private void PlayagainlabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlayagainlabelMouseExited
        //allazei tin gramatoseira sto mouseExited
        Playagainlabel.setFont(new java.awt.Font("Tahoma", 0, 26));
    }//GEN-LAST:event_PlayagainlabelMouseExited

    private void MainmenulabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MainmenulabelMouseEntered
        //allazei tin gramatoseira sto mouseEntered
        Mainmenulabel.setFont(new java.awt.Font("Tahoma", 0, 27));
    }//GEN-LAST:event_MainmenulabelMouseEntered

    private void MainmenulabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MainmenulabelMouseExited
        //allazei tin gramatoseira sto mouseExited
        Mainmenulabel.setFont(new java.awt.Font("Tahoma", 0, 26));
    }//GEN-LAST:event_MainmenulabelMouseExited

    private void ExitgamelabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExitgamelabelMouseClicked
        //kleinei to programma
        System.exit(0);
    }//GEN-LAST:event_ExitgamelabelMouseClicked

    private void ExitgamelabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExitgamelabelMouseEntered
        //allazei tin gramatoseira sto mouseEntered
        Exitgamelabel.setFont(new java.awt.Font("Tahoma", 0, 27));
    }//GEN-LAST:event_ExitgamelabelMouseEntered

    private void ExitgamelabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExitgamelabelMouseExited
        //allazei tin gramatoseira sto mouseExited
        Exitgamelabel.setFont(new java.awt.Font("Tahoma", 0, 26));
    }//GEN-LAST:event_ExitgamelabelMouseExited

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(snakegame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(snakegame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(snakegame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(snakegame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new snakegame().setVisible(true);
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.ButtonGroup Bgroup;
    public javax.swing.ButtonGroup Bgroupcolorblind;
    public javax.swing.JLabel Changeusernamelabel;
    public javax.swing.JLabel ColorBlindlabel;
    public javax.swing.JRadioButton Colorblindoff;
    public javax.swing.JButton Continuebutton;
    public javax.swing.JButton Crossbutton;
    public javax.swing.JRadioButton Deuteranopiabutton;
    public javax.swing.JRadioButton Easyradiobutton;
    public javax.swing.JTextField Enterusernametext;
    public javax.swing.JLabel Exitgamelabel;
    public javax.swing.JFrame GameOver;
    public javax.swing.JLabel Gameover;
    public javax.swing.JRadioButton Hardradiobutton;
    public javax.swing.JLabel Highscore;
    public javax.swing.JLabel Level;
    public javax.swing.JLabel Mainmenulabel;
    public javax.swing.JLabel Playagainlabel;
    public javax.swing.JRadioButton Protanopiabutton;
    public javax.swing.JLabel Score;
    public javax.swing.JFrame Settingsmenu;
    public javax.swing.JLabel SnakeGamewelcometitle;
    public javax.swing.JLabel SnakegameText;
    public javax.swing.JToggleButton Soundbutton;
    public javax.swing.JToggleButton Soundfxbutton;
    public javax.swing.JLabel Soundfxlabel;
    public javax.swing.JLabel Soundofflabel;
    public javax.swing.JLabel Soundonlabel;
    public javax.swing.JLabel StartGamelabel;
    public javax.swing.JRadioButton Tritanopiabutton;
    public javax.swing.JFrame Usernamemenu;
    public javax.swing.JLabel Welcome;
    public javax.swing.JLabel apple;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel labelEnterusername;
    public javax.swing.JLabel labelSnake;
    public javax.swing.JLabel labelSnake1;
    public javax.swing.JFrame mainmenu;
    public javax.swing.JButton settingsButton;
    public javax.swing.JLabel snake;
    public javax.swing.JLabel usernametext;
    // End of variables declaration//GEN-END:variables
}
