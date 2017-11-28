package de.hu_berlin.cchecker.core.models.pdfa.pretty;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.emfjson.jackson.resource.JsonResourceFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A {@link JsonResourceFactory} that pretty-prints all outputs by default.
 */
public class PrettyJsonResourceFactory extends JsonResourceFactory {
	public PrettyJsonResourceFactory(ObjectMapper mapper) {
		super(mapper);
	}
	
	@Override
	public Resource createResource(URI uri) {
		return new PrettyJsonResource(uri, this.getMapper());
	}
}
