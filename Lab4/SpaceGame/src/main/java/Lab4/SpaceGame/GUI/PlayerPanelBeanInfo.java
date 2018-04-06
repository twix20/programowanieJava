package Lab4.SpaceGame.GUI;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class PlayerPanelBeanInfo extends SimpleBeanInfo {
	static BeanDescriptor beanDescriptor = null;

	Class<PlayerPanelBean> beanClass;

	public PlayerPanelBeanInfo() {
		beanClass = PlayerPanelBean.class;
	}

	public BeanDescriptor getBeanDescriptor() {
		if (beanDescriptor == null) {
			beanDescriptor = new BeanDescriptor(beanClass);

		}
		return beanDescriptor;
	}

	public PropertyDescriptor[] getPropertyDescriptors() {
		try {
			PropertyDescriptor MechanicPD = new PropertyDescriptor("mechanic", beanClass);
			PropertyDescriptor SteersmanPD = new PropertyDescriptor("steersman", beanClass);

			PropertyDescriptor rv[] = { MechanicPD, SteersmanPD};
			return rv;
		} catch (IntrospectionException e) {
			throw new Error(e.toString());
		}
	}
	
	
}
