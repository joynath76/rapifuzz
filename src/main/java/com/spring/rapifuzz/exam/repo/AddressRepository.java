package com.spring.rapifuzz.exam.repo;

import com.spring.rapifuzz.exam.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {}

