package cc.coopersoft.cloud.camunda.messages;

import cc.coopersoft.cloud.message.WorkChangeMessage;
import cc.coopersoft.cloud.message.WorkItemType;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.spring.boot.starter.event.ExecutionEvent;
import org.camunda.bpm.spring.boot.starter.event.TaskEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CamundaListener {

  private final TaskService taskService;

  private final ProcessChangeService processChangeService;

  private final RepositoryService repositoryService;

  private final IdentityService identityService;

  public CamundaListener(TaskService taskService,
                         ProcessChangeService processChangeService,
                         RepositoryService repositoryService, IdentityService identityService) {
    this.taskService = taskService;
    this.processChangeService = processChangeService;
    this.repositoryService = repositoryService;
    this.identityService = identityService;
  }

  @EventListener
  public void onTaskEvent(DelegateTask taskDelegate) {
    log.debug("mutable task event by taskDelegate: {}", taskDelegate.getEventName());
    // handle mutable task event
  }

  @EventListener(condition="#taskEvent.eventName=='complete'")
  public void onTaskEvent(TaskEvent taskEvent) {
    log.debug("immutable task event: {} by TaskEvent", taskEvent.getEventName());

    ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
        .processDefinitionId(taskEvent.getProcessDefinitionId())
        .singleResult();

    Task task = taskService.createTaskQuery().taskId(taskEvent.getId()).initializeFormKeys().singleResult();

    User user = identityService.createUserQuery().userId(taskEvent.getAssignee()).singleResult();

    try {
      processChangeService.processChange(WorkChangeMessage.builder()
              .message((String) taskService.getVariable(taskEvent.getId(),"message"))
              .workId(Long.parseLong(taskEvent.getId()))
              .empId(taskEvent.getAssignee())
              .empName(user.getFirstName() + user.getLastName())
              .orgId(0L)
              .taskName(task.getName())
              .taskId(taskEvent.getId())
              .type(WorkItemType.valueOf((String) taskService.getVariable(taskEvent.getId(),"type")))
              .build(),
            processDefinition.getId());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

//    log.debug("       task Assignee: {} by TaskEvent", taskEvent.getAssignee());
//
//
//    taskEvent.getAssignee();
//
//
//    if (task != null){
//      log.debug("      task FormKey: {} by TaskEvent", task.getFormKey());
//      //task.getFormKey();
//
//      taskService.getVariableLocal(taskEvent.getId(),"type");
//
//      log.debug("      taskEvent id: {} == {} by TaskEvent", taskEvent.getId(),task.getId());
//      log.debug("      task variable message: {} by TaskEvent", taskService.getVariableLocal(taskEvent.getId(),"message"));
//
//      log.debug("      task variable type: {} by TaskEvent", taskService.getVariable(taskEvent.getId(),"type"));
//      // handle immutable task event
//    }

  }

  @EventListener
  public void onExecutionEvent(DelegateExecution executionDelegate) {
    log.debug("immutable executionDelegate event: {}", executionDelegate.getEventName());
    // handle mutable execution event
  }

  @EventListener
  public void onExecutionEvent(ExecutionEvent executionEvent) {
    log.debug("immutable execution event: {}", executionEvent.getEventName());
    // handle immutable execution event
  }

  @EventListener
  public void onHistoryEvent(HistoryEvent historyEvent) {
    log.debug("history event");
    // handle history event
  }

}
