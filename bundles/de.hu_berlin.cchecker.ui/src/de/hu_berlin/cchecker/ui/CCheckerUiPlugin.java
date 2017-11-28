package de.hu_berlin.cchecker.ui;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import de.hu_berlin.cchecker.core.models.ModelSetup;

/**
 * Bundle activator of the Consistency Checker UI plugin.
 * 
 * Also the central singleton UI plugin instance.
 */
public class CCheckerUiPlugin extends AbstractUIPlugin {
	
	private static CCheckerUiPlugin instance;
	
	/**
	 * Returns a singleton instance of the {@link CCheckerUiPlugin}.
	 * 
	 * This method may return {@code null} if the containing bundle has not been activated yet.
	 */
	public static CCheckerUiPlugin getInstance() {
		return instance;
	}
	
	private ResourceSet resourceSet;
	
	@Override
	public void start(BundleContext context) throws Exception {
		// Register the base EMF package
		EPackage.Registry.INSTANCE.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		
		// Register the PdfaPackage
		ModelSetup.register();
		
		// Create a globally available resource set
		this.resourceSet = new ResourceSetImpl();
		
		// Set singleton instance of the plugin
		instance = this;
		
		super.start(context);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// Unregister the PdfaPackage
		ModelSetup.unregister();
		
		super.stop(context);
	}
	
	/**
	 * Returns a {@link ResourceSet} to query resources in 
	 * the workspace.
	 */
	public ResourceSet getWorkspaceResourceSet() {
		return resourceSet;
	}	
}
