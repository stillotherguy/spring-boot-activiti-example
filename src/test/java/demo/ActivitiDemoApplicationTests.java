package demo;

import static org.junit.Assert.*;

import com.google.common.collect.Maps;
import org.activiti.engine.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ActivitiDemoApplication.class)
//@WebAppConfiguration
@IntegrationTest
public class ActivitiDemoApplicationTests {

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private ManagementService managementService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	private FormService formService;

	@Test
	//@Deployment(resources = {"simple.bpmn20.xml"})
	public void testStartSimpleProcess() throws SQLException {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();
		assertEquals(processDefinition.getKey(), "simpleProcess");
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("simpleProcess");
		assertNotNull(processInstance);
		System.out.println("pid=" + processInstance.getId() + ", pdid=" + processInstance.getProcessDefinitionId());
	}

	@Test
	public void testSimpleUserTask() {
		Map<String, Object> variables = Maps.newHashMap();
		variables.put("applyUser", "Ethan Zhang");
		variables.put("days", 3);

		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("simpleProcess");
		assertNotNull(processInstance);
		System.out.println("pid=" + processInstance.getId() + ", pdid=" + processInstance.getProcessDefinitionId());

		Task taskOfDeptLeader = taskService.createTaskQuery().taskCandidateOrAssigned("deptLeader").singleResult();
		assertNotNull(taskOfDeptLeader);
		assertEquals("领导审批", taskOfDeptLeader.getName());
		taskService.claim(taskOfDeptLeader.getId(), "Alex Yang");
		variables.clear();
		variables.put("approved", true);
		taskService.complete(taskOfDeptLeader.getId(), variables);
		taskOfDeptLeader = taskService.createTaskQuery().taskCandidateOrAssigned("deptLeader").singleResult();
		assertNull(taskOfDeptLeader);

		long count = historyService.createHistoricProcessInstanceQuery().finished().count();
		assertEquals(1, count);
	}

}
