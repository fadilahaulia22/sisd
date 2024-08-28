package com.sisd.sisd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SistemInformasiSekolahDasarApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemInformasiSekolahDasarApplication.class, args);
	}

}
