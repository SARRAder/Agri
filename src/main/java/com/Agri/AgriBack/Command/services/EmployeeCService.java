package com.Agri.AgriBack.Command.services;

import com.Agri.AgriBack.Command.producer.EmployeeProducer;
import com.Agri.AgriBack.Command.entity.Employee;
import com.Agri.AgriBack.Command.repository.EmployeeCRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCService {
    @Autowired
    private EmployeeCRepo empRepo;

    @Autowired
    private EmployeeProducer empProducer;

    public Employee createEmployee(Employee employee) {
        Employee savedEmployee = empRepo.save(employee);
        empProducer.createEmployeeEvent(savedEmployee);
        return savedEmployee;
    }

    public Employee updateEmployee(Employee employee){
        Employee savedEmployee = empRepo.save(employee);
        empProducer.updateEmployeeEvent(savedEmployee);
        return savedEmployee;
    }

    //pour l'authentification selon email et mdp
    public Employee authenticateUser(String email, String password) {
        Optional<Employee> user = empRepo.findByEmailAndPassword(email, password);
        return user.orElse(null);
    }

    //pour la generation d'un mot de passe random
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int PASSWORD_LENGTH = 8;
    private static final SecureRandom RANDOM = new SecureRandom();

    public String generatePassword() {
        return IntStream.range(0, PASSWORD_LENGTH)
                .map(i -> RANDOM.nextInt(CHARS.length()))
                .mapToObj(CHARS::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    //pour l'envoi d'un email
    @Autowired
    private JavaMailSender mailSender;
    @Async
    public void sendSimpleEmail(String toEmail, String subject, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sarra.labidi.395@gmail.com");
        message.setTo(toEmail);

        // Utilisation de HTML dans le corps du message
        String htmlBody = "Your password for authentication " + code + "";

        message.setText(htmlBody); // Utilisez setText pour le contenu du message
        message.setSubject(subject);

        mailSender.send(message); // Envoi du message

        System.out.println("Mail Send...");
    }
}
