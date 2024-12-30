package com.shop.e_comerce.security.UserDetails;

import com.shop.e_comerce.model.User;
import com.shop.e_comerce.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ShopUserDetailsService  implements UserDetailsService {

    private final UserRepository userRepository;
    public ShopUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= Optional.ofNullable(userRepository.findByEmail(username)).orElseThrow(()->new UsernameNotFoundException("the user dosen't exist"));
        return ShopUserDetails.build(user);
    }
}
