package vn.fs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import vn.fs.commom.CommomDataService;
import vn.fs.entities.Comment;
import vn.fs.entities.Product;
import vn.fs.entities.Role;
import vn.fs.entities.User;
import vn.fs.repository.CommentRepository;
import vn.fs.repository.ProductRepository;
import vn.fs.repository.UserRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;


@Controller
public class CommentController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommomDataService commomDataService;

    @PostMapping("/addComment")
    public String AddComment(Model model,Comment comment,
                             @RequestParam("userId") Long userId,
                             @RequestParam("content") String content,
                             @RequestParam("id") Long id,
                             @RequestParam("star") int star) {
        User user = userRepository.findById(userId).orElse(null);
        Product product = productRepository.findById(id).orElse(null);
        comment.setProduct(product);
        comment.setUser(user);
        comment.setContent(content);
        comment.setStar(star);
        comment.setStatus(1);
        comment.setRateDate(new Date());
        commentRepository.save(comment);
        commomDataService.commonData(model, user);
        return "redirect:/productDetail?id=" + product.getProductId();
    }

}
