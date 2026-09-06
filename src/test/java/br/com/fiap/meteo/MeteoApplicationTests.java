package br.com.fiap.meteo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class MeteoApplicationTests {

	@Test
	void contextLoads() {

		System.out.println(new BCryptPasswordEncoder().encode("teste123"));
	}



}
