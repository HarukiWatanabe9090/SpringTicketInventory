package com.example.app.service;

import com.example.app.domain.Mail;
import com.example.app.domain.Member;
import com.example.app.domain.Reserves;


public interface SendMailService {
	Mail reserveDone(Reserves reserves)throws Exception;
	Mail addMemberDone(Member member)throws Exception;

}
