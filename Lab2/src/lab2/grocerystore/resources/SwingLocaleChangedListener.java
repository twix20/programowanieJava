package lab2.grocerystore.resources;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Label;
import java.util.*;

import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.github.peholmst.i18n4vaadin.LocaleChangedEvent;
import com.github.peholmst.i18n4vaadin.LocaleChangedListener;

public class SwingLocaleChangedListener implements LocaleChangedListener {

    /**
     * Component, BundleString
     */
    private Map<Component, String> abstractComponents = null;

    @Override
    public void localeChanged(LocaleChangedEvent event) {

    	abstractComponents.keySet().forEach(c -> {
    		ResourceBundle rb = Resources.get().getBundle(abstractComponents.get(c));
    		System.out.println("Locale changed to '" + rb.getLocale() + "' for " + c.getName());
    		
    		String newComponentText = rb.getString(c.getName());
    		
    		if(c instanceof AbstractButton) {
    			((AbstractButton)c).setText(newComponentText);
    		}
    		else if(c instanceof Label) {
    			((Label)c).setText(newComponentText);
    		}
    		else if(c instanceof JFrame){
    			((JFrame)c).setTitle(newComponentText);
    		}else {
    			throw new UnsupportedOperationException(c.getName());
    		}
    		
    		c.setComponentOrientation(ComponentOrientation.getOrientation(rb.getLocale())); //EDIT: Line added
    	});
    }
    
    public void add(Component b, String bundleName) {
        initAbstractButtons();
        abstractComponents.put(b, bundleName);
    }

    private void initAbstractButtons() {
        if (abstractComponents == null) {
            this.abstractComponents = new HashMap<>();
        }
    }

}