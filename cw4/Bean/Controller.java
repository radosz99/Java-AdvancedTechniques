import java.awt.Font;
import java.beans.*;
public class Controller implements VetoableChangeListener,PropertyChangeListener{

	@Override
	public void vetoableChange(PropertyChangeEvent e) throws PropertyVetoException {
		Font newFnt = (Font) e.getNewValue();
		Font val = newFnt;
		if(val.getFontName().equals("Cambria"))
			throw new PropertyVetoException("I don't agree to this font", e);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		if(evt.getOldValue() instanceof Integer)
		{
			System.out.println("Change value to: " + evt.getNewValue());
		}
		else
		{
			System.out.println("Change font to: " + evt.getNewValue());
		}
	}
	

}
