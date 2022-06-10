package vn.fs.ApiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.fs.entities.Product;
import vn.fs.entities.ResponseObject;
import vn.fs.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/app/api/products")
public class HomeApiController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("")
    List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    //getDetailProduct
    @GetMapping("/{productId}")
    ResponseEntity<ResponseObject> ProductDetail(@PathVariable Long productId){
        Optional<Product> foundProduct = productRepository.findById(productId);
        return foundProduct.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("Ok", "Query product Success", foundProduct)
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("Ok", "Cannot find Product With id =" + productId, "foundProduct")
                );
    }

    //Top10 product Same Type
    @GetMapping("/listProductTop/{categoryId}")
    ResponseEntity<ResponseObject> listProductByCategory10(@PathVariable Long categoryId){
        List<Product> foundProduct = productRepository.listProductByCategory10(categoryId);
        return foundProduct.size() > 0 ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("Ok", "Query product Success", foundProduct)
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("Ok", "Cannot find Product With id =" + categoryId, "foundProduct")
                );
    }
    //Top10 product Same Type
    @GetMapping("/listproduct10")
    ResponseEntity<ResponseObject> listproduct10(){
        List<Product> foundProduct = productRepository.listProductNew20();
        return foundProduct.size() > 0 ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("Ok", "Query product Success", foundProduct)
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("Ok", "Query product Fails", "foundProduct")
                );
    }


}