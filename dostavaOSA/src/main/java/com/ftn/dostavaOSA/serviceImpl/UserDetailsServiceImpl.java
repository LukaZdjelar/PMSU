package com.ftn.dostavaOSA.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ftn.dostavaOSA.model.Korisnik;
import com.ftn.dostavaOSA.service.KorisnikService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	KorisnikService korisnikService;

	@Override
	public UserDetails loadUserByUsername(String korisnickoIme) throws UsernameNotFoundException {
		Korisnik korisnik = korisnikService.findKorisnikByKorisnickoIme(korisnickoIme);

        if(korisnik == null){
            throw new UsernameNotFoundException("There is no user with username " + korisnickoIme);
        }else{
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            String role = "ROLE_" + korisnik.getUloga().toString();
            grantedAuthorities.add(new SimpleGrantedAuthority(role));

            return new org.springframework.security.core.userdetails.User(
            		korisnik.getKorisnickoIme().trim(),
            		korisnik.getLozinka().trim(),
                    grantedAuthorities);
        }
	}

}
