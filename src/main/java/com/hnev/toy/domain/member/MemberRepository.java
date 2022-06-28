package com.hnev.toy.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    String updateMemberLastLoginTime = "update member set last_login_time = NOW() where email = :email";

    @Transactional
    @Modifying
    @Query(value = updateMemberLastLoginTime, nativeQuery = true)
    public int updateMemberLastLogin(@Param("email") String email);
    public Optional<Member> findByEmail(String email);
    public int countByEmailAndDropYn(String email, String dropYn);
}
