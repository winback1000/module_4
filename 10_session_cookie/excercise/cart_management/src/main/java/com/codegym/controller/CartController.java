package com.codegym.controller;

import com.codegym.model.Cart;
import com.codegym.model.CartProduct;
import com.codegym.model.Customer;
import com.codegym.model.TempCartProduct;
import com.codegym.service.ICartProductService;
import com.codegym.service.ICartService;
import com.codegym.service.ICustomerService;
import com.codegym.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@SessionAttributes({"customer","tempCart"})
public class CartController {
    @Autowired
    private ICustomerService iCustomerService;
    @Autowired
    private IProductService iProductService;
    @Autowired
    private ICartService iCartService;
    @Autowired
    private ICartProductService iCartProductService;

    @GetMapping({"","/","/productList"})

    public String goHomePage(Model model, Pageable pageable) {
        model.addAttribute("productList", iProductService.findAll(pageable));
        return "homePage";
    }
    @GetMapping("/product/detail/{id}")
    public String goDetailPage(Model model, @PathVariable Integer id) {
        model.addAttribute("selectedProduct", iProductService.findById(id));
        return "detailProduct";
    }
    @GetMapping("product/addToCart/{id}")
    public String addToCart(@SessionAttribute("tempCart") List<TempCartProduct> tempCart, @PathVariable Integer id){
        TempCartProduct tempCartProduct = new TempCartProduct();
        tempCartProduct.setProductId(id);
        tempCartProduct.setName(iProductService.findById(id).getName());
        tempCartProduct.setImage(iProductService.findById(id).getImage());
        tempCartProduct.setPrice(iProductService.findById(id).getPrice());
        tempCartProduct.setQuantity(1);
//       CartProduct tempCartProduct = new CartProduct();
//       tempCartProduct.setProduct(iProductService.findById(id));
//       tempCartProduct.setQuantity(1);
//       tempCartProduct.setId(1);
//       tempCartProduct.setCart(customer.getUnpaidCart());
//       customer.getUnpaidCart().getCardProductList().add(tempCartProduct);
//       model.addAttribute("customer", customer);
        tempCart.add(tempCartProduct);
       return "redirect:/productList";
    }
    @GetMapping("/cart")
    public String viewCart() {
//        model.addAttribute("tempCartFix",tempCart);
        return "showCart";
    }
    @ModelAttribute("customer")
    public Customer checkCookie(@CookieValue(defaultValue = "0") String customerId, @ModelAttribute Optional<Customer> customer) {
        if(!customerId.equals("0")){
            return iCustomerService.findById(Integer.parseInt(customerId));
        } else {
            if(!customer.isPresent()) {
                return new Customer();
            } else return customer.get();
        }
    }
    @ModelAttribute("tempCart")
    public List<TempCartProduct> createTempCart(){
        List<TempCartProduct> tempCart = new ArrayList<>();
        return tempCart;
    }
}
