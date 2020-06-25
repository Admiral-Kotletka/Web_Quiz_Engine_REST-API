package com.example.demo.service;
import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class AccountService implements UserDetailsService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    AccountRepository accountRepository;

    public Account saveAccount(Account account) {
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    public Account findAccountByEmail(String email) {
        for (Account i : accountRepository.findAll()) {
            if (i.getEmail().equals(email)) {
                return i;
            }
        }
        return null;
    }

    public Account getCurrentAccount () {

        //get principal from security holder
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //match account with principal and return
        return findAccountByEmail(((UserDetails) principal).getUsername());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = findAccountByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException("not found");
        }
        return account;
    }
}
