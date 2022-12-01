package com.example.demohibernate;

import com.example.demohibernate.models.Message;
import com.example.demohibernate.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class DemoHibernateApplication extends JFrame implements CommandLineRunner {

    @Autowired
    MessageRepository messageRepository;

    public DemoHibernateApplication() {
        JButton button = new JButton("ok");

        button.addActionListener(e -> {
            System.out.println("Hello world");
            messageRepository.save(new Message(1L, "Hola"));
            messageRepository.save(new Message(2L, "A"));
            messageRepository.save(new Message(3L, "B"));
            for (Message message : messageRepository.findAll()) {
                System.out.println(message);
            }
        });

        JPanel panel = new JPanel();

        panel.add(button);

        this.add(panel);

        this.setSize(400, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        //SpringApplication.run(DemoHibernateApplication.class, args);

        ConfigurableApplicationContext context = new SpringApplicationBuilder(DemoHibernateApplication.class)
                .headless(false).run(args);

        EventQueue.invokeLater(() -> {

            DemoHibernateApplication app = context.getBean(DemoHibernateApplication.class);
            app.setVisible(true);
        });

    }

    @Override
    public void run(String... args) throws Exception {

    }
}
