package com.example.app.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.domain.Inventory;
import com.example.app.domain.Mail;
import com.example.app.domain.Member;
import com.example.app.domain.Reserves;
import com.example.app.domain.ReservesForm;
import com.example.app.service.InventoryService;
import com.example.app.service.MemberService;
import com.example.app.service.ReservesService;
import com.example.app.service.SendMailService;

@Controller
@RequestMapping("/ticketInventory/reserve")
public class ReserveController {

	@Autowired
	private ReservesService reservesService;
	@Autowired
	private MemberService memberService;

	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private SendMailService sendMailService;

	@GetMapping
	public String reserveGet(Model model)throws Exception{

		model.addAttribute("title","チケットを予約する");
		model.addAttribute("message","予約");
		model.addAttribute("form",new ReservesForm());
		model.addAttribute("schedulesList",reservesService.getSchedulesList());
		model.addAttribute("typeList",reservesService.getTypeList());
		model.addAttribute("ordersList",reservesService.getOrdersList());
		model.addAttribute("paymentList",reservesService.getPaymentList());
		model.addAttribute("inventoryList",inventoryService.getInventoryList());
		return "/ticketInventory/reserve/ticket";
	}

	@PostMapping
	public String reservePost(
			HttpSession session,
			@Valid @ModelAttribute("form") ReservesForm form,
			Errors errors,
			Model model)throws Exception{

		if(errors.hasErrors()) {
			model.addAttribute("title","チケットを予約する");
			model.addAttribute("message","予約");
			model.addAttribute("schedulesList",reservesService.getSchedulesList());
			model.addAttribute("typeList",reservesService.getTypeList());
			model.addAttribute("ordersList",reservesService.getOrdersList());
			model.addAttribute("paymentList",reservesService.getPaymentList());
			model.addAttribute("inventoryList",inventoryService.getInventoryList());
			return "/ticketInventory/reserve/ticket";
		}
		if (!inventoryService.checkInventory(form.getSchedulesId(), form.getTypeId(), form.getAmount())) {
			errors.rejectValue("amount", "error.sold_out");
			model.addAttribute("title","チケットを予約する");
			model.addAttribute("message","予約");
			model.addAttribute("schedulesList",reservesService.getSchedulesList());
			model.addAttribute("typeList",reservesService.getTypeList());
			model.addAttribute("ordersList",reservesService.getOrdersList());
			model.addAttribute("paymentList",reservesService.getPaymentList());
			model.addAttribute("inventoryList",inventoryService.getInventoryList());
			return "/ticketInventory/reserve/ticket";
		}
		Reserves reserves =reservesService.changeIdToString(form);
		Integer total= reservesService.getTotalPrice(reserves);
		reserves.setTotal(total);
		Integer memberId =(int)session.getAttribute("memberId");
		reserves.setMember(memberService.getMemberById(memberId));

		session.setAttribute("reserves", reserves);
		return "redirect:/ticketInventory/reserve/comfirm";
	}

	@GetMapping("/comfirm")
	public String comfirmGet(HttpSession session,
			Model model)throws Exception{
		model.addAttribute("title","チケットを予約する");
		model.addAttribute("message","予約");
		model.addAttribute("reserves",session.getAttribute("reserves"));
		return "/ticketInventory/reserve/comfirm";
	}

