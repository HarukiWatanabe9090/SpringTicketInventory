package com.example.app.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.domain.Orders;
import com.example.app.domain.Payment;
import com.example.app.domain.Schedules;
import com.example.app.domain.Type;
import com.example.app.service.SettingService;

@Controller
@RequestMapping("/ticketInventory/admin/setting")
public class SettingController {

	@Autowired
	private SettingService service;

	@GetMapping
	public String list(
			HttpSession session,
			Model model) throws Exception {
		if (!session.getAttribute("status").equals("admin")) {
			return "redirect:/";
		} else {
			model.addAttribute("schedulesList", service.getSchedulesList());
			model.addAttribute("typeList", service.getTypeList());
			model.addAttribute("paymentList", service.getPaymentList());
			model.addAttribute("ordersList", service.getOrdersList());
			return "/ticketInventory/admin/setting/home";
		}
	}

	@PostMapping
	public String list(
			@RequestParam(name = "setting", required = false) Integer setting,
			Model model) throws Exception {
		switch (setting) {
		case 0:
			return "redirect:/ticketInventory/admin/setting/schedules";

		case 1:
			return "redirect:/ticketInventory/admin/setting/type";

		case 2:
			return "redirect:/ticketInventory/admin/setting/payment";

		case 3:
			return "redirect:/ticketInventory/admin/setting/orders";
		}
		return "redirect:/ticketInventory/admin/setting/home";
	}

	@GetMapping("/schedules")
	public String getScheSetting(
			HttpSession session,
			Model model)throws Exception{
		model.addAttribute("schedulesList", service.getSchedulesList());
		model.addAttribute("addSchedules",new Schedules());
		return "/ticketInventory/admin/setting/schedules";

	}
	@PostMapping("/schedules")
	public String postScheSetting(
			@RequestParam(name="editId" ,required=false)Integer editId,
			@RequestParam(name="editSchedule" ,required=false)String editSchedule,
			@RequestParam(name="deleteId" ,required=false)Integer deleteId,
			@RequestParam(name="endId",required=false)Integer endId,
			@ModelAttribute("addSchedules")Schedules addSchedules,
			Model model)throws Exception{

		if (editSchedule != null) {
			Schedules editSchedules =service.getScheduleById(editId);
			editSchedules.setSchedule(editSchedule);
			service.editSchedule(editSchedules);
			return "redirect:/ticketInventory/admin/setting/schedules";
		}
		if (deleteId != null) {
			service.deleteSchedule(deleteId);
			return "redirect:/ticketInventory/admin/setting/schedules";
		}
		if (endId != null) {
			service.endSchedule(endId);
			return "redirect:/ticketInventory/admin/setting/schedules";
		}
		if (addSchedules != null) {
			service.addSchedule(addSchedules);
			return "redirect:/ticketInventory/admin/setting/schedules";
		}
		return "redirect:/ticketInventory/admin/setting/schedules";
	}

	@GetMapping("/orders")
	public String getOrdersSetting(
			HttpSession session,
			Model model)throws Exception{
		model.addAttribute("ordersList", service.getOrdersList());
		model.addAttribute("addOrders",new Orders());
		return "/ticketInventory/admin/setting/orders";

	}
	@PostMapping("/orders")
	public String postOrdersSetting(
			@RequestParam(name="editId" ,required=false)Integer editId,
			@RequestParam(name="editName" ,required=false)String editName,
			@RequestParam(name="deleteId" ,required=false)Integer deleteId,
			@ModelAttribute("addOrders")Orders addOrders,
			Model model)throws Exception{

		if (editName != null) {
			Orders editOrders =service.getOrdersById(editId);
			editOrders.setName(editName);
			service.editOrders(editOrders);;
			return "redirect:/ticketInventory/admin/setting/orders";
		}
		if (deleteId != null) {
			service.deleteOrders(deleteId);
			return "redirect:/ticketInventory/admin/setting/orders";
		}
		if (addOrders != null) {
			service.addOrders(addOrders);
			return "redirect:/ticketInventory/admin/setting/orders";
		}
		return "redirect:/ticketInventory/admin/setting/orders";
	}

	@GetMapping("/payment")
	public String getPaymentSetting(
			HttpSession session,
			Model model)throws Exception{
		model.addAttribute("paymentList", service.getPaymentList());
		model.addAttribute("addPayment",new Payment());
		return "/ticketInventory/admin/setting/payment";

	}
	@PostMapping("/payment")
	public String postPaymentSetting(
			@RequestParam(name="editId" ,required=false)Integer editId,
			@RequestParam(name="editWay" ,required=false)String editWay,
			@RequestParam(name="deleteId" ,required=false)Integer deleteId,
			@ModelAttribute("addPayment")Payment addPayment,
			Model model)throws Exception{

		if (editWay != null) {
			Payment editPayment =service.getPaymentById(editId);
			editPayment.setWay(editWay);
			service.editPayment(editPayment);;
			return "redirect:/ticketInventory/admin/setting/payment";
		}
		if (deleteId != null) {
			service.deletePayment(deleteId);
			return "redirect:/ticketInventory/admin/setting/payment";
		}
		if (addPayment != null) {
			service.addPayment(addPayment);
			return "redirect:/ticketInventory/admin/setting/payment";
		}
		return "redirect:/ticketInventory/admin/setting/payment";
	}

	@GetMapping("/type")
	public String getTypeSetting(
			HttpSession session,
			Model model)throws Exception{
		model.addAttribute("typeList", service.getTypeList());
		model.addAttribute("addType",new Type());
		return "/ticketInventory/admin/setting/type";

	}
	@PostMapping("/type")
	public String postTypeSetting(
			@RequestParam(name="editId" ,required=false)Integer editId,
			@RequestParam(name="editName" ,required=false)String editName,
			@RequestParam(name="editPrice" ,required=false)Integer editPrice,
			@RequestParam(name="deleteId" ,required=false)Integer deleteId,
			@ModelAttribute("addType")Type addType,
			Model model)throws Exception{

		if (editName != null) {
			Type editType = service.getTypeById(editId);
			editType.setName(editName);
			editType.setPrice(editPrice);
			service.editType(editType);
			return "redirect:/ticketInventory/admin/setting/type";
		}
		if (deleteId != null) {
			service.deleteType(deleteId);
			return "redirect:/ticketInventory/admin/setting/type";
		}
		if (addType != null) {
			service.addType(addType);
			return "redirect:/ticketInventory/admin/setting/type";
		}
		return "redirect:/ticketInventory/admin/setting/type";
	}
}
