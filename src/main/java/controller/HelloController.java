package controller;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by Ethan Zhang on 15/6/23.
 */
@RestController
public class HelloController {
    @Autowired
    private ProcessEngineFactoryBean engine;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private DataSource dataSource;

    @RequestMapping("/index")
    public String hello() throws SQLException {
        System.out.println(dataSource.getConnection());
        System.out.println(repositoryService);
        return "hello";
    }
}
