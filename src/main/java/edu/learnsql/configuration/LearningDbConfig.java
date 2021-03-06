package edu.learnsql.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories(
        basePackages = "edu.learnsql.dao.learning",
        entityManagerFactoryRef = "learningEntityManager",
        transactionManagerRef = "learningTransactionManager"
)
public class LearningDbConfig {
    private final Environment env;

    @Autowired
    public LearningDbConfig(Environment env) {this.env = env;}

    @Bean
        public LocalContainerEntityManagerFactoryBean learningEntityManager() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(learningDataSource());
        em.setPackagesToScan("edu.learnsql.entities.learning");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect",
                       env.getProperty("spring.jpa.properties.hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.learning-datasource")
    public DataSource learningDataSource() {

        DriverManagerDataSource dataSource
                = new DriverManagerDataSource();
        dataSource.setDriverClassName(
                env.getProperty("spring.datasource.driverClassName"));
        dataSource.setUrl(env.getProperty("spring.learning-datasource.url"));
        dataSource.setUsername(env.getProperty("spring.learning-datasource.username"));
        dataSource.setPassword(env.getProperty("spring.learning-datasource.password"));

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager learningTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                learningEntityManager().getObject());
        return transactionManager;
    }
}