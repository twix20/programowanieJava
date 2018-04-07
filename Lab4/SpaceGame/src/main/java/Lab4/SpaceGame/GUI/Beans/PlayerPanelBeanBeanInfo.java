package Lab4.SpaceGame.GUI.Beans;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class PlayerPanelBeanBeanInfo extends SimpleBeanInfo {
	static BeanDescriptor beanDescriptor = null;

	Class<PlayerPanelBean> beanClass;

	public PlayerPanelBeanBeanInfo() {
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

			PropertyDescriptor checkBoxPD = new PropertyDescriptor("spinerEnabled", beanClass);
			PropertyDescriptor checkBoxNamePD = new PropertyDescriptor("spinerName", beanClass);

			PropertyDescriptor rv[] = { sliderPD, sliderNamePD, spinerPD, spinerNamePD, checkBoxPD, checkBoxNamePD };
			return rv;
		} catch (IntrospectionException e) {
			throw new Error(e.toString());
		}
	}

}
