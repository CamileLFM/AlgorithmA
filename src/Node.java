
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JButton;

public class Node extends JButton implements ActionListener{

    Node parent;
    int col;
    int row;
    int costGoal;
    int costStart;
    int costTotal;
    boolean start;
    boolean goal;
    boolean solid;
    boolean open;
    boolean checked;

    public Node (int col, int row){
        this.col = col;
        this.row = row;

        setBackground(Color.white);
        setForeground(Color.black);
        addActionListener(this);
    }

    public void setAsStart() {
        setBackground(Color.blue);
        setForeground(Color.white);
        setText("<html>Press Enter<br> to<br> Start</html>");
        start = true;
    }

    public void setGoal() {
        setBackground(Color.magenta);
        setForeground(Color.black);
        setText("Goal");
        start = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //setBackground(Color.pink);
        // setText(String.valueOf(col)+ "," + String.valueOf(row) );
    }

    public void setAsSolid() {
        setBackground(Color.black);
        setForeground(Color.black);
        setText("Solid");
        solid = true;
    }

    public void setAsOpen () {
        open = true;
    }

    public void setAsPath () {
        setBackground(Color.GRAY);
        setForeground(Color.black);
    }

    public void setAsChecked () {
        if (!start && !goal) {
            setBackground(Color.CYAN);
            setForeground(Color.black);
        }
        checked = true;
    }
    
}
