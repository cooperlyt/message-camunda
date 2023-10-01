package cc.coopersoft.cloud.camunda.delegate;

import cc.coopersoft.cloud.camunda.mq.ProcessChangeService;
import cc.coopersoft.cloud.message.WorkStatus;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class WorkRejectDelegate implements JavaDelegate {

  public WorkRejectDelegate() {
    BeanInjectionHelper.autowireBean(this);
  }

  @Autowired
  private ProcessChangeService processChangeService;

  @Autowired
  private RepositoryService repositoryService;

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    log.info("work reject define: {}", delegateExecution.getProcessBusinessKey());
    ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
        .processDefinitionId(delegateExecution.getProcessDefinitionId())
        .singleResult();
    processChangeService.statusChange(Long.parseLong(delegateExecution.getProcessBusinessKey()),
        WorkStatus.REJECT, processDefinition.getId());

  }
}
