package ca.mcgill.cs.NoviceHelper.translator;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import ca.mcgill.cs.NoviceHelper.Activator;
/**
 * Translator contains one static method called translate.
 * Translator depends on ErrorDictionary.properties for look up of errors.
 * @author Caroline Berger
 *
 */
public class Translator {
	/**
	 * sole method.
	 * translate is a static method that translates an error with 
	 * the ErrorDictionary.properties
	 * @param error compiler produced error
	 * @return translation, if there is no translation, return the original error
	 */
	
	public static String translate(String error) {
		String translation = null;
		Properties prop = new Properties();
		InputStream input = null;
		
		// find ErrorDictionary
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		URL url = bundle.getEntry("ErrorDictionary.properties");
		
		try {
			input = url.openStream();
		} 
		catch (IOException e1) {
			e1.printStackTrace();
			return "Internal error: unable to open stream";
		}

		if (input == null) {
			return "Internal error: locating ErrorDictionary";
		}
		
		try {
			prop.load(input);
			translation = prop.getProperty(error);
			
			//if no translation exists, show the original error
			if (translation == null) {
				translation = error;
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if (input != null) {
				try{
					input.close();
				}
				catch (IOException e){
					e.printStackTrace();
				}
			}
		}
		return translation;
		
	}
}
