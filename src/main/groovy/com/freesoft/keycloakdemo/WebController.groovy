package com.freesoft.keycloakdemo

import org.keycloak.KeycloakPrincipal
import org.keycloak.KeycloakSecurityContext
import org.keycloak.adapters.RefreshableKeycloakSecurityContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class WebController {

    @Autowired
    private CustomerDAO customerDAO

    @GetMapping(path = "/")
    def index() { "external" }

    @GetMapping(path = "/customers")
    String customers(HttpServletRequest httpServletRequest, Model model) {
        RefreshableKeycloakSecurityContext context = httpServletRequest.getAttribute(KeycloakSecurityContext.class.getName())
        def username = context.token.preferredUsername
        Iterable<Customer> customers = customerDAO.findAll()
        model.addAttribute("customers", customers)
        model.addAttribute("username", username)
        "customers"
    }
}
