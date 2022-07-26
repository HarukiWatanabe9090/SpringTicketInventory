package com.example.app.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.domain.Member;
import com.example.app.domain.Reserves;
import com.example.app.service.MemberService;
import com.example.app.service.ReservesService;

@Controller
@RequestMapping("/ticketInventory/member")
public class MemberController {

	private static final int NUM_PER_PAGE = 20;

	@Autowired
	private ReservesService reservesService;

	@Autowired
	private MemberService memberService;

	@GetMapping
	public String list(
			HttpSession session,
			@RequestParam(name="page",defaultValue="1")Integer page,
			Model model) throws Exception{
		String status =(String)session.getAttribute("status");
		Integer memberId =(int)session.getAttribute("memberId");
		Member member = memberService.getMemberById(memberId);


		if(status == null) {
			model.addAttribute("member", member);
			model.addAttribute("reservesList", reservesService.getMemberReserves(memberId));
			model.addAttribute("page", page);
			model.addAttribute("totalPages", reservesService.getTotalPages(NUM_PER_PAGE));

			return "ticketInventory/member";
		}
		if(status.equals("staff")) {
			model.addAttribute("staff", member);
			Integer ordersId = member.getOrders().getId();
			model.addAttribute("reservesList",reservesService.getReserveOrdersListByPage(ordersId, page, NUM_PER_PAGE));
			model.addAttribute("page", page);
			model.addAttribute("totalPages", reservesService.getTotalPagesByOrders(ordersId,NUM_PER_PAGE));

			return "ticketInventory/staff";
		}
		else {
			return "redirect:/";
		}

	}
	@PostMapping
	public String changeOrCancell(
			HttpSession session,
			@RequestParam(name="changeId",required=false)Integer changeId,
			@RequestParam(name="cancellId",required=false)Integer cancellId,
			Model model
			)throws Exception{
		if(changeId != null) {
			Reserves changeReserves = reservesService.getById(changeId);
			session.setAttribute("lastReserves", changeReserves);
			return "redirect:/ticketInventory/reserve/change";
		}
		if(cancellId != null) {
			Reserves cancellReserves = reservesService.getById(cancellId);
			session.setAttribute("cancellReserves",cancellReserves);
			return "redirect:/ticketInventory/reserve/cancell";
		}
		else {
			return "ticketInventory/member";
		}
	}

}
