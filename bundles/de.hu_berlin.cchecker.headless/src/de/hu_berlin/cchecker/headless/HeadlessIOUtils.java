package de.hu_berlin.cchecker.headless;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import de.hu_berlin.cchecker.core.checking.report.ConsistencyReport;
import de.hu_berlin.cchecker.core.checking.report.ConsistencyReportUtils;
import de.hu_berlin.cchecker.core.models.ModelSetup;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;

/**
 * Headless IO utilities for {@link ProbabilisticAutomata}s and {@link ConsistencyReport}s.
 */
public class HeadlessIOUtils {
	private static ResourceSet resourceSet;

	private static ResourceSet getResourceSet() {
		ModelSetup.register();
		if (null != resourceSet) {
			return resourceSet;
		}
		resourceSet = new ResourceSetImpl();
		return resourceSet;
	}

	/**
	 * Loads a {@link ProbabilisticAutomata} from the given file.
	 * 
	 * Returns {@code null} on failure.
	 * 
	 * Assumes the given file exists.
	 */
	public static ProbabilisticAutomata loadFromFile(File path) {
		URI uri = URI.createFileURI(path.toPath().toString());
		Resource resource = getResourceSet().getResource(uri, true);

		EList<EObject> contents = resource.getContents();

		if (contents.isEmpty()) {
			return null;
		} else {
			EObject firstObject = contents.get(0);
			if (firstObject instanceof ProbabilisticAutomata) {
				return (ProbabilisticAutomata) firstObject;
			} else {
				return null;
			}
		}
	}

	private static String getSerializedModel(EObject model, String fileExtension) throws IOException {
		if (ConsistAntsCommandLineInterface.getCurrent().isPerformance()) {
			/* Skip output generation in performance mode */
			return "";
		}

		Resource r = getResourceSet().createResource(URI.createFileURI("./tmp." + fileExtension));
		r.getContents().add(model);

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			r.save(outputStream, null);
			// print output
			return new String(outputStream.toByteArray());
		}
	}

	/**
	 * Returns the serialised version (JSON) of the given
	 * {@link ProbabilisticAutomata} model.
	 * 
	 * @throws IOException
	 *             if the temporary resource handling fails
	 */
	public static String getSerializedAutomatonString(ProbabilisticAutomata automaton) throws IOException {
		return getSerializedModel(automaton, "pdfa");
	}

	/**
	 * Returns the serialised version (XMI) of the given {@link ConsistencyReport}
	 * model.
	 * 
	 * @throws IOException
	 *             if the temporary resource handling fails
	 */
	public static String getSerializedReportString(ConsistencyReport report) throws IOException {
		return getSerializedModel(report, "report");
	}

	/**
	 * Returns a string-representation of the given report.
	 * 
	 * The format of the representation is either human-readable or it is the EMF
	 * XMI serialisation if further processing is required.
	 * 
	 * @param report
	 *            The automaton
	 * @param humanReadable
	 *            Specifies the type of output format.
	 * @return
	 */
	public static String reportToString(ConsistencyReport report, boolean humanReadable) {
		if (humanReadable) {
			return ConsistencyReportUtils.createTextualReport(report);
		} else {
			try {
				return getSerializedReportString(report);
			} catch (IOException e) {
				throw new CommandLineExecutionFailed("Failed to serialize consistency report.");
			}
		}
	}

	/**
	 * Returns a string-representation of the given automaton.
	 * 
	 * The format of the representation is either human-readable but can't be used
	 * as input for the framework, or it is the JSON EMF serialisation.
	 * 
	 * @param a
	 *            The automaton
	 * @param humanReadable
	 *            Specifies the type of output format.
	 * @return
	 */
	public static String automatonToString(ProbabilisticAutomata a, boolean humanReadable) {
		if (humanReadable) {
			return a.toString();
		} else {
			try {
				return getSerializedAutomatonString(a);
			} catch (IOException e) {
				throw new CommandLineExecutionFailed("Failed to serialize resulting automaton");
			}
		}
	}
	
	/**
	 * Returns the checksum of the given file.
	 */
	public static String getChecksum(File file) throws IOException {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("Hash algorithm is unavailable");
		}
		md.update(Files.readAllBytes(file.toPath()));
		return DatatypeConverter.printHexBinary(md.digest());
	}
	
	private HeadlessIOUtils() {
		// non-instantiable utility class
	}
}
