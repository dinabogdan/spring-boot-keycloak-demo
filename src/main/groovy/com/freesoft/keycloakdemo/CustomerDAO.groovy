package com.freesoft.keycloakdemo

import org.springframework.data.repository.CrudRepository

interface CustomerDAO extends CrudRepository<Customer, Long> {
}
