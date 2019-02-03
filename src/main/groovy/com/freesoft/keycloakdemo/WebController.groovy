package com.freesoft.keycloakdemo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WebController {

    @Autowired
    private final CustomerDAO customerDAO

    @GetMapping(path = "/")
    def index() { "external" }

    @GetMapping(path = "/customers")
    def customers(Model model) {
        Iterable<Customer> customers = customerDAO.findAll()
        model.addAttribute("customers", customers)
        "customers"
    }
}
