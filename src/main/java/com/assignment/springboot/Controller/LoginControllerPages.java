package com.assignment.springboot.Controller;

import com.assignment.springboot.Model.Cart;
import com.assignment.springboot.Model.CartMember;
import com.assignment.springboot.Model.Member;
import com.assignment.springboot.Repository.CategoryRepository;
import com.assignment.springboot.Service.MemberService;
import com.assignment.springboot.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class LoginControllerPages {

    @Autowired
    private MemberService memberService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @PostMapping("/sign-up")
    public String doSignUp(Model model,@ModelAttribute("member") Member member,HttpSession session) {
        List<Member> members = memberService.findAll();
        for (Member mbe: members) {
            if(member.getMemberEmail().equalsIgnoreCase(mbe.getMemberEmail())){
                return "redirect:#";
            }
        }
        member.setMemberStatus(true);
        String password = passwordEncoder.encode(member.getMemberPassword());
        member.setMemberPassword(password);
        memberService.save(member);
        List<Cart> cartsItems  = (List<Cart>) session.getAttribute("myCartItems");
        Member mb = memberService.loginMember(member.getMemberEmail());
        if(mb == null){
            return "redirect:#";
        }
        else {
            session.setAttribute("myLogin",mb);
            if (session.getAttribute(mb.getMemberEmail())==null){
                List<CartMember> cartMembers = new ArrayList<>();
                if(cartsItems != null){
                    for(Cart cart :cartsItems){
                        cartMembers.add(new CartMember(cart.getProduct(),cart.getQuantity()));
                    }
                    cartsItems.removeAll(cartsItems);
                }
                session.setAttribute(mb.getMemberUser(), cartMembers);
                session.setAttribute("myCartMembers", session.getAttribute(mb.getMemberEmail()));
                session.setAttribute("myCartTotal",totalPriceMember(cartMembers));
                session.setAttribute("myCartNum",cartMembers.size());
            }
            else {

                List<CartMember> cartMembers = (List<CartMember>) session.getAttribute(mb.getMemberEmail());
                if(cartsItems != null){
                    for(Cart cart :cartsItems){
                        int index = existsMember(cart.getProduct().getProductId(),cartMembers);
                        try {
                            if (index != -1){
                                if(cart.getProduct().getProductId()== cartMembers.get(index).getProduct().getProductId()){
                                    cartMembers.get(index).setQuantity(cart.getQuantity()+cartMembers.get(index).getQuantity());
                                }
                            }
                            else {
                                cartMembers.add(new CartMember(cart.getProduct(),cart.getQuantity()));
                            }
                        }
                        catch (Exception e){
                            System.out.println(e);
                        }
                    }
                    cartsItems.removeAll(cartsItems);
                }
                session.setAttribute(mb.getMemberEmail(), cartMembers);
                session.setAttribute("myCartMembers", session.getAttribute(mb.getMemberEmail()));
                session.setAttribute("myCartTotal",totalPriceMember(cartMembers));
                session.setAttribute("myCartNum",cartMembers.size());
            }
        }
        // Create a Simple MailMessage.
        String from = "nguyentunghh06@gmail.com";
        String nameFrom = "Elite Shoppy";
        String to = member.getMemberEmail();
        String subject = "Đăng kí tài khoản " + member.getMemberUser() ;
        String body ="Chúc mừng bạn "+ member.getMemberUser() + " đã kích hoạt thành công tài khoản với email " + member.getMemberEmail() +
                " .Chúc bạn mua hàng vui vẻ !";
        sendEmail(from,nameFrom,to,from,subject,body);
        model.addAttribute("member", new Member());
        model.addAttribute("listCategory", categoryRepository.findAll());
        model.addAttribute("listProductFeatured",productService.getListFeatured(4));
        model.addAttribute("listProductSale",productService.getListSale(4));
        return "index";
    }

    @PostMapping("/login-member")
    public String doLogin(Model model, HttpSession session, @ModelAttribute("Member")Member member){
        List<Cart> cartsItems  = (List<Cart>) session.getAttribute("myCartItems");
        List<Member> members = memberService.findAll();
        Member mb = null;
        boolean match = false;
        for (Member mbe: members) {
            if(member.getMemberUser().equalsIgnoreCase(mbe.getMemberEmail())){
                match = BCrypt.checkpw(member.getMemberPassword(),mbe.getMemberPassword());
                if(match==true){
                    mb = memberService.loginMember(member.getMemberUser());
                }
            }
        }

        if(mb == null){
            return "redirect:#";
        }
        else {
            session.setAttribute("myLogin",mb);
            if (session.getAttribute(mb.getMemberEmail())==null){
                List<CartMember> cartMembers = new ArrayList<>();
                if(cartsItems != null){
                    for(Cart cart :cartsItems){
                        cartMembers.add(new CartMember(cart.getProduct(),cart.getQuantity()));
                    }
                    cartsItems.removeAll(cartsItems);
                }
                session.setAttribute(mb.getMemberEmail(), cartMembers);
                session.setAttribute("myCartMembers", session.getAttribute(mb.getMemberEmail()));
                session.setAttribute("myCartTotal",totalPriceMember(cartMembers));
                session.setAttribute("myCartNum",cartMembers.size());
            }
            else {

                List<CartMember> cartMembers = (List<CartMember>) session.getAttribute(mb.getMemberEmail());
                if(cartsItems != null){
                    for(Cart cart :cartsItems){
                        int index = existsMember(cart.getProduct().getProductId(),cartMembers);
                        System.out.println(index);
                        try{
                            if (index != -1){
                                if(cart.getProduct().getProductId().equals(cartMembers.get(index).getProduct().getProductId())){
                                    cartMembers.get(index).setQuantity(cart.getQuantity()+cartMembers.get(index).getQuantity());
                                }
                            }
                            else {
                                cartMembers.add(new CartMember(cart.getProduct(),cart.getQuantity()));
                            }
                        }
                        catch (Exception e){
                            System.out.println(e);
                        }
                    }
                    cartsItems.removeAll(cartsItems);
                }
                session.setAttribute(mb.getMemberEmail(), cartMembers);
                session.setAttribute("myCartMembers", session.getAttribute(mb.getMemberEmail()));
                session.setAttribute("myCartTotal",totalPriceMember(cartMembers));
                session.setAttribute("myCartNum",cartMembers.size());
                }
            }
            model.addAttribute("member", new Member());
            model.addAttribute("listCategory", categoryRepository.findAll());
            model.addAttribute("listProductFeatured",productService.getListFeatured(4));
            model.addAttribute("listProductSale",productService.getListSale(4));
            return "index";
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpSession session){
        session.setAttribute("myLogin",null);
        session.setAttribute("myCartItems", null);
        session.setAttribute("myCartNum",0);
        session.setAttribute("myCartTotal",0);
        model.addAttribute("member", new Member());
        model.addAttribute("listCategory", categoryRepository.findAll());
        model.addAttribute("listProductFeatured",productService.getListFeatured(4));
        model.addAttribute("listProductSale",productService.getListSale(4));
        return "index";
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


    private int existsMember(Long productId, List<CartMember> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getProduct().getProductId().equals(productId)) {
                return i;
            }
        }
        return -1;
    }


    public double totalPriceMember(List<CartMember> cartItems) {
        int count = 0;
        for (CartMember list : cartItems) {
            count += (list.getProduct().getProductPrice()- (list.getProduct().getProductPrice()/100 * list.getProduct().getProductSale())) * list.getQuantity();
        }
        return count;
    }
}
