package Lab11.GroceryStoreFX.resources;

import java.util.*;
import java.util.function.Consumer;

import com.github.peholmst.i18n4vaadin.LocaleChangedEvent;
import com.github.peholmst.i18n4vaadin.LocaleChangedListener;

import javafx.scene.control.*;

public class JavaFxLocaleChangedListener implements LocaleChangedListener {

    private Map<Object, BundleInfo> abstractComponents = new HashMap<>();
    private List<Consumer<LocaleChangedEvent>> eventOcuredFunctions = new ArrayList<>();

    @Override
    public void localeChanged(final LocaleChangedEvent event) {

    	abstractComponents.keySet().forEach(c -> {
    		
    		BundleInfo bundleInfo = abstractComponents.get(c);
    		ResourceBundle rb = Resources.get().getBundle(bundleInfo.bundleString);
    		String newComponentText = rb.getString(bundleInfo.bundleKey);
    		
    		System.out.println("Locale changed to '" + rb.getLocale() + "' for " + bundleInfo.bundleKey + " newString " + newComponentText);
    		
    		if(c instanceof Label) {
    			((Label)c).setText(newComponentText);
    		}
    		else if(c instanceof Menu) {
    			((Menu)c).setText(newComponentText);
    		}
    		else if(c instanceof MenuItem) {
    			((MenuItem)c).setText(newComponentText);
    		}
    		else {
    			throw new UnsupportedOperationException(c.getClass().getName());
    		}
    	});
    	
    	eventOcuredFunctions.forEach(c -> {
    		c.accept(event);
    	});
    }
    
    public void add(Object b, String key, String bundleName) {
        abstractComponents.put(b, new BundleInfo(key, bundleName));
    }
    
    public void add(Consumer<LocaleChangedEvent> toRun) {
    	eventOcuredFunctions.add(toRun);
    }
    
    private class BundleInfo {
    	public String bundleString, bundleKey;
    	
    	public BundleInfo(String bundleKey, String bundleString) {
    		this.bundleKey = bundleKey;
    		this.bundleString = bundleString;
    	}
    	
    }
}