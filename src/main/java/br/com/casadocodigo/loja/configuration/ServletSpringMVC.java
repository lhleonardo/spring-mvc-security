package br.com.casadocodigo.loja.configuration;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { SecurityConfiguration.class, AppWebConfiguration.class, JPAConfiguration.class };
	}

	/**
	 * Configurações que dizem para o Spring quais são os controllers da
	 * aplicação.
	 * 
	 * Essas classes são responsáveis por conter todas as informações dos
	 * gerenciadores de mapeamentos e outros.
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {};
	}

	/**
	 * Caminhos que o Spring gerenciará na aplicação, a partir de seus
	 * mapeamentos.
	 * 
	 * Coloquei / para gerenciar todas as requisições.
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	/**
	 * Método responsável por prover ao spring os filtros já existentes e também
	 * adicionar um a mais, que neste caso será o filtro de encoding dos dados.
	 */
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		return new Filter[] { filter };
	}

	/**
	 * Diz para o spring que agora ele consegue resolver situações de envios de
	 * arquivos para forms com Multipart
	 */
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		servletContext.addListener(RequestContextListener.class);
		servletContext.setInitParameter("spring.profiles.active", "dev");
	}

}
