package lab2.grocerystore.resources;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.util.*;
import java.util.function.Consumer;

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
    private List<Consumer<LocaleChangedEvent>> eventOcuredFunctions = null;

    @Override
    public void localeChanged(LocaleChangedEvent event) {

    	abstractComponents.keySet().forEach(c -> {
    		ResourceBundle rb = Resources.get().getBundle(abstractComponents.get(c));
    		String newComponentText = rb.getString(c.getName());
    		
    		System.out.println("Locale changed to '" + rb.getLocale() + "' for " + c.getName() + " newString " + newComponentText);
    		
    		if(c instanceof AbstractButton) {
    			((AbstractButton)c).setText(newComponentText);
    		}
    		else if(c instanceof JLabel) {
    			((JLabel)c).setText(newComponentText);
    		}
    		else if(c instanceof JFrame){
    			((JFrame)c).setTitle(newComponentText);
    		}
    		else {
    			throw new UnsupportedOperationException(c.getName());
    		}
    		
    		c.setComponentOrientation(ComponentOrientation.getOrientation(rb.getLocale())); //EDIT: Line added
    	});
    	
    	eventOcuredFunctions.forEach(c -> {
    		c.accept(event);
    	});
    }
    
    public void add(Component b, String bundleName) {
    	init();
        abstractComponents.put(b, bundleName);
    }
    
    public void add(Consumer<LocaleChangedEvent> toRun) {
    	init();
    	eventOcuredFunctions.add(toRun);
    }

    private void init() {
        if (abstractComponents == null)
            this.abstractComponents = new HashMap<>();
        
        if(eventOcuredFunctions == null)
        	this.eventOcuredFunctions = new ArrayList<>();
    }

}