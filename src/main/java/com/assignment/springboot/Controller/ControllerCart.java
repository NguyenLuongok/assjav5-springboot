package com.assignment.springboot.Controller;
import com.assignment.springboot.Model.*;
import com.assignment.springboot.Repository.CategoryRepository;
import com.assignment.springboot.Service.MemberService;
import com.assignment.springboot.Service.ProductService;
import com.assignment.springboot.Service.ReceiptItemService;
import com.assignment.springboot.Service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("cart/")
public class ControllerCart {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private ReceiptItemService receiptItemService;

    @Autowired
    private MemberService memberService;

    @Autowired
    public JavaMailSender emailSender;

    @GetMapping("add/{productId}")
    public String addToCart(Model model, HttpSession session, @PathVariable("productId") Long productId){
        Member mb = (Member) session.getAttribute("myLogin");
       if(session.getAttribute("myLogin")==null){
           if(session.getAttribute("myCartItems") == null){
               List<Cart> cartItems = new ArrayList<Cart>();
               cartItems.add(new Cart(productService.findById(productId),1));
               session.setAttribute("myCartItems", cartItems);
               session.setAttribute("myCartTotal",totalPriceItems(cartItems));
               session.setAttribute("myCartNum",cartItems.size());
           }
           else {
               List<Cart> cartItems = (List<Cart>) session.getAttribute("myCartItems");
               int index = this.existsItems(productId, cartItems);
               if (index == -1) {
                   cartItems.add(new Cart(productService.findById(productId), 1));
               } else {
                   int quantity = cartItems.get(index).getQuantity() + 1;
                   cartItems.get(index).setQuantity(quantity);
               }
               session.setAttribute("myCartItems", cartItems);
               session.setAttribute("myCartTotal",totalPriceItems(cartItems));
               session.setAttribute("myCartNum",cartItems.size());
           }
       }
       else {
           if(session.getAttribute(mb.getMemberEmail()) == null){
               List<CartMember> cartItems = new ArrayList<CartMember>();
               cartItems.add(new CartMember(productService.findById(productId),1));
               session.setAttribute(mb.getMemberEmail(), cartItems);
               session.setAttribute("myCartMembers", session.getAttribute(mb.getMemberEmail()));
               session.setAttribute("myCartTotal",totalPriceMember(cartItems));
               session.setAttribute("myCartNum",cartItems.size());
           }
           else {
               List<CartMember> cartMembers = (List<CartMember>) session.getAttribute(mb.getMemberEmail());
               int index = this.existsMember(productId, cartMembers);
               if (index == -1) {
                   cartMembers.add(new CartMember(productService.findById(productId), 1));
               } else {
                   int quantity = cartMembers.get(index).getQuantity() + 1;
                   cartMembers.get(index).setQuantity(quantity);
               }
               session.setAttribute(mb.getMemberEmail(), cartMembers);
               session.setAttribute("myCartMembers", session.getAttribute(mb.getMemberEmail()));
               session.setAttribute("myCartTotal",totalPriceMember(cartMembers));
               session.setAttribute("myCartNum",cartMembers.size());
           }
       }
        model.addAttribute("member", new Member());
        model.addAttribute("listCategory",categoryRepository.findAll());
        return "cart";
    }

    @GetMapping("cart")
    public String viewCart(Model model,HttpSession session){
        Member mb = (Member) session.getAttribute("myLogin");
        if (session.getAttribute("myLogin") ==null){
            List<Cart> cartItems = (List<Cart>) session.getAttribute("myCartItems");
            session.setAttribute("myCartItems",cartItems);
            if(cartItems != null){
                session.setAttribute("myCartTotal",totalPriceItems(cartItems));
                session.setAttribute("myCartNum",cartItems.size());
            }
            model.addAttribute("listCategory",categoryRepository.findAll());
        }
        else {
            List<CartMember> cartMembers = (List<CartMember>) session.getAttribute(mb.getMemberEmail());
            if(session.getAttribute("myCartMembers")!=null){
                session.setAttribute("myCartTotal",totalPriceMember(cartMembers));
                session.setAttribute("myCartNum",cartMembers.size());
            }
            session.setAttribute("myCartMembers",cartMembers);
            model.addAttribute("listCategory",categoryRepository.findAll());
        }
        model.addAttribute("member", new Member());
        return "cart";
    }

