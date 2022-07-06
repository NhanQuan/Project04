package vn.fs.controller.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.fs.entities.Category;
import vn.fs.entities.Product;
import vn.fs.entities.User;
import vn.fs.repository.CategoryRepository;
import vn.fs.repository.OrderDetailRepository;
import vn.fs.repository.ProductRepository;
import vn.fs.repository.UserRepository;

/**
 * @author DongTHD
 *
 */
@Controller
@RequestMapping("/admin")
public class ProductController{

	@Value("${upload.path}")
	private String pathUploadImage;

	@Autowired
	ProductRepository productRepository;
	@Autowired
	OrderDetailRepository orderDetailRepository;
	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	UserRepository userRepository;

	@ModelAttribute(value = "user")
	public User user(Model model, Principal principal, User user) {

		if (principal != null) {
			model.addAttribute("user", new User());
			user = userRepository.findByEmail(principal.getName());
			model.addAttribute("user", user);
		}

		return user;
	}

	public ProductController(CategoryRepository categoryRepository,
							 ProductRepository productRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}

	// show list product - table list
	@ModelAttribute("products")
	public List<Product> showProduct(Model model) {
		List<Product> products = productRepository.findAll();
		model.addAttribute("products", products);

		return products;
	}

	@GetMapping(value = "/products")
	public String products(Model model, Principal principal) {
		Product product = new Product();
		model.addAttribute("product", product);

		return "admin/products";
	}

	// add product
	@PostMapping(value = "/addProduct")
	public String addProduct(@ModelAttribute("product") Product product, ModelMap model,
							 @RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest) {

		if(productRepository.ProductName(product.getProductName()).isEmpty()){
			try {
				File convFile = new File(pathUploadImage + "/" + file.getOriginalFilename());
				FileOutputStream fos = new FileOutputStream(convFile);
				fos.write(file.getBytes());
				fos.close();
			} catch (IOException e) {
			}
			product.setProductImage(file.getOriginalFilename());
			product.setEnteredDate(new Date());
			Product p = productRepository.save(product);
			if (null != p) {
				model.addAttribute("message", "Add success");
				model.addAttribute("product", product);
				return "redirect:/admin/products";

			} else {
				model.addAttribute("error", "Add failure");
				model.addAttribute("product", product);
				return "redirect:/admin/products";
			}
		}else {
			model.addAttribute("error", "Product already exists ");
			model.addAttribute("product", product);
			return "admin/products";
		}

	}

	// show select option ở add product
	@ModelAttribute("categoryList")
	public List<Category> showCategory(Model model) {
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categoryList", categoryList);

		return categoryList;
	}

	// get Edit brand
	@GetMapping(value = "/editProduct/{id}")
	public String editCategory(@PathVariable("id") Long id, ModelMap model) {
		Product product = productRepository.findById(id).orElse(null);

		model.addAttribute("product", product);

		return "admin/editProduct";
	}
	// edit product
	@PostMapping(value = "/editProduct")
	public String editProduct(@ModelAttribute("product") Product p, ModelMap model,
							  @RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest) {
		Product product = productRepository.findById(p.getProductId()).orElse(null);
			if (file.isEmpty()) {
				p.setProductImage(product.getProductImage());
			} else {
				try {
					File convFile = new File(pathUploadImage + "/" + file.getOriginalFilename());
					FileOutputStream fos = new FileOutputStream(convFile);
					fos.write(file.getBytes());
					fos.close();
				} catch (IOException e) {
				}
				p.setProductImage(file.getOriginalFilename());
			}
			productRepository.save(p);
			if (null != p) {
				model.addAttribute("message", "Update success");
				model.addAttribute("product", p);
			} else {
				model.addAttribute("message", "Update failure");
				model.addAttribute("product", p);
			}
		return "redirect:/admin/products";
	}

	// delete category
	@GetMapping("/deleteProduct/{id}")
	public String delProduct(@PathVariable("id") Long id, Model model) {
		productRepository.deleteById(id);
		model.addAttribute("message", "Delete successful!");

		return "redirect:/admin/products";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
}
