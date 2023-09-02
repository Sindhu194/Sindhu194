import java.awt.event.*;
import java.awt.*;
import java.util.Random;
import javax.swing.*;
import java.util.Arrays;

public class GamePanel extends JPanel implements ActionListener {
    static final int screen_width = 2560;
    static final int screen_height = 1600;
    static final int unit_size = 30;//size of the objects that are going to appear in gameplay
    static final int game_units = (screen_width * screen_height) / (unit_size);// no of objects appearing in game play
    static final int delay =55;//game delay
    final int x[] = new int[game_units];//holds all the x coordinates of all the body parts of the snake
    final int y[] = new int[game_units];//holds all the y coordinates of all the body parts of the snake
    int bodyParts = 6;
    int applesEaten;//no of apples eaten by the snake
    int appleX;// x coordinate of where the apple is located
    int appleY;// y coordinate of where the apple is located
    char direction = 'r';//direction of where the snake goes-'r' for right,'l' for left,'u' for up and 'd' for down
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(screen_width, screen_height));//setting size of game panel
        this.setBackground(Color.black);//setting the background colour of the game panel
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        random=new Random();
        startGame();

    }

    public void startGame() {
        newApple();//calling the method newApple
        running = true;//since the game started the boolean value is true now
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }


    public void draw(Graphics g) {
       if (running) {

//           for (int i = 0; i < ((screen_height) / (unit_size)); i++)// this for loop is created to draw a grid like line pattern to make the movement of the snake more visible and clearer to the user
//           {
//               g.drawLine(i * unit_size, 0, i * unit_size, screen_height);
//               g.drawLine(0, i * unit_size, screen_width, i * unit_size);
//
//           }
           g.setColor(Color.red);//setting the color of the apple to red
           g.fillOval(appleX, appleY, unit_size, unit_size);//filling the grid space formed with the apple

           for (int i = 0; i < bodyParts; i++) {
               if (i == 0) {
                   g.setColor(Color.green);// setting the color for the snake head
                   g.fillRect(x[i], y[i], unit_size, unit_size);// filling the grid with green to depict a snake head
               } else {
                   g.setColor(new Color(45, 180, 0));//setting a different shade for the body of the snake
                   g.fillRect(x[i], y[i], unit_size, unit_size);// filling the grid with shade to depict a snake
               }
           }
           // to show the score during the game
           g.setColor(Color.blue);
           g.setFont(new Font("Ink Free", Font.BOLD, 75));
           FontMetrics metrics = getFontMetrics(g.getFont());
           g.drawString("SCORE :" + applesEaten, (screen_width - metrics.stringWidth("SCORE :" + applesEaten)) / 2, g.getFont().getSize());
       }
       else
           gameOver(g);
    }

        public void move ()
        {
            for (int i = bodyParts; i > 0; i--) {
                x[i] = x[i - 1];
                y[i] = y[i - 1];

            }
            switch (direction)// a switchcase statement to choose in what direction the snake moves
            {
                case 'u':
                    y[0] = y[0] - unit_size;
                    break;
                case 'd':
                    y[0] = y[0] + unit_size;
                    break;
                case 'l':
                    x[0] = x[0] - unit_size;
                    break;
                case 'r':
                    x[0] = x[0] + unit_size;
                    break;
                default:
                    System.out.println("Entered the wrong character");
                    break;
            }

        }
        public void newApple ()
        {
            appleY = random.nextInt((int) screen_height / unit_size) * unit_size;//randomly places an apple on the y axis within the given measurements of the grid and be placed evenly
            appleX = random.nextInt((int) screen_width / unit_size) * unit_size;//randomly places an apple on the x axis within the given measurements of the grid and be placed evenly
        }
        public void checkApple ()
        {
            if ((x[0] == appleX) && (y[0] == appleY))// checks to see if snake has reached the apple
            {
                bodyParts++;
                applesEaten++;
                newApple();
            }

        }
        public void checkCollisions ()
        {
            for (int i = bodyParts; i > 0; i--)
            {
                if ((x[0] == x[i]) && (y[0] == y[i]))// checks if the head collides with the body
                {
                    running = false;
                }
                if (x[0] < 0)//checks if head touches the left border
                    running = false;
                if (x[0] > screen_width)// checks if head touches the right border
                    running = false;
                if (y[0] < 0)//checks if head touches top border
                    running = false;
                if (y[0] > screen_height)//checks if head touches bottom border
                    running = false;
                if (!running) {
                    timer.stop();//terminates the game if the running boolean value is false
                }
            }

        }
        public void gameOver (Graphics g)
        {//score at the end of the game
            g.setColor(Color.blue);
            g.setFont(new Font("Ink Free", Font.BOLD, 75));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("SCORE :" + applesEaten, (screen_width - metrics1.stringWidth("SCORE :" + applesEaten)) / 2, g.getFont().getSize());
            // game over text
            g.setColor(Color.blue);
            g.setFont(new Font("Ink Free", Font.BOLD, 75));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("GAME OVER!", (screen_width - metrics2.stringWidth("GAME OVER!")) / 2, screen_height / 2);

        }

        @Override
        public void actionPerformed (ActionEvent e)
        {
            if (running)
            {
                move();
                checkApple();
                checkCollisions();
            }
            repaint();

        }
        public class MyKeyAdapter extends KeyAdapter
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                switch (e.getKeyCode())// switchcase statement for more intricate details about the snakes movement
                {
                    case KeyEvent.VK_LEFT:
                        if (direction != 'r')
                        {
                            direction = 'l';
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (direction != 'l')
                        {
                            direction = 'r';
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (direction != 'd')
                        {
                            direction = 'u';
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (direction != 'u')
                        {
                            direction = 'd';
                        }
                        break;
                    case KeyEvent.VK_R:
                        if(!running) {
                            applesEaten = 0;
                            bodyParts = 6;
                            direction = 'r';
                            Arrays.fill(x, 0);
                            Arrays.fill(y, 0);
                            startGame();
                        }
                        break;
                        }
                }
            }
        }

