package com.example.app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.Mail;
import com.example.app.domain.Member;
import com.example.app.domain.Reserves;

@Service
@Transactional
public class SendMailServiceImpl implements SendMailService {

	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	@Override
	public Mail reserveDone(Reserves reserves) throws Exception {
		Mail mail = new Mail();
		String subject = "予約が完了しました[" + reserves.getMember().getFurigana() + "様 ]";
		String report =
		reserves.getMember().getFurigana() + "様"+ LINE_SEPARATOR + LINE_SEPARATOR
		+"この度は『劇団名』「公演名」にご予約いただきましてありがとうございます。"+LINE_SEPARATOR
		+ "ご予約内容"+ LINE_SEPARATOR
		+ "ご予約名：" +reserves.getMember().getFurigana() + "様" +LINE_SEPARATOR
		+ "開演："+reserves.getSchedules().getSchedule() +LINE_SEPARATOR
		+ "※開場は開演30分前となります。"+LINE_SEPARATOR
		+ "券種/枚数："+reserves.getType().getName() +" / "+ reserves.getAmount() +"枚"+LINE_SEPARATOR
		+ "合計："+reserves.getTotal() +"円"+LINE_SEPARATOR
		+ "お支払方法："+reserves.getPayment().getWay() +LINE_SEPARATOR
		+"【受付について】～～～～～"+LINE_SEPARATOR
		+"【上演について】～～～～～"+LINE_SEPARATOR
		+"【その他】～～～～～"+LINE_SEPARATOR
		+ "それではご来場を心よりお待ちしております。";

		mail.setSubject(subject);
		mail.setReport(report);
		return mail;
	}

	@Override
	public Mail addMemberDone(Member member) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
