package com.freesoft.keycloakdemo

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class Runner implements CommandLineRunner {

    CustomerDAO customerDao

    Runner(CustomerDAO customerDAO) {
        this.customerDao = customerDAO
    }

    @Override
    void run(String... args) throws Exception {
        def customer1 = new Customer()
        customer1.setAddress("111 foo blvd")
        customer1.setName("Foo industries")
        customer1.setServiceRendered("Important services")
        customerDao.save(customer1)

        def customer2 = new Customer()
        customer2.setAddress("2222 bar street")
        customer2.setName("Bar LLP")
        customer2.setServiceRendered("Important services")
        customerDao.save(customer2)

        def customer3 = new Customer()
        customer3.setAddress("33 main street")
        customer3.setName("BIg LLC")
        customer3.setServiceRendered("Important services")
        customerDao.save(customer3)
    }
}