	@PostMapping("/comfirm")
	public String comfirmPost(
			HttpSession session,
			Reserves reserves,
			Model model)throws Exception{
		reserves=(Reserves)session.getAttribute("reserves");

		Inventory inventory=
		inventoryService.getBySchedulesIdAndTypeId(
				reserves.getSchedules().getId(), reserves.getType().getId());

		//予約完了メール↓
		try {
			//DB更新
			inventory.setSheet(inventory.getSheet() - reserves.getAmount());
			inventoryService.update(inventory);
			reservesService.addReserve(reserves);
			//更新エラーがなければメール処理↓
			Mail mail = sendMailService.reserveDone(reserves);
			SimpleEmail email =new SimpleEmail();
			email.setHostName("smtp.gmail.com");
			email.setStartTLSEnabled(true);
			email.setSslSmtpPort("465");
			email.setAuthentication("haruki.watanabe.0119@gmail.com","nvcwmeimdpqascnq" );
			email.setFrom("haruki.watanabe.0119@gmail.com","チケット予約システム", "ISO-2022-JP");

			email.addTo(reserves.getMember().getEmail(),"管理者","ISO-2022-JP");

			email.setCharset("ISO-2022-JP");
			email.setSubject(mail.getSubject());
			email.setMsg(mail.getReport());

			email.send();
		}catch (EmailException e) {
			Integer memberId =(int)session.getAttribute("memberId");
			Member member = memberService.getMemberById(memberId);
			String status;
			if(member.getStatus().equals("admin")) {
				status = "admin";
			}else {
				 status = "member";
			}
			model.addAttribute("status",status);
			return "/ticketInventory/reserve/fail";
		}
		//ここまでメール処理↑

		session.removeAttribute("reserves");
		model.addAttribute("title","チケットを予約する");
		model.addAttribute("message","予約完了メールを送信しました");
		Integer memberId =(int)session.getAttribute("memberId");
		Member member = memberService.getMemberById(memberId);
		String status;
		if(member.getStatus().equals("admin")) {
			status = "admin";
		}else {
			 status = "member";
		}
		model.addAttribute("status",status);
		return "/ticketInventory/reserve/done";
	}

	//変更↓

	@GetMapping("/change")
	public String changeGet(
			HttpSession session,
			Model model)throws Exception{
		Reserves reserves=(Reserves)session.getAttribute("lastReserves");
		model.addAttribute("title","予約を変更する");
		model.addAttribute("message","変更");

		model.addAttribute("form",reservesService.StringToChangeId(reserves));
		model.addAttribute("schedulesList",reservesService.getSchedulesList());
		model.addAttribute("typeList",reservesService.getTypeList());
		model.addAttribute("ordersList",reservesService.getOrdersList());
		model.addAttribute("paymentList",reservesService.getPaymentList());
		model.addAttribute("inventoryList",inventoryService.getInventoryList());

		Inventory inventory=
				inventoryService.getBySchedulesIdAndTypeId(
						reserves.getSchedules().getId(), reserves.getType().getId());
		session.setAttribute("lastInventory", inventory);
		return "ticketInventory/reserve/ticket";
	}

	@PostMapping("/change")
	public String changePost(
			HttpSession session,
			@Valid @ModelAttribute("form") ReservesForm form,
			Errors errors,
			Model model)throws Exception{

		if(errors.hasErrors()) {
			model.addAttribute("title","予約を変更する");
			model.addAttribute("message","変更");
			model.addAttribute("schedulesList",reservesService.getSchedulesList());
			model.addAttribute("typeList",reservesService.getTypeList());
			model.addAttribute("ordersList",reservesService.getOrdersList());
			model.addAttribute("paymentList",reservesService.getPaymentList());
			model.addAttribute("inventoryList",inventoryService.getInventoryList());
			return "ticketInventory/reserve/ticket";
		}
		if (!inventoryService.checkInventory(form.getSchedulesId(), form.getTypeId(), form.getAmount())) {
			errors.rejectValue("amount", "error.sold_out");
			model.addAttribute("title","チケットを予約する");
			model.addAttribute("message","予約");
			model.addAttribute("schedulesList",reservesService.getSchedulesList());
			model.addAttribute("typeList",reservesService.getTypeList());
			model.addAttribute("ordersList",reservesService.getOrdersList());
			model.addAttribute("paymentList",reservesService.getPaymentList());
			model.addAttribute("inventoryList",inventoryService.getInventoryList());
			return "/ticketInventory/reserve/ticket";
		}
		Reserves reserves=(Reserves)session.getAttribute("lastReserves");
		Reserves newReserves =reservesService.changeIdToString(form);
		Integer total= reservesService.getTotalPrice(newReserves);
		newReserves.setTotal(total);
		newReserves.setId(reserves.getId());
		newReserves.setMember(reserves.getMember());
		newReserves.setMember(reserves.getMember());

		session.setAttribute("newReserves", newReserves);
		return "redirect:/ticketInventory/reserve/change/comfirm";
	}

