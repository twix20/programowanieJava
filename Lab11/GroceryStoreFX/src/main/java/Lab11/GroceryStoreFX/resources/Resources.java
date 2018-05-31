package Lab11.GroceryStoreFX.resources;

import java.awt.Component;
import java.text.NumberFormat;
import java.util.*;
import java.util.function.Consumer;

import com.github.peholmst.i18n4vaadin.*;

public class Resources {
	public final static String GUI_BUNDLE = "lab2/grocerystore/resources/gui";
	public final static String GROCERY_ITEMS_BUNDLE = "lab2/grocerystore/resources/groceryItems";
	
	private static Resources instance = null;
    public static Resources get() {
        if (instance == null) {
            instance = new Resources();
        }
        return instance;
    }
	
	private List<Locale> supportedLocales = new ArrayList<>(Arrays.asList(new Locale("en", "US"), new Locale("pl", "PL")));
	private SupportedLocale currentLocale = SupportedLocale.English;
    private SwingLocaleChangedListener listener = new SwingLocaleChangedListener();

	public void changeLocale(SupportedLocale locale) {
		this.currentLocale = locale;
		
        if (listener != null) {
        	LocaleChangedEvent e = new LocaleChangedEvent(null, getCurrentLocale());
            listener.localeChanged(e);
        }

	}
	
    public void register(Component c, String bundleName) {
        listener.add(c, bundleName);
    }
    
    public void register(Consumer<LocaleChangedEvent> toRun) {
    	listener.add(toRun);
    }
	
	public String localizeNumber(Number n) {
		NumberFormat format = NumberFormat.getNumberInstance(getCurrentLocale());
		
		return format.format(n);
	}
	
	public ResourceBundle getBundle(String bundleName) {
		return ResourceBundle.getBundle(bundleName, getCurrentLocale());
	}

	public Locale getCurrentLocale() {
		return supportedLocales.get(currentLocale.ordinal());
	}
	
	public enum SupportedLocale{
		English,
		Polish
	}

}
