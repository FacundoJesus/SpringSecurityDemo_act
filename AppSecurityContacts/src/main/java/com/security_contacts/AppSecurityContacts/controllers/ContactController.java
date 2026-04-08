package com.security_contacts.AppSecurityContacts.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    //USER AND ADMIN
    @GetMapping
    public String getContacts() {
        return "Returning all contacts";
    }

    //ADMIN
    @PostMapping
    public String addContact() {
        return "New contact added!";
    }

    //ADMIN
    @DeleteMapping("/{id}")
    public String deleteContact(@PathVariable int id) {
        return "Contact " + id + " deleted!";
    }

    //NO LOGIN REQUIRED. TODOS
    @GetMapping("/public/info")
    public String publicInfo() {
        return "This is a public endpoint";
    }

}
