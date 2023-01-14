package com.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.config.AppConstants;
import com.blog.entities.Role;
import com.blog.repositories.RoleRepository;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner{
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

//	@Override
//	public void run(String... args) throws Exception {	
//		System.out.println(this.passwordEncoder.encode("maxank"));
//	}
	
	@Override
	public void run(String... args) throws Exception {
		try {
			Role role1 = new Role();
			role1.setRoleId(AppConstants.ADMIN_USER);
			role1.setName("ROLE_ADMIN");
			
			Role role2 = new Role();
			role2.setRoleId(AppConstants.NORMAL_USER);
			role2.setName("ROLE_NORMAL");

			List<Role> roles = List.of(role1,role2);
			
			List<Role> result = this.roleRepository.saveAll(roles);
			
			for(Role role:result) {
				System.out.println(role.getName());
			}
		}
		
		catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
