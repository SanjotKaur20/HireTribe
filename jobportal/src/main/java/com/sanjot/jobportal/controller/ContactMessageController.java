package com.sanjot.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanjot.jobportal.entity.ContactMessage;
import com.sanjot.jobportal.repository.ContactMessageRepository;

@Controller
@RequestMapping("/ContactUs")
public class ContactMessageController {
    private final ContactMessageRepository contactMessageRepository;

    @Autowired
    public ContactMessageController(ContactMessageRepository contactMessageRepository) {
        this.contactMessageRepository = contactMessageRepository;
    }

    @GetMapping
    public String showContactUsPage(Model model) {
        model.addAttribute("contactMessage", new ContactMessage());
        return "ContactUs"; // Ensure ContactUs.html exists in the templates directory
    }

    @PostMapping
    public String sendMessage(ContactMessage contactMessage) {
        contactMessageRepository.save(contactMessage);
        return "redirect:/ContactUs"; // Redirect to refresh the page
    }

    // Uncomment and use this method if you wish to clear all messages
    // @PostMapping("/clear")
    // public String clearMessages() {
    //     contactMessageRepository.deleteAll();
    //     return "redirect:/ContactUs"; // Redirect back to ContactUs page
    // }
}
