package com.example.app.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.app.domain.Member;
import com.example.app.service.MemberService;
import com.example.app.validation.AddGroup;

@Controller
public class RegisterMemberController {

	@Autowired
	private MemberService service;

	@GetMapping("/addMember")
	public String addGet(
			Model model)throws Exception{
		model.addAttribute("member",new Member());
		return "addMember";
	}


	@PostMapping("/addMember")
	public String addPost(
			@Validated(AddGroup.class) Member member,
			Errors errors,
			HttpSession session,
			Model model)throws Exception{
		if(errors.hasErrors()) {
			model.addAttribute("member",member);
			return "addMember";
		}
		if(!member.getPassword().equals(member.getPassword2())) {
			errors.rejectValue("password2", "error.not_match_password");
			return "addMember";
		}

		session.setAttribute("member", member);
		return "addMemberConfirm";

	}

	@GetMapping("/addMember/confirm")
	public String addComfirmGet(
			HttpSession session,
			Model model)throws Exception{
		model.addAttribute("member",session.getAttribute("member"));
		return "addMemberConfirm";
	}
	@PostMapping("/addMember/confirm")
	public String addComfirmPost(
			HttpSession session,
			Model model)throws Exception{

		Member member=(Member) session.getAttribute("member");
		service.addMember(member);
		session.removeAttribute("member");
		return "addMemberDone";
	}
	//編集↓
	@GetMapping("/ticketInventory/editMember")
	public String editGet(
			HttpSession session,
			Model model)throws Exception{
		model.addAttribute("member",
				service.getMemberById((int)session.getAttribute("memberId")));
		return "/ticketInventory/editMember/detail";
	}


	@PostMapping("/ticketInventory/editMember")
	public String editPost(
			@Validated(AddGroup.class) Member member,
			Errors errors,
			HttpSession session,
			Model model)throws Exception{
		if(errors.hasErrors()) {
			model.addAttribute("member",member);
			return "/ticketInventory/editMember/detail";
		}
		if(!member.getPassword().equals(member.getPassword2())) {
			errors.rejectValue("password2", "error.not_match_password");
			return "/ticketInventory/editMember/detail";
		}

		session.setAttribute("member", member);
		return "/ticketInventory/editMember/confirm";

	}

	@GetMapping("/ticketInventory/editMember/confirm")
	public String editComfirmGet(
			HttpSession session,
			Model model)throws Exception{
		model.addAttribute("member",session.getAttribute("member"));
		return "/ticketInventory/editMember/comfirm";
	}

	@PostMapping("/ticketInventory/editMember/confirm")
	public String editComfirmPost(
			HttpSession session,
			Member member,
			Model model)throws Exception{

		member=(Member) session.getAttribute("member");
		service.editMember(member);
		session.removeAttribute("member");
		return "/ticketInventory/editMember/done";
	}


}
