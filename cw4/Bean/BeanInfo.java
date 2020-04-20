import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class BeanInfo extends SimpleBeanInfo  {

	public PropertyDescriptor[] getPropertyDescriptors() {
        try {

            return new PropertyDescriptor[] {
            		new PropertyDescriptor("beanFont", Bean.class, "getBeanFont", "setBeanFont"),
                    new PropertyDescriptor("text", Bean.class, "getBeanString", "setBeanString"),
                    new PropertyDescriptor("beanVal", Bean.class, "getBeanValue", "setBeanValue"),
            };
        } catch (IntrospectionException e) {
            e.printStackTrace();
            return null;
        }
}
}
