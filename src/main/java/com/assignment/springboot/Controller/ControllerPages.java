package com.assignment.springboot.Controller;
import com.assignment.springboot.Model.Member;
import com.assignment.springboot.Repository.CategoryRepository;
import com.assignment.springboot.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ControllerPages {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;


    @GetMapping("/")
    public String viewHome(Model model){
        model.addAttribute("listCategory", categoryRepository.findAll());
        model.addAttribute("listProductFeatured",productService.getListFeatured(4));
        model.addAttribute("listProductSale",productService.getListSale(4));
        model.addAttribute("member", new Member());
        return "index";
    }

    @GetMapping("/category/{categoryId}")
    public String viewProductByCategory(@PathVariable("categoryId") Long categoryId,Model model){
        model.addAttribute("listCategory",categoryRepository.findAll());
        model.addAttribute("listProduct",productService.getListByCategory(categoryId));
        model.addAttribute("member", new Member());
        return "product-with-category";
    }

    @GetMapping("/product")
    public String viewProduct(Model model){
        model.addAttribute("listCategory",categoryRepository.findAll());
        model.addAttribute("listProduct",productService.findAll());
        model.addAttribute("member", new Member());
        return "product";
    }

    @GetMapping("/product/{id}")
    public String viewSingleProduct(@PathVariable("id") Long id, Model model){
        model.addAttribute("listCategory",categoryRepository.findAll());
        model.addAttribute("product",productService.findById(id));
        model.addAttribute("listProductFeatured",productService.getListFeatured(4));
        model.addAttribute("member", new Member());
        return "single";
    }
}