    @PostMapping("add/{productId}")
    public String addToCartSingle (@PathVariable("productId") Long productId,@RequestParam("total") int total,Model model, HttpSession session){
        Product product = productService.findById(productId);
        Member mb = (Member) session.getAttribute("myLogin");
       if (total < 0 || total > product.getProductTotal()){
           model.addAttribute("listCategory",categoryRepository.findAll());
           model.addAttribute("product",productService.findById(productId));
           model.addAttribute("listProductFeatured",productService.getListFeatured(4));
           model.addAttribute("member", new Member());
            return "single";
       }
        if(session.getAttribute("myLogin")==null){
            if(session.getAttribute("myCartItems") == null){
                List<Cart> cartItems = new ArrayList<Cart>();
                cartItems.add(new Cart(productService.findById(productId),total));
                session.setAttribute("myCartItems", cartItems);
                session.setAttribute("myCartTotal",totalPriceItems(cartItems));
                session.setAttribute("myCartNum",cartItems.size());
            }
            else {
                List<Cart> cartItems = (List<Cart>) session.getAttribute("myCartItems");
                int index = this.existsItems(productId, cartItems);
                if (index == -1) {
                    cartItems.add(new Cart(productService.findById(productId), total));
                } else {
                    int quantity = cartItems.get(index).getQuantity() + total;
                    cartItems.get(index).setQuantity(quantity);
                }
                session.setAttribute("myCartItems", cartItems);
                session.setAttribute("myCartTotal",totalPriceItems(cartItems));
                session.setAttribute("myCartNum",cartItems.size());
            }
        }
        else {
            if(session.getAttribute(mb.getMemberEmail()) == null){
                List<CartMember> cartItems = new ArrayList<CartMember>();
                cartItems.add(new CartMember(productService.findById(productId),total));
                session.setAttribute(mb.getMemberEmail(), cartItems);
                session.setAttribute("myCartMembers", session.getAttribute(mb.getMemberEmail()));
                session.setAttribute("myCartTotal",totalPriceMember(cartItems));
                session.setAttribute("myCartNum",cartItems.size());
            }
            else {
                List<CartMember> cartMembers = (List<CartMember>) session.getAttribute(mb.getMemberEmail());
                int index = this.existsMember(productId, cartMembers);
                if (index == -1) {
                    cartMembers.add(new CartMember(productService.findById(productId), total));
                } else {
                    int quantity = cartMembers.get(index).getQuantity() + total;
                    cartMembers.get(index).setQuantity(quantity);
                }
                session.setAttribute(mb.getMemberEmail(), cartMembers);
                session.setAttribute("myCartMembers", session.getAttribute(mb.getMemberEmail()));
                session.setAttribute("myCartTotal",totalPriceMember(cartMembers));
                session.setAttribute("myCartNum",cartMembers.size());
            }
        }
        model.addAttribute("member", new Member());
        model.addAttribute("listCategory",categoryRepository.findAll());
        return "cart";
    }

