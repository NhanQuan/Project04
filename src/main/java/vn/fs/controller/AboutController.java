package vn.fs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ModelAttribute;
import vn.fs.commom.CommomDataService;
import vn.fs.entities.Faq;
import vn.fs.entities.User;
import vn.fs.repository.FaqRepository;

import java.security.Principal;
import java.util.List;


@Controller
public class AboutController extends CommomController {
	@Autowired
	FaqRepository faqRepository;
	@Autowired
	CommomDataService commomDataService;

	@GetMapping(value = "/aboutUs")
	public String about(Model model, User user) {

		commomDataService.commonData(model, user);
		return "web/about";
	}
	// show list product - table list
	@ModelAttribute("faqs")
	public List<Faq> showFaq(Model model) {
		List<Faq> faqs = faqRepository.findAll();
		model.addAttribute("faqs", faqs);

		return faqs;
	}

	@GetMapping(value = "/faq")
	public String faqs(Model model, Principal principal) {
		Faq faq = new Faq();
		model.addAttribute("faq", faq);

		return "web/faq";
	}

}
