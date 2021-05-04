package org.uresti.pozarreal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.uresti.pozarreal.model.RoleByUser;

@Repository
public interface RolesRepository extends JpaRepository<RoleByUser, String> {
    @Query("SELECT r.role FROM RoleByUser r WHERE r.userId = :user")
    List<String> findRolesByUser(String user);
}
