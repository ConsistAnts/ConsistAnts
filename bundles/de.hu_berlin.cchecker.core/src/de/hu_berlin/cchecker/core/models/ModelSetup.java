package de.hu_berlin.cchecker.core.models;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emfjson.jackson.module.EMFModule;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.hu_berlin.cchecker.core.checking.report.ReportPackage;
import de.hu_berlin.cchecker.core.models.pdfa.PdfaPackage;
import de.hu_berlin.cchecker.core.models.pdfa.pretty.PrettyJsonResourceFactory;

/**
 * Setup class to register and unregister the EMF packages of the core models. 
 */
public class ModelSetup {
	public static final String PROBABILISTIC_AUTOMATA_FILE_EXTENSION = "pdfa";
	public static final String CONSISTENCY_REPORT_FILE_EXTENSION = "report";
	
	public static void register() {
		
		// Register EMF package
		EPackage.Registry.INSTANCE.put(PdfaPackage.eNS_URI, PdfaPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(ReportPackage.eNS_URI, ReportPackage.eINSTANCE);
		
		// Create ObjectMapper for EMF-JSON Serialization and 
		// register EMF module with JSON Deserializer
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new EMFModule());
		
		Resource.Factory.Registry.INSTANCE
			.getExtensionToFactoryMap().put(PROBABILISTIC_AUTOMATA_FILE_EXTENSION, new PrettyJsonResourceFactory(mapper));
		
		Resource.Factory.Registry.INSTANCE
			.getExtensionToFactoryMap().put(CONSISTENCY_REPORT_FILE_EXTENSION, new XMIResourceFactoryImpl());
	}
	
	public static void unregister() {
		// Unregister the package
		EPackage.Registry.INSTANCE.remove(PdfaPackage.eNS_URI);
		EPackage.Registry.INSTANCE.remove(ReportPackage.eNS_URI);
		
		// Unregister the file extension
		// Clear file extension associations
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
			.remove(PROBABILISTIC_AUTOMATA_FILE_EXTENSION);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
			.remove(CONSISTENCY_REPORT_FILE_EXTENSION);
	}
}
