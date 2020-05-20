import javax.swing.JFrame;
import javax.swing.JLabel;

public class Hello extends JFrame {

    private static final long serialVersionUID = 4968624166243565348L;

    private JLabel label = new JLabel("Hello JAVAWS!");

    public Hello() {
        super("JNLP Example");
        this.setSize(350, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
    }

    public void addButtons() {
        label.setSize(200, 30);
        label.setLocation(80, 50);
        this.getContentPane().add(label);
    }

    public static void main(String[] args) {
        Hello exp = new Hello();
        exp.addButtons();
        exp.setVisible(true);
    }
}
