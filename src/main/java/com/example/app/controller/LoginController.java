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
import com.example.app.validation.LoginGroup;

@Controller
public class LoginController {

	@Autowired
	private MemberService service;

	@GetMapping
	public String showLogin(HttpSession session,Model model) {
		session.invalidate();
		model.addAttribute("member", new Member());
		return "login";
	}

	@PostMapping
	public String login(
			@Validated(LoginGroup.class) Member member,
			Errors errors,
			HttpSession session) throws Exception {


		if (errors.hasErrors()) {
			return "login";
		}

		if (!service.isCorrectEmailAndPassword(member.getEmail(), member.getPassword())) {
			errors.rejectValue("email", "error.incorrect_id_password");
			return "login";
		}
		member = service.getMemberByEmail(member.getEmail());
		session.setAttribute("memberId", member.getId());

		if (member.getStatus().equals("member")) {
			session.setAttribute("status", member.getStatus());
			return "redirect:/ticketInventory/member";
		}
			else if (member.getStatus().equals("staff")) {
			session.setAttribute("status", member.getStatus());
			return "redirect:/ticketInventory/member";
		}
			else if (member.getStatus().equals("admin")) {
			session.setAttribute("status", member.getStatus());
			return "redirect:/ticketInventory/admin";
		}
		else {
			return "redirect:/";
		}
	}

	@GetMapping("/ticketInventory/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "/logoutDone";
	}

}
