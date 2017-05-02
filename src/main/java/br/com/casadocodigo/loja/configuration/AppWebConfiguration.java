package br.com.casadocodigo.loja.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.google.common.cache.CacheBuilder;

import br.com.casadocodigo.loja.controllers.HomeController;
import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Carrinho;

// ativa o módulo de MVC para o projeto
@EnableWebMvc
// diz para o Spring quais são os pacotes que ele deve escanear a partir das
// classes informadas.
@ComponentScan(basePackageClasses = { HomeController.class, ProdutoDAO.class, FileSaver.class, Carrinho.class })
@EnableCaching
public class AppWebConfiguration extends WebMvcConfigurerAdapter {

	/**
	 * Diz para o Spring qual é o prefixo e sulfixo da localização dos arquivos
	 * de JSPs dentro da pasta WEB-INF
	 * 
	 * @return @Bean (classe gerenciada) do Spring
	 */
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");

		resolver.setExposedContextBeanNames("carrinho");

		return resolver;
	}

	/**
	 * Método responsável por configurar a externalização do arquivo padrão de
	 * mensagens da aplicação, configurando seu Encoding e também o tempo para
	 * recarrega-lo, importante para que não seja necessário o restart da
	 * aplicação quando o arquivo de mensagens for modificado.
	 * 
	 * @return {@link ReloadableResourceBundleMessageSource}
	 */
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource message = new ReloadableResourceBundleMessageSource();
		message.setBasename("/WEB-INF/messages");
		message.setDefaultEncoding("UTF-8");
		message.setCacheSeconds(1);

		return message;
	}

	/**
	 * Método responsável por manipular os padrões de datas no sistema,
	 * incluindo seus registradores.
	 * 
	 */
	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService service = new DefaultFormattingConversionService();
		DateFormatterRegistrar register = new DateFormatterRegistrar();
		register.setFormatter(new DateFormatter("dd/MM/yyyy"));
		register.registerFormatters(service);
		return service;
	}

	/**
	 * Método responsável por configurar o Multipart de arquivos enviados em
	 * formulários, para indexação e disponibilização no servidor
	 * 
	 * @return
	 */
	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}

	/**
	 * Método que ativa o gerenciamento dos arquivos (na verdade não sei bem o
	 * porquê dele estar aqui)
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/**
	 * Método responsável por configurar o acesso externo de servidores REST,
	 * acessando-os a partir da implementação provinda pelo Spring
	 * 
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	/**
	 * Método responsável por configurar o Guava, gerenciador de caches da
	 * aplicação feita pelo Google
	 * 
	 * @return
	 */
	@Bean
	public CacheManager cacheManager() {
		CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.MINUTES)
				.initialCapacity(100);
		GuavaCacheManager manager = new GuavaCacheManager();
		manager.setCacheBuilder(builder);
		return manager;
	}

	/**
	 * Método responsável por dizer a aplicação que ela deverá gerar exportação
	 * quando uma url que consome dados for entregue com um .json no final de
	 * seu recurso
	 * 
	 * @param manager
	 * @return
	 */
	@Bean
	public ViewResolver contentNegotiation(ContentNegotiationManager manager) {
		List<ViewResolver> viewResolvers = new ArrayList<>();
		viewResolvers.add(internalResourceViewResolver());
		viewResolvers.add(new JsonViewResolver());

		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setViewResolvers(viewResolvers);
		resolver.setContentNegotiationManager(manager);

		return resolver;
	}

	/**
	 * Método responsável por adicionar um interceptador de locale, para que
	 * quando for recebido na url o parâmetro
	 * 
	 * <code>?locale=en</code>, o sistema utilize o arquivo padrão para o
	 * Inglês, e assim vai para os outros idiomas.
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
	}

	/**
	 * Método responsável por dizer ao spring que a forma padrão de gerenciar o
	 * locale é através de cookies.
	 * 
	 * @return
	 */
	@Bean
	public LocaleResolver localeResolver() {
		return new CookieLocaleResolver();
	}
}
