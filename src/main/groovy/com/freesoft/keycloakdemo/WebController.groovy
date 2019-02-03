package com.freesoft.keycloakdemo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

import java.security.Principal

@Controller
class WebController {

    @Autowired
    private CustomerDAO customerDAO

    @GetMapping(path = "/")
    def index() { "external" }

    @GetMapping(path = "/customers")
    String customers(Principal principal, Model model) {
        Iterable<Customer> customers = customerDAO.findAll()
        model.addAttribute("customers", customers)
        model.addAttribute("username", principal.getName())
        "customers"
    }
}
