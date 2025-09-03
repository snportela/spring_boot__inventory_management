package vo.project.inventory.security.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${spring.mail.username}")
    private String mailUsername;

    public void sendEmail(String email, String subject, String body) {
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(email);
        mail.setFrom(mailUsername);
        mail.setSubject(subject);
        mail.setText(body);

        mailSender.send(mail);
    }
}
