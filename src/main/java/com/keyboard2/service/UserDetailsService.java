package com.keyboard2.service;


import com.keyboard2.dto.MemberDTO;
import com.keyboard2.dto.UserDTO;
import com.keyboard2.entity.User;
import com.keyboard2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 데이터베이스에 조회해서 사용자를 받아오는 역할
 * @author magic
 *
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository clubMemberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("ClubUserDetailsService loadUserByUsername " + username);

        // 아이디(username)로 조회
//        Optional<User> result = clubMemberRepository.findByUserId(username);
        User user = clubMemberRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));

        System.out.println("result :: " + user);
//        if(user.isEmpty()){
//            throw new UsernameNotFoundException("Check User Email or from Social ");
//        }

        User clubMember = user;
        log.info("clubMember :: " + clubMember);

        /*
         * 1. 반환 타입이 UserDetails이기 때문에 UserDetails를 구현한
         *    ClubAuthMemberDTO 타입으로 갈아태움.
         * 2. 권한은 여러개일 수 있으므로
         *
         */
        MemberDTO clubAuthMember = new MemberDTO(
                clubMember.getUserId(),
                clubMember.getUserPassword(),
                clubMember.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_"+role.getRoleName()))
                        .collect(Collectors.toSet())
        );

        System.out.println("clubAuthMember :: " + clubAuthMember);

        clubAuthMember.setUserId(clubMember.getUserId());

        return clubAuthMember;
    }
}
