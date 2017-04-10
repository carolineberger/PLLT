package ca.mcgill.cs.NoviceHelper;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
/**
 * Activator class controls the lifetime of the plugin.
 * @author Caroline Berger
 *
 */
public class Activator extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "ca.mcgill.cs.NoviceHelper";
	
	private static Activator plugin;
	/**
	 * Sole constructor.
	 */
	public Activator() {
	}
	/**
	 * (auto-generated code)
	 * apart of parent (AbstractUIPlugin)
	 * starts plugin with a specific bundle context
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
	}
	/**
	 * (auto-generated code)
	 * apart of parent (AbstractUIPlugin)
	 * stops plugin with a specific bundle context
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}
	/**
	 * 
	 * @return instance of itself
	 */
	public static Activator getDefault() {
		return plugin;
	}
	/**
	 * (auto-generated javadoc):
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