    @GetMapping("/update/{productId}")
    public String updateCart(@PathVariable("productId") Long productId,@RequestParam("total") int total,Model model,HttpSession session){
        List<Cart> cartItems = (List<Cart>) session.getAttribute("myCartItems");
        Member mb = (Member) session.getAttribute("myLogin");
        Product product  = productService.findById(productId);
        if(0 >total || total > product.getProductTotal()){
            model.addAttribute("listCategory",categoryRepository.findAll());
            session.setAttribute("myCartItems",cartItems);
            session.setAttribute("myCartTotal",totalPriceItems(cartItems));
            session.setAttribute("myCartNum",cartItems.size());
            return "cart";
        }
        if (session.getAttribute("myLogin") == null){
            int index = this.existsItems(productId,cartItems);
            cartItems.get(index).setQuantity(total);
            session.setAttribute("myCartItems",cartItems);
            session.setAttribute("myCartTotal",totalPriceItems(cartItems));
            session.setAttribute("myCartNum",cartItems.size());
        }
        else {
            List<CartMember> cartMembers = (List<CartMember>) session.getAttribute(mb.getMemberEmail());
            int index = this.existsMember(productId,cartMembers);
            cartMembers.get(index).setQuantity(total);
            session.setAttribute(mb.getMemberEmail(),cartMembers);
            session.setAttribute("myCartMembers",session.getAttribute(mb.getMemberEmail()));
            session.setAttribute("myCartTotal",totalPriceMember(cartMembers));
            session.setAttribute("myCartNum",cartMembers.size());
        }
        model.addAttribute("member", new Member());
        model.addAttribute("listCategory",categoryRepository.findAll());
        return "cart";
    }

    @GetMapping("/remove/{productId}")
    public String removeCart(@PathVariable("productId") Long productId,HttpSession session,Model model){
        List<Cart> cartItems = (List<Cart>) session.getAttribute("myCartItems");
        Member mb = (Member) session.getAttribute("myLogin");
        if (session.getAttribute("myLogin") == null){
            int index = this.existsItems(productId, cartItems);
            cartItems.remove(index);
            session.setAttribute("myCartItems",cartItems);
            session.setAttribute("myCartTotal",totalPriceItems(cartItems));
            session.setAttribute("myCartNum",cartItems.size());
        }
        else {
            List<CartMember> cartMembers = (List<CartMember>) session.getAttribute(mb.getMemberEmail());
            int index = this.existsMember(productId, cartMembers);
            cartMembers.remove(index);
            session.setAttribute(mb.getMemberEmail(),cartMembers);
            session.setAttribute("myCartMembers",session.getAttribute(mb.getMemberEmail()));
            session.setAttribute("myCartTotal",totalPriceMember(cartMembers));
            session.setAttribute("myCartNum",cartMembers.size());
        }
        model.addAttribute("member", new Member());
        model.addAttribute("listCategory",categoryRepository.findAll());
        return "cart";
    }

    @GetMapping("/checkout")
    public String viewCheckOut(Model model, HttpSession session){
        List<Cart> cartItems = (List<Cart>) session.getAttribute("myCartItems");
        Member mb = (Member) session.getAttribute("myLogin");

        if(session.getAttribute("myLogin")==null){
            session.setAttribute("myCartItems",cartItems);
            session.setAttribute("myCartTotal",totalPriceItems(cartItems));
            session.setAttribute("myCartNum",cartItems.size());
        }
        else {
            List<CartMember> cartMembers = (List<CartMember>) session.getAttribute(mb.getMemberEmail());
            session.setAttribute(mb.getMemberEmail(),cartMembers);
            session.setAttribute("myCartMembers",session.getAttribute(mb.getMemberEmail()));
            session.setAttribute("myCartTotal",totalPriceMember(cartMembers));
            session.setAttribute("myCartNum",cartMembers.size());
            model.addAttribute("ifmMember",memberService.findById(mb.getMemberId()));
        }
        model.addAttribute("member", new Member());
        model.addAttribute("receipt",new Receipt());
        model.addAttribute("listCategory",categoryRepository.findAll());
        return "checkout";
    }

