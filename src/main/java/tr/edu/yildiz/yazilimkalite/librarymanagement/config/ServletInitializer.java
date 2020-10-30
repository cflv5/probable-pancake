package tr.edu.yildiz.yazilimkalite.librarymanagement.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import tr.edu.yildiz.yazilimkalite.librarymanagement.LibraryManagementApplication;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(LibraryManagementApplication.class);
	}

}
