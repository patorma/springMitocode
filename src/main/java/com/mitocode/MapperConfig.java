package com.mitocode;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
	
	// con la anotacion spring toma la instancia que se retorna
	//lo gestiona en el core container y más adelante cuan do se haga un autowired
	//
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
