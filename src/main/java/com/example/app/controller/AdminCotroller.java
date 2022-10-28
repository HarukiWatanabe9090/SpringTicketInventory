package com.example.app.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.domain.Inventory;
import com.example.app.domain.Member;
import com.example.app.domain.Orders;
import com.example.app.domain.Progress;
import com.example.app.domain.Reserves;
import com.example.app.domain.Schedules;
import com.example.app.service.InventoryService;
import com.example.app.service.MemberService;
import com.example.app.service.ReservesService;
import com.example.app.service.SettingService;

@Controller
@RequestMapping("/ticketInventory/admin")
public class AdminCotroller {

	private static final int NUM_PER_PAGE = 20;

	@Autowired
	private ReservesService reservesService;

	@Autowired
	private SettingService settingService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private InventoryService inventoryService;

	@GetMapping
	public String list(
			HttpSession session,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			Model model) throws Exception {
		session.removeAttribute("schedulesId");
		Integer memberId = (int) session.getAttribute("memberId");
		model.addAttribute("member", memberService.getMemberById(memberId));
		model.addAttribute("reservesList", reservesService.getReserveListByPage(page, NUM_PER_PAGE));
		model.addAttribute("page", page);
		model.addAttribute("totalPages", reservesService.getTotalPages(NUM_PER_PAGE));
		return "ticketInventory/admin/list";
	}

	@PostMapping
	public String changeOrCancell(
			HttpSession session,
			@RequestParam(name = "changeId", required = false) Integer changeId,
			@RequestParam(name = "cancellId", required = false) Integer cancellId,
			Model model) throws Exception {
		if (changeId != null) {
			Reserves changeReserves = reservesService.getById(changeId);
			session.setAttribute("lastReserves", changeReserves);
			return "redirect:/ticketInventory/reserve/change";
		}
		if (cancellId != null) {
			Reserves cancellReserves = reservesService.getById(cancellId);
			session.setAttribute("cancellReserves", cancellReserves);
			return "redirect:/ticketInventory/reserve/cancell";
		} else {
			return "ticketInventory/admin";
		}
	}

	//会員一覧↓
	@GetMapping("/memberList")
	public String memberList(
			HttpSession session,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			Model model) throws Exception {
		if (!session.getAttribute("status").equals("admin")) {
			return "redirect:/";
		} else {
			session.removeAttribute("reserves");
			session.removeAttribute("reservationChange");

			model.addAttribute("memberList", memberService.getMemberListByPage(page, NUM_PER_PAGE));
			model.addAttribute("page", page);
			model.addAttribute("totalPages", memberService.getTotalPages(NUM_PER_PAGE));
			model.addAttribute("ordersList", reservesService.getOrdersList());

			return "/ticketInventory/admin/memberList";
		}
	}

	@PostMapping("/memberList")
	public String linkOrders(
			HttpSession session,
			@RequestParam(name = "ordersId", required = false) Integer ordersId,
			@RequestParam(name = "memberId", required = false) Integer memberId,
			@RequestParam(name = "resetId", required = false) Integer resetId,
			Model model) throws Exception {

		if (ordersId != null) {
			Orders orders = settingService.getOrdersById(ordersId);
			Member member = memberService.getMemberById(memberId);
			member.setOrders(orders);
			memberService.linkOrder(member);
			return "redirect:/ticketInventory/admin/memberList";
		}
		if (resetId != null) {
			memberService.resetOrder(resetId);
			return "redirect:/ticketInventory/admin/memberList";
		}
		return "/ticketInventory/admin/memberList";

	}

	//売り上げ状況↓
	@GetMapping("/sales")
	public String sales(
			HttpSession session,
			Model model) throws Exception {
		if (!session.getAttribute("status").equals("admin")) {
			return "redirect:/";
		} else {
			session.removeAttribute("reserves");
			session.removeAttribute("reservationChange");

			model.addAttribute("reserveList", reservesService.getEachCount());
			return "/ticketInventory/admin/sales";
		}
	}

	//残席数管理↓
	@GetMapping("/inventoryList")
	public String inventoryList(
			HttpSession session,
			Model model) throws Exception {
		if (!session.getAttribute("status").equals("admin")) {
			return "redirect:/";
		} else {
//			model.addAttribute("inventory", new Inventory());
			model.addAttribute("inventoryList", inventoryService.getInventoryList());
			model.addAttribute("addInventory", new Inventory());
			model.addAttribute("schedulesList", reservesService.getSchedulesList());
			model.addAttribute("typeList", reservesService.getTypeList());

			return "/ticketInventory/admin/inventoryList";
		}
	}

