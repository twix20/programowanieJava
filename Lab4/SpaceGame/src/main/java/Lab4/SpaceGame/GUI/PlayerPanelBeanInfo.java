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
			PropertyDescriptor sliderPD = new PropertyDescriptor("sliderEnabled", beanClass);
			PropertyDescriptor sliderNamePD = new PropertyDescriptor("sliderName", beanClass);
			
			PropertyDescriptor spinerPD = new PropertyDescriptor("spinerEnabled", beanClass);
			PropertyDescriptor spinerNamePD = new PropertyDescriptor("spinerName", beanClass);

			PropertyDescriptor rv[] = { sliderPD, sliderNamePD, spinerPD, spinerNamePD};
			return rv;
		} catch (IntrospectionException e) {
			throw new Error(e.toString());
		}
	}
	
	
}
