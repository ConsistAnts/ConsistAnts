package de.hu_berlin.cchecker.core.models.pdfa.pretty;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.emfjson.jackson.databind.EMFContext;
import org.emfjson.jackson.resource.JsonResource;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A {@link JsonResource} that pretty-prints all output by default.
 */
public class PrettyJsonResource extends JsonResource {

	private ObjectMapper mapperCopy;

	public PrettyJsonResource(URI uri) {
		super(uri);
	}

	public PrettyJsonResource(URI uri, ObjectMapper mapper) {
		super(uri, mapper);
		this.mapperCopy = mapper;
	}

	@Override
	protected void doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {
		if (options == null) {
			options = Collections.<String, Object>emptyMap();
		}

		if (outputStream instanceof URIConverter.Saveable) {
			((URIConverter.Saveable) outputStream).saveResource(this);
		} else {
			mapperCopy.writerWithDefaultPrettyPrinter().with(EMFContext.from(options)).writeValue(outputStream, this);
		}
	}

}
