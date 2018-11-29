package br.com.cresol.desafio;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author evandro
 *
 */
@ApplicationPath("/api")
public class DesafioApplication extends Application {

	public DesafioApplication() {
//		BeanConfig conf = new BeanConfig();
//		conf.setTitle("Desafio Desenvolvimento API");
//		conf.setDescription("Desafio Desenvolvimento API - Cresol Confederacao");
//		conf.setVersion("1.0.0");
//		conf.setHost("localhost:8080");
//		conf.setBasePath("/desafio-desenvolvimento/api");
//		conf.setSchemes(new String[] { "http" });
//		conf.setResourcePackage("br.com.cresol.desafio");
//		conf.setScan(true);
	}

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new HashSet<>();
//		resources.add(EmprestimoResource.class);
//		resources.add(ApiListingResource.class);
//		resources.add(SwaggerSerializers.class);
		return resources;
	}
}
