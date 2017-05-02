package br.com.casadocodigo.loja.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.casadocodigo.loja.configuration.AppWebConfiguration;
import br.com.casadocodigo.loja.configuration.DataSourceConfigurationTest;
import br.com.casadocodigo.loja.configuration.JPAConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JPAConfiguration.class, AppWebConfiguration.class,
		DataSourceConfigurationTest.class })
@WebAppConfiguration
@ActiveProfiles("test")
public class HomeControllerTest {

	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mock;
	
	@Before
	public void init() {
		this.mock = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void deveRetornarParaHomeComLivros() throws Exception {
		this.mock.perform(MockMvcRequestBuilders.get("/"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("produtos"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/views/home.jsp"));
	}

}
