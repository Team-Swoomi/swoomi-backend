//package teamc.opgg.swoomi.service;
//
//import lombok.AllArgsConstructor;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//import teamc.opgg.swoomi.dto.MailDto;
//
//@Service
//@AllArgsConstructor
//public class MailService {
//    private JavaMailSender mailSender;
//
//    public void mailSend(MailDto mailDto) {
//        SimpleMailMessage mailMsg = new SimpleMailMessage();
//        mailMsg.setTo(mailDto.getTo());
//        mailMsg.setSentDate(mailDto.getSentDate());
//        mailMsg.setSubject(mailDto.getSubject());
//        mailMsg.setText(mailDto.getText());
//
//        mailSender.send(mailMsg);
//    }
//}
