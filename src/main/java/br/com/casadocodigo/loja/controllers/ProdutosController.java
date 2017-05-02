package br.com.casadocodigo.loja.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;
import br.com.casadocodigo.loja.validation.ProdutoValidation;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {

	@Autowired
	private ProdutoDAO daoProduto;

	@Autowired
	private FileSaver fileSaver;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new ProdutoValidation());
	}

	@RequestMapping("/form")
	public ModelAndView form(Produto p) {
		ModelAndView mv = new ModelAndView("produtos/form");
		mv.addObject("tipos", TipoPreco.values());

		return mv;
	}

	@RequestMapping(method = { RequestMethod.POST })
	@CacheEvict(value = "produtosHome", allEntries = true)
	public ModelAndView grava(MultipartFile sumario, @Valid Produto produto, BindingResult result,
			RedirectAttributes redirectAttributes) {
		System.out.println(sumario.getOriginalFilename());
		if (result.hasErrors()) {
			return form(produto);
		}

		String path = fileSaver.write("arquivos-sumario", sumario);
		produto.setSumarioPath(path);
		daoProduto.grava(produto);
		ModelAndView mv = new ModelAndView("redirect:produtos");
		redirectAttributes.addFlashAttribute("success", "Produto salvo com sucesso!");
		return mv;
	}

	@RequestMapping(method = { RequestMethod.GET })
	public ModelAndView lista() {
		ModelAndView mv = new ModelAndView("produtos/lista");
		mv.addObject("produtos", daoProduto.listaTodos());

		return mv;

	}

	@RequestMapping("/detalhe/{id}")
	public ModelAndView detalhe(@PathVariable(value = "id") Long id) {
		ModelAndView mv = new ModelAndView("produtos/detalhe");
		mv.addObject("produto", daoProduto.buscaPeloId(id));
		return mv;
	}

}
