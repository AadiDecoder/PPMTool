package io.webBack.ppmtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication()
public class PpmtoolApplication {

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(PpmtoolApplication.class, args);
	}

//	@Bean
//	public HibernateJpaSessionFactoryBean sessionFactory() {
//		return new HibernateJpaSessionFactoryBean();
//	}
//
//	@Bean
//	public FilterRegistrationBean registerOpenSessionInViewFilter() {
//		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//		OpenEntityManagerInViewFilter filter = new OpenEntityManagerInViewFilter();
//		registrationBean.setFilter(filter);
//		registrationBean.setOrder(5);
//		return registrationBean;
//	}

}
