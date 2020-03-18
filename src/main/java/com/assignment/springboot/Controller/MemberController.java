package com.assignment.springboot.Controller;

import com.assignment.springboot.Model.Member;
import com.assignment.springboot.Model.ReceiptItem;
import com.assignment.springboot.Repository.CategoryRepository;
import com.assignment.springboot.Service.MemberService;
import com.assignment.springboot.Service.ReceiptItemService;
import com.assignment.springboot.Service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("member/")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private ReceiptItemService receiptItemService;


    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping("account")
    public String informationMember(Model model){
        model.addAttribute("member",new Member());
        model.addAttribute("listCategory", categoryRepository.findAll());
        return "account";
    }
    @GetMapping("update-account/{memberId}")
    public String viewAccount(@PathVariable("memberId") Long id, Model model){
        model.addAttribute("member",new Member());
        model.addAttribute("account",memberService.findById(id));
        model.addAttribute("listCategory", categoryRepository.findAll());
        return "update-account";
    }

    @PostMapping("update-account/{memberId}")
    public String updateAccount(@PathVariable("memberId") Long id, Model model, @ModelAttribute("Member") Member member, HttpSession session){
        member.setMemberPassword(member.getMemberPassword());
        member.setMemberStatus(true);
        member.setMemberId(id);
        memberService.update(member);
        session.setAttribute("myLogin",member);
        model.addAttribute("member",new Member());
        model.addAttribute("listCategory", categoryRepository.findAll());
        return "account";
    }

    @GetMapping("receipt-account/{memberId}")
    public String viewReceipt(@PathVariable("memberId") Long id, Model model){
        model.addAttribute("member",new Member());
        model.addAttribute("listCategory", categoryRepository.findAll());
        model.addAttribute("receiptAccount",receiptService.fillAllReceiptByMemberId(id));
        return "receipt-account";
    }

    @GetMapping("receipt-item-account/{receiptId}")
    public String viewReceiptItems(@PathVariable("receiptId") Long id,Model model){
        model.addAttribute("receiptItems",receiptItemService.fillAllByReceiptId(id));
        model.addAttribute("member",new Member());
        model.addAttribute("listCategory", categoryRepository.findAll());
        return "receipt-item-account";
    }

    @GetMapping("remove-item-account/{receiptItemId}/{receiptId}")
    public String removeItemAccount(@PathVariable("receiptItemId") Long receiptItemId,@PathVariable("receiptId") Long receiptId, Model model, HttpSession session){
        Member member =(Member) session.getAttribute("myLogin");
        receiptItemService.remove(receiptItemId);
        List<ReceiptItem> checkItem = receiptItemService.fillAllByReceiptId(receiptId);
        if (checkItem.isEmpty()){
            receiptService.remove(receiptId);
            return "redirect:/member/receipt-account/"+member.getMemberId();
        }
        else {
           return "redirect:/member/receipt-item-account/"+receiptId;
        }
    }


}
