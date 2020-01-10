package com.samcode.sysbet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.samcode.sysbet.api.entity.User;
import com.samcode.sysbet.api.enums.ProfileEnum;
import com.samcode.sysbet.api.properties.GlobalParametersProperties;
import com.samcode.sysbet.api.repository.UserRepository;

@SpringBootApplication
public class SysBetApplication {
	
	@Autowired
	private GlobalParametersProperties parameters;

	public static void main(String[] args) {
		SpringApplication.run(SysBetApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(/*UserRepository userRepository, PasswordEncoder passwordEncoder*/) {
		return args -> {
			initUsers(/*userRepository, passwordEncoder*/);
		};
	}
	
	private void initUsers(/*UserRepository userRepository, PasswordEncoder passwordEncoder*/) {
		System.out.println(parameters.getReportPath());
		/*User admin = new User();
		admin.setUsername("admin");
		admin.setPassword(passwordEncoder.encode("123456"));
		admin.setProfile(ProfileEnum.ROLE_ADMIN);
		
		User find = userRepository.findByUsername("sdaneu");
		if(find == null) {
			userRepository.save(admin);
		}*/
	}
}
