package cn.tedu.store;

import javax.servlet.MultipartConfigElement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

@SpringBootApplication
@Configuration
@MapperScan("cn.tedu.store.mapper")
public class StoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
	}

	@Bean
	public MultipartConfigElement multipartConfigration() {
		MultipartConfigFactory mcf
			= new MultipartConfigFactory();
		DataSize maxFileSize = DataSize.ofBytes(100 * 1024 * 1024);
		DataSize maxRequestSize = DataSize.ofBytes(100 * 1024 * 1024);
		mcf.setMaxFileSize(maxFileSize);
		mcf.setMaxRequestSize(maxRequestSize);
		MultipartConfigElement mce
			= mcf.createMultipartConfig();
		return mce;
	}
	
}







