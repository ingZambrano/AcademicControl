package com.avior.academic.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.avior.academic.dao.UserData;
import com.avior.academic.pojo.PreLogin;
@Service
public class AuthenticationService implements UserDetailsService {
	
//	private final static Logger logger = Logger.getLogger(AuthenticationService.class);
	
	@Autowired
	private PreLogin pre;
	
	@Autowired
	@Qualifier("userDataImp")
	private UserData userDataImp;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		//Validar que exista PreLogin
//		if(pre.getBase() ==  null){
//			return null;
//		}		
		
		
//		UserInfo userInfo = null;
//		try{
//			userInfo = userDataImp.getUserIdByUserName(username);
			//De momento se hardcodea, cuando se tenga el servicio de login se omite ya que la info se trae del dao
//			userInfo.setPassword("123456*");
//			userInfo.setRole("USER_FAMILIAR");
//		}catch(Exception e){
//			logger.error(e.getMessage(),e);
//			return this.getUserDetails();
//		}		
		
//		GrantedAuthority authority = new SimpleGrantedAuthority(userInfo.getRole());
//		UserDetails userDetails = (UserDetails)new User(userInfo.getUsername(), 
//				userInfo.getPassword(), Arrays.asList(authority));
//		return userDetails;
		return null;
	}
	
//	private UserDetails getUserDetails(){
//		
//		return new UserDetails() {
//			
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public boolean isEnabled() {
//				// TODO Auto-generated method stub
//				return false;
//			}
//			
//			@Override
//			public boolean isCredentialsNonExpired() {
//				// TODO Auto-generated method stub
//				return false;
//			}
//			
//			@Override
//			public boolean isAccountNonLocked() {
//				// TODO Auto-generated method stub
//				return false;
//			}
//			
//			@Override
//			public boolean isAccountNonExpired() {
//				// TODO Auto-generated method stub
//				return false;
//			}
//			
//			@Override
//			public String getUsername() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//			@Override
//			public String getPassword() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//			@Override
//			public Collection<? extends GrantedAuthority> getAuthorities() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//		};		
//		
//	}
} 