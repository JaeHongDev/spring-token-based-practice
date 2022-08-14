package com.example.springtokenbasedpractice.service;


import com.example.springtokenbasedpractice.model.AppUser;
import com.example.springtokenbasedpractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public String signin (String username,String password){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
            return jwtTokenProvider.createToken();
        }catch(AuthenticationException e){
            throw new CustomException("Invalid username/password");
        }
    }

    public String signup(AppUser appUser){
        if(!userRepository.existsByUsername(appUser.getUsername()){
            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
            userRepository.save(appUser);
            return jwtTokenProvider.createToken(appuser.getUsername());
        }
        else{
            throw new CustomException("username is alerady");
        }
    }

    public void delete(String username){
        userRepository.deleteByUsername(username);
    }

    public AppUser search(String username){
        AppUser appUser = userRepository.findByUsername(username);
        if(appUser == null){
            throw new CustomException("The user doesn't exists");
        }
        return appUser;
    }

    public AppUser whoami(HttpServletRequest req){
        return userRepository.findByUsername(jwtTokenProvider.resolveToken(req));
    }
    public String refresh(String username){
        return jwtTokenProvider
                .createToken(username,userRepository.findByUsername(username).getAppUserRoles());
    }
}