    @PostMapping("/checkout")
    public String checkOut(Model model, HttpSession session, @ModelAttribute("receipt")Receipt receipt, @ModelAttribute("member") Member member){
        long uid = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE ;
        List<Cart> cartItems = (List<Cart>) session.getAttribute("myCartItems");
        Member mb = (Member) session.getAttribute("myLogin");
        List<ReceiptItem> receiptItems = new ArrayList<ReceiptItem>();
        receipt.setReceiptId(uid);
        receipt.setReceiptDate(new Timestamp(new Date().getTime()));
        receipt.setReceiptStatus(true);
        if (session.getAttribute("myLogin") == null){
            receiptService.save(receipt);
            for (Cart cart : cartItems){
                receiptItems.add(new ReceiptItem(receipt,
                        cart.getProduct(),cart.getProduct().getProductPrice(),
                        cart.getProduct().getProductSale(),cart.getQuantity(),true
                ));
            }
            // send email
            String from = "nguyentunghh06@gmail.com";
            String nameFrom = "Đặt hàng thành công !";
            String to = receipt.getReceiptEmail();
            String subject = "Chào bạn  " + receipt.getReceiptName() ;
            String body ="Chúc mừng bạn "+ receipt.getReceiptName() + " đã đặt hàng thành công !.Chúc bạn có một ngày mua sắm vui vẻ !";
            // send
            sendEmail(from,nameFrom,to,from,subject,body);
            // --- send email
            receiptItemService.saveAll(receiptItems);
            cartItems.removeAll(cartItems);
            session.setAttribute("myCartItems",cartItems);
            session.setAttribute("myCartTotal",totalPriceItems(cartItems));
            session.setAttribute("myCartNum",cartItems.size());
        }
        else {
            receipt.setReceiptAddress(member.getMemberAddress());
            receipt.setReceiptPhone(member.getMembersPhone());
            receipt.setReceiptName(member.getMemberUser());
            receipt.setReceiptEmail(member.getMemberEmail());
            receipt.setMember(member);
            receiptService.save(receipt);
            List<CartMember> cartMembers = (List<CartMember>) session.getAttribute(mb.getMemberEmail());
            for (CartMember cart : cartMembers){
                receiptItems.add(new ReceiptItem(receipt,
                        cart.getProduct(),cart.getProduct().getProductPrice(),
                        cart.getProduct().getProductSale(),cart.getQuantity(),true
                ));
            }
            // send email
            String from = "nguyentunghh06@gmail.com";
            String nameFrom = "Đặt hàng thành công !";
            String to = member.getMemberEmail();
            String subject = "Chào bạn  " + member.getMemberUser() ;
            String body ="Chúc mừng bạn "+ member.getMemberUser() + " đã đặt hàng thành công !.Chúc bạn có một ngày mua sắm vui vẻ !";
            // send
            sendEmail(from,nameFrom,to,from,subject,body);
            // --- send email
            receiptItemService.saveAll(receiptItems);
            cartMembers.removeAll(cartMembers);
            session.setAttribute(mb.getMemberEmail(),cartItems);
            session.setAttribute("myCartMembers",session.getAttribute(mb.getMemberEmail()));
            session.setAttribute("myCartTotal",totalPriceMember(cartMembers));
            session.setAttribute("myCartNum",cartMembers.size());
        }
        model.addAttribute("member", new Member());
        return "cart";
    }
    public double totalPriceItems(List<Cart> cartItems) {
        int count = 0;
        for (Cart list : cartItems) {
            count += (list.getProduct().getProductPrice()- (list.getProduct().getProductPrice()/100 * list.getProduct().getProductSale())) * list.getQuantity();
        }
        return count;
    }

    public double totalPriceMember(List<CartMember> cartItems) {
        int count = 0;
        for (CartMember list : cartItems) {
            count += (list.getProduct().getProductPrice()- (list.getProduct().getProductPrice()/100 * list.getProduct().getProductSale())) * list.getQuantity();
        }
        return count;
    }

    private int existsItems(Long productId, List<Cart> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getProduct().getProductId().equals(productId)) {
                return i;
            }
        }
        return -1;
    }

    private int existsMember(Long productId, List<CartMember> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getProduct().getProductId().equals(productId)) {
                return i;
            }
        }
        return -1;
    }
    public void sendEmail(String from, String nameFrom, String to, String replyTo, String subject, String body){
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(from,nameFrom);
            helper.setTo(to);
            helper.setReplyTo(replyTo,nameFrom);
            helper.setSubject(subject);
            helper.setText(body);
            emailSender.send(message);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
