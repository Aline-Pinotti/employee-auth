package com.devsuperior.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devsuperior.demo.entities.User;
import com.devsuperior.demo.projections.UserDetailsProjection;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query(nativeQuery = true, value = """
        SELECT u.email AS username, u.password, r.id AS roleId, r.authority
        FROM tb_user u
        INNER JOIN tb_user_role ur ON u.id = ur.user_id
        INNER JOIN tb_role r ON ur.role_id = r.id
        WHERE u.email = :email
        """)
    List<UserDetailsProjection> searchUserAndRolesByEmail(String email); //mesmo usando o FETCH Eager na entity??

}
