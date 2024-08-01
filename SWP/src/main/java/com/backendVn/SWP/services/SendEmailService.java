package com.backendVn.SWP.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.random.RandomGenerator;

@Component
public class SendEmailService {

    @Autowired
    private JavaMailSender emailSender;

    @NonFinal
    @Value("${spring.mail.username}")
    String appEmail;

    public String random6NumberCode(){
        Random rand = new Random();
        String code = "";
        for (int i = 0; i < 6; i++) {
            code += rand.nextInt(10);
        }
        return code;
    }

    public String sendSimpleMessage(String to) throws MessagingException {
        String code = this.random6NumberCode();
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setFrom(appEmail);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject("RESET PASSWORD LINK");
        mimeMessageHelper.setText("""
        Xin chào, bạn vừa gửi yêu cầu đổi mật khẩu, vui lòng nhập code bên dưới lên web để đổi mật khẩu:
        Mã: %s
        Nếu như bạn không yêu cầu điều này, vui lòng bỏ qua thông báo này
    """.formatted(code));
        emailSender.send(mimeMessage);
        return code;
    }
}
