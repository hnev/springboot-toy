package com.hnev.toy.domain.member;

import com.hnev.toy.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@Getter
@Entity(name = "member")
public class Member extends BaseTimeEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String gender;
    private String dropYn;
    private LocalDateTime lastLoginTime;

    @Builder
    public Member(Long id, String email, String password, String gender, String dropYn) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.dropYn = dropYn;
    }

    @Getter
    @Setter
    public static class RequestDto {
        private String email;
        private String password;
        private String gender;
        private String dropYn;

        public Member toEntity() {
            return Member.builder()
                    .email(email)
                    .password(password)
                    .gender(gender)
                    .dropYn(dropYn)
                    .build();
        }
    }

    @Getter
    public static class ResponseDto {
        private Long id;
        private String email;
        private String password;
        private String gender;
        private String dropYn;
        private String lastLoginTime;
        private String registerTime;
        private String modifyTime;

        public ResponseDto(Member member) {
            this.id = member.id;
            this.email = member.email;
            this.password = member.password;
            this.gender = member.gender;
            this.dropYn = member.dropYn;
            this.lastLoginTime = member.toStringDateTime(member.getLastLoginTime());
            this.registerTime = member.toStringDateTime(member.getRegisterTime());
            this.modifyTime = member.toStringDateTime(member.getModifyTime());
        }
    }


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    //????????? ???????????? ?????? ????????? ??????
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection <GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(() -> {
            return "????????? ????????? ??????";
        });

        //collectors.add(new SimpleGrantedAuthority("Role"));

        return collectors;
    }

    //????????? ???????????? ???????????? ?????? (true: ?????? ??????)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //????????? ??????????????? ???????????? ??????. (true: ????????? ??????)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //??????????????? ???????????? ???????????? ????????????. (true: ?????? ??????)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //????????? ?????????(????????????)?????? ?????? (true: ?????????)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