	@PostMapping("/inventoryList")
	public String editInventory(
			HttpSession session,
			@RequestParam(name = "id", required = false) Integer id,
			@RequestParam(name = "sheet", required = false) Integer sheet,
			@RequestParam(name = "deleteId", required = false) Integer deleteId,
			@Valid @ModelAttribute("addInventory") Inventory addInventory,
			Errors errors,
			Model model) throws Exception {
		if (id != null) {
			//二次開発で追加↓
			if (sheet != null) {
				if (sheet >= 0 && sheet <= 9999) {
			//ここまで↑
					Inventory inventory = inventoryService.getInventoryById(id);
					inventory.setSheet(sheet);
					inventoryService.update(inventory);
					return "redirect:/ticketInventory/admin/inventoryList";
			//二次開発で追加↓
				}
			}
			else {
				return "redirect:/ticketInventory/admin/inventoryList";
			}
			//ここまで↑
		}
		if (deleteId != null) {
			inventoryService.delete(deleteId);
			return "redirect:/ticketInventory/admin/inventoryList";
		}

		if (addInventory != null) {
			//二次開発で追加↓
			if (errors.hasErrors()) {
//				model.addAttribute("inventory", new Inventory());
				model.addAttribute("inventoryList", inventoryService.getInventoryList());
				model.addAttribute("addInventory", addInventory);
				model.addAttribute("schedulesList", reservesService.getSchedulesList());
				model.addAttribute("typeList", reservesService.getTypeList());
				return "/ticketInventory/admin/inventoryList";
			}
			//ここまで↑
			inventoryService.add(addInventory);
			return "redirect:/ticketInventory/admin/inventoryList";
		}

		return "redirect:/ticketInventory/admin/inventoryList";
	}

	//来場者管理↓
	@GetMapping("/visitedList")
	public String visitedList(
			HttpSession session,
			Model model) throws Exception {
		if (!session.getAttribute("status").equals("admin")) {
			return "redirect:/";
		}
		Integer schedulesId = (Integer) session.getAttribute("schedulesId");
		if (schedulesId != null) {
			model.addAttribute("reservesList", reservesService.getReserveListBySchedules(schedulesId));
			model.addAttribute("progress", reservesService.getProgress(schedulesId));
			model.addAttribute("schedulesList", settingService.getSchedulesList());
			model.addAttribute("schedules", new Schedules());
			return "/ticketInventory/admin/visitedList";
		} else {
			model.addAttribute("reservesList", reservesService.getAllReserves());
			model.addAttribute("schedulesList", settingService.getSchedulesList());
			model.addAttribute("schedules", new Schedules());
			model.addAttribute("progress", new Progress());
			return "/ticketInventory/admin/visitedList";
		}
	}

	@PostMapping("/visitedList")
	public String editVisited(
			HttpSession session,
			@ModelAttribute("schedules") Schedules schedules,
			@RequestParam(name = "visitedId", required = false) Integer visitedId,
			@RequestParam(name = "notVisitedId", required = false) Integer notVisitedId,
			@RequestParam(name = "paidId", required = false) Integer paidId,
			@RequestParam(name = "notPaidId", required = false) Integer notPaidId,
			Model model) throws Exception {
		if (visitedId != null) {
			reservesService.visitedReserve(visitedId);
			return "redirect:/ticketInventory/admin/visitedList";
		}
		if (notVisitedId != null) {
			reservesService.notVisitedReserve(notVisitedId);
			return "redirect:/ticketInventory/admin/visitedList";
		}
		if (paidId != null) {
			reservesService.paidReserve(paidId);
			return "redirect:/ticketInventory/admin/visitedList";
		}
		if (notPaidId != null) {
			reservesService.notPaidReserve(notPaidId);
			return "redirect:/ticketInventory/admin/visitedList";
		}
		if (schedules != null) {
			session.setAttribute("schedulesId", schedules.getId());
			return "redirect:/ticketInventory/admin/visitedList";
		}

		return "redirect:/";
	}

}
