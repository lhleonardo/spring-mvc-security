package br.com.casadocodigo.loja.controllers;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.models.Carrinho;
import br.com.casadocodigo.loja.models.DadosPagamento;

@Controller
@RequestMapping("/pagamento")
public class PagamentoController {

	@Autowired
	private Carrinho carrinho;

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(value = "/finaliza", method = RequestMethod.POST)
	public Callable<ModelAndView> finaliza(RedirectAttributes model) {
		return () -> {
			
			ModelAndView mv = new ModelAndView("redirect:/produtos");

			try {
				String uri = "http://book-payment.herokuapp.com/payment";
				String result = restTemplate.postForObject(uri, new DadosPagamento(carrinho.getValorTotal()),
						String.class);

				model.addFlashAttribute("success", result);
			} catch (HttpClientErrorException e) {
				e.printStackTrace();
				model.addFlashAttribute("error", e.getResponseBodyAsString());
			}

			return mv;
		};
		
	}
}
