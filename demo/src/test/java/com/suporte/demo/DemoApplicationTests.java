package com.suporte.demo;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;

import org.springframework.security.core.token.SecureRandomFactoryBean;
import org.springframework.security.core.token.Token;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}



	@Test
	void criaçãoToken() throws Exception{
		
		KeyBasedPersistenceTokenService service = new KeyBasedPersistenceTokenService();
		service.setServerSecret("olha o carro");
		service.setServerInteger(16);
		service.setSecureRandom(new SecureRandomFactoryBean().getObject());


		Token allocateToken = service.allocateToken("maria@email.com");;

		System.out.println(allocateToken);
		System.out.println(new Date(allocateToken.getKeyCreationTime()));
		System.out.println(allocateToken.getKey());
	}






}
