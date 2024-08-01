package com.backendVn.SWP.repositories;

import com.backendVn.SWP.entities.Process;
import com.backendVn.SWP.entities.RequestOrder;
import com.backendVn.SWP.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcessRepository extends JpaRepository<Process, Integer> {
    List<Process> findByRequestOrderid(RequestOrder requestOrder);
    List<Process> findByUpdatedBy(User user);
}