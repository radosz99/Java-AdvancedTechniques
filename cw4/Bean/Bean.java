import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

public class Bean extends JPanel implements Externalizable {

  private int beanVal;
  private String beanString; 
  private Font beanFont;
  
  protected PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
  protected VetoableChangeSupport vetoSupport = new VetoableChangeSupport(this);

  protected EventListenerList listenerList = new EventListenerList();

  public Bean() { 
	
	setBorder(BorderFactory.createEtchedBorder());
    beanVal = 0;
    beanString = "Ziarenko ";
    beanFont = new Font("SanSerif", Font.BOLD | Font.ITALIC, 16);
    
  }
  
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setFont(beanFont);
    g.drawString(beanString + " ma obecnie wartosc "+ beanVal, 30, 30);
    
  }
  
  @SuppressWarnings("deprecation")
public void setBeanVal(int newValue) throws PropertyVetoException {
    int oldValue = beanVal;
    beanVal = newValue;
    changeSupport.firePropertyChange("beanVal", new Integer(oldValue), new Integer(newValue));
  }

  public int getBeanVal() {
	 return beanVal;
  }
  

  public void setBeanString(String newString) {
	  beanString = newString;
  }

  public String getBeanString() {
	  return beanString;
  }

  public void setBeanFont(Font font) throws PropertyVetoException {
	  Font oldFont = beanFont;
	  vetoSupport.fireVetoableChange("beanFont", oldFont, font);
	  beanFont = font;
	  changeSupport.firePropertyChange("beanFont", oldFont, font);
  }

  public Font getBeanFont() {
	  return beanFont;
  }
  
  public synchronized void addPropertyChangeListener(PropertyChangeListener l) {
	  changeSupport.addPropertyChangeListener(l);
  }

  public synchronized void removePropertyChangeListener(PropertyChangeListener l) {
	  changeSupport.removePropertyChangeListener(l);
  }
	  
  public synchronized void addVetoableChangeListener(VetoableChangeListener l) {
	  vetoSupport.addVetoableChangeListener(l);
  }

  public synchronized void removeVetoableChangeListener(VetoableChangeListener l) {
	  vetoSupport.removeVetoableChangeListener(l);
  }

  public synchronized void addChangeListener(ChangeListener l) {
	  listenerList.add(ChangeListener.class, l);
  }

  public synchronized void removeChangeListener(ChangeListener l) {
	  listenerList.remove(ChangeListener.class, l);
  }


  public void writeExternal(ObjectOutput out) throws IOException {
	out.writeObject(beanFont);
	out.writeObject(new Dimension(300, 300));
    out.writeInt(beanVal);
    out.writeObject(beanString);
  }

  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    try {
	  setBeanVal(in.readInt());
    } catch (PropertyVetoException pve) {
    	System.out.println("Value vetoed...");
    }
    setBeanString((String) in.readObject());
    setPreferredSize((Dimension) in.readObject());
    setMinimumSize(getPreferredSize());
  }
}
