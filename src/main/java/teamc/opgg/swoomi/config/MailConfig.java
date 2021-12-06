//package teamc.opgg.swoomi.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//import java.util.Properties;
//
//@Profile("local")
//@Configuration
//public class MailConfig {
//
//    @Value("${email.password}")
//    private String password;
//
//    @Bean
//    public JavaMailSender javaMailSender() {
//        JavaMailSenderImpl javaMailSender =  new JavaMailSenderImpl();
//
//        Properties props = new Properties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
//        props.put("mail.smtp.ssl.enable", "true");
//
//        javaMailSender.setHost("smtp.gmail.com");
//        javaMailSender.setPort(587);
//        javaMailSender.setUsername("swoomi21@gmail.com");
//        javaMailSender.setPassword(password);
//        javaMailSender.setJavaMailProperties(props);
//
//        return javaMailSender;
//    }
//}