	@GetMapping("/change/comfirm")
	public String chgComfirmGet(HttpSession session,
			Model model)throws Exception{
		model.addAttribute("title","予約を変更する");
		model.addAttribute("message","変更");

		model.addAttribute("reserves",session.getAttribute("newReserves"));
		return "/ticketInventory/reserve/comfirm";
	}

	@PostMapping("/change/comfirm")
	public String chgComfirmPost(
			HttpSession session,
			Model model)throws Exception{
		Integer memberId =(int)session.getAttribute("memberId");
		Member member = memberService.getMemberById(memberId);
		Reserves newReserves=(Reserves)session.getAttribute("newReserves");
		Reserves lastReserves=(Reserves)session.getAttribute("lastReserves");
		Inventory lastInventory=(Inventory)session.getAttribute("lastInventory");
		Inventory inventory=
				inventoryService.getBySchedulesIdAndTypeId(
						newReserves.getSchedules().getId(), newReserves.getType().getId());

		//予約完了メール↓
				try {
					//DB更新
					lastInventory.setSheet(lastInventory.getSheet()+lastReserves.getAmount());
					inventoryService.update(lastInventory);
					inventory.setSheet(inventory.getSheet() - newReserves.getAmount());
					inventoryService.update(inventory);
					reservesService.editReserve(newReserves);
					//更新エラーがなければメール処理↓
					Mail mail = sendMailService.reserveDone(newReserves);
					SimpleEmail email =new SimpleEmail();
					email.setHostName("smtp.gmail.com");
					email.setStartTLSEnabled(true);
					email.setSslSmtpPort("465");
					email.setAuthentication("haruki.watanabe.0119@gmail.com","nvcwmeimdpqascnq" );
					email.setFrom("haruki.watanabe.0119@gmail.com","チケット予約システム", "ISO-2022-JP");

					email.addTo(member.getEmail(),"管理者","ISO-2022-JP");

					email.setCharset("ISO-2022-JP");
					email.setSubject(mail.getSubject());
					email.setMsg(mail.getReport());

					email.send();
				}catch (EmailException e) {
					String status;
					if(member.getStatus().equals("admin")) {
						status = "admin";
					}else {
						 status = "member";
					}
					model.addAttribute("status",status);
					return "/ticketInventory/reserve/fail";
				}
				//ここまでメール処理↑

		session.removeAttribute("newReserves");
		session.removeAttribute("lastReserves");
		session.removeAttribute("lastInventory");
		model.addAttribute("title","予約を変更する");
		model.addAttribute("message","変更しました");

		String status;
		if(member.getStatus().equals("admin")) {
			status = "admin";
		}else {
			 status = "member";
		}
		model.addAttribute("status",status);
		return "/ticketInventory/reserve/done";
	}

	//キャンセル↓
	@GetMapping("/cancell")
	public String cancellGet(HttpSession session,Model model)
			throws Exception{
		model.addAttribute("reserves",session.getAttribute("cancellReserves"));
		return "/ticketInventory/reserve/cancell";
	}

	@PostMapping("/cancell")
	public String cancellPost(HttpSession session,
			Model model)throws Exception{
		Reserves cancellReserves=(Reserves)session.getAttribute("cancellReserves");
		Inventory inventory=inventoryService.getBySchedulesIdAndTypeId(
			cancellReserves.getSchedules().getId(), cancellReserves.getType().getId());
		inventory.setSheet(inventory.getSheet() + cancellReserves.getAmount());
		inventoryService.update(inventory);

		reservesService.deleteReserve(cancellReserves);
		session.removeAttribute("cancellReserves");
		model.addAttribute("title","予約を取り消す");
		model.addAttribute("message","取り消しました");
		Integer memberId =(int)session.getAttribute("memberId");
		Member member = memberService.getMemberById(memberId);
		String status;
		if(member.getStatus().equals("admin")) {
			status = "admin";
		}
		else {
			 status = "member";
		}
		model.addAttribute("status",status);

		return "/ticketInventory/reserve/done";
	}
}
