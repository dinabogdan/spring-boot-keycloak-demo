package com.freesoft.keycloakdemo

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id

    private String name
    private String serviceRendered
    private String address

    Long getId() {
        return id
    }

    void setId(Long id) {
        this.id = id
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    String getServiceRendered() {
        return serviceRendered
    }

    void setServiceRendered(String serviceRendered) {
        this.serviceRendered = serviceRendered
    }

    String getAddress() {
        return address
    }

    void setAddress(String address) {
        this.address = address
    }
}
