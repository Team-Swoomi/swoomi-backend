package teamc.opgg.swoomi.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import teamc.opgg.swoomi.dto.MailDto;

@Service
@AllArgsConstructor
public class MailService {
    private JavaMailSender mailSender;
    private static final String SENDER_EMAIL = "dnstlr2933@gmail.com";

    public void mailSend(MailDto mailDto) {
        SimpleMailMessage mailMsg = new SimpleMailMessage();
        mailMsg.setFrom(SENDER_EMAIL);
        mailMsg.setTo(mailDto.getTo());
        mailMsg.setSentDate(mailDto.getSentDate());
        mailMsg.setSubject(mailDto.getSubject());
        mailMsg.setText(mailDto.getText());

        mailSender.send(mailMsg);
    }
}
