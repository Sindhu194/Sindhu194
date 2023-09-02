import javax.swing.JFrame;

public class GameFrame extends JFrame
{
  GameFrame()
  {
        this.setExtendedState(this.getExtendedState()| this.MAXIMIZED_BOTH);
        this.add(new GamePanel());
        this.setTitle("SNAKE");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

  }
}

