package com.biblioteca;

import java.net.URI;
import java.net.URISyntaxException;
//import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

//classe de configuração do banco de dados

@Configuration
public class DataConfiguration {
	
	
	//dois Beans, 1 faz conexão com o banco e o outro Bean configura o hibernate??????
	
	
	@Bean
    public BasicDataSource dataSource() throws URISyntaxException {
        URI dbUri = new URI(System.getenv("JAWSDB_MARIA_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:mysql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);

        return basicDataSource;
    }
	

	
	
/*	
	
	
	@Bean //Bean de conexão com o banco
	public DataSource dataSource() {
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
			dataSource.setUrl("jdbc:mysql://localhost:3306/biblioteca?useSSL=true");//url do banco de dados /eventoapp -> vai ser o nome do banco
			dataSource.setUsername("root"); //define o root que vc está usando no seu Mysql 
			dataSource.setPassword("root"); //que ter o username e root que vc usou na sua máquina
			return dataSource; //retorna um objeto dataSource
			}
			
*/
	
	@Bean 
	public JpaVendorAdapter jpaVendorAdapter() { 
			HibernateJpaVendorAdapter adapter = new  HibernateJpaVendorAdapter();//cria conexão com o hibernate
			adapter.setDatabase(Database.MYSQL); //define qual database estamos utilizando, nesse caso o MySql
			adapter.setShowSql(false);//mostra tds os comandos no console
			adapter.setGenerateDdl(true);//permite que o hibernate crie as tabelas automaticamente
			adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");//dialeto que vai ser utilizado Mysql
			adapter.setPrepareConnection(true);//preparar a conexão automaticamente
			return adapter; //retorna um objeto adapter
	}
}
