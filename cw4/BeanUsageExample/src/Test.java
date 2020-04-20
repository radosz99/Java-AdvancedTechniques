import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.JButton;
import say.swing.JFontChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;


public class Test {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Ziarenko");
		JTextField textField = new JTextField();
		Controller control = new Controller();
		Bean bean = new Bean();
		bean.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		bean.addVetoableChangeListener(control);
		bean.addPropertyChangeListener(control);
		JTextField textField_2 = new JTextField();
		JButton submit = new JButton("Submit");
		JButton choose = new JButton("Choose font");
		
		
		choose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			JFontChooser jf = new JFontChooser();
			jf.showDialog(null);
			jf.getSelectedFont();
			try {
				bean.setBeanFont(jf.getSelectedFont());
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
			SwingUtilities.updateComponentTreeUI(frame);
			}
		});
		
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!textField.getText().isEmpty())
				bean.setBeanString(textField.getText());
				try {
					if(!textField_2.getText().isEmpty())
					bean.setBeanVal(Integer.parseInt(textField_2.getText()));
				} catch (NumberFormatException | PropertyVetoException e) {
					e.printStackTrace();
				}
				SwingUtilities.updateComponentTreeUI(frame);
				
			}
		});
		
			frame.setSize(553, 135);
		    frame.getContentPane().setLayout(new BorderLayout());
		    frame.getContentPane().add(submit, BorderLayout.EAST);
		    frame.getContentPane().add(textField, BorderLayout.NORTH);
		    frame.getContentPane().add(textField_2, BorderLayout.SOUTH);
		    frame.getContentPane().add(choose, BorderLayout.WEST);
		    frame.getContentPane().add(bean, BorderLayout.CENTER);
		    frame.setVisible(true);
		  }
	}
