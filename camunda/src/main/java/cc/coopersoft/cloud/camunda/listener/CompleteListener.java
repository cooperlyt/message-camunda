package cc.coopersoft.cloud.camunda.listener;

import cc.coopersoft.cloud.camunda.delegate.BeanInjectionHelper;
import cc.coopersoft.cloud.camunda.mq.ProcessChangeService;
import cc.coopersoft.cloud.message.WorkStatus;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class CompleteListener implements ExecutionListener {

  @Autowired
  private ProcessChangeService processChangeService;

  @Autowired
  private RepositoryService repositoryService;

  public CompleteListener() {
    BeanInjectionHelper.autowireBean(this);
  }

  @Override
  public void notify(DelegateExecution execution) throws Exception {
    log.info("complete listener process: {}", execution.getProcessBusinessKey());

    ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
        .processDefinitionId(execution.getProcessDefinitionId())
        .singleResult();

    processChangeService.statusChange(Long.parseLong(execution.getProcessBusinessKey()),
        WorkStatus.COMPLETED, processDefinition.getId());
  }
}
