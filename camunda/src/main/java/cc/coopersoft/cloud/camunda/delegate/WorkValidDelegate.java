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
public class WorkValidDelegate implements JavaDelegate {

  @Autowired
  private ProcessChangeService processChangeService;

  public WorkValidDelegate() {
    BeanInjectionHelper.autowireBean(this);
  }

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    log.info("work valid define: {}", delegateExecution.getProcessBusinessKey());

    processChangeService.statusChange(Long.parseLong(delegateExecution.getProcessBusinessKey()),
        WorkStatus.VALID,delegateExecution.getProcessDefinitionId());
  }
}
