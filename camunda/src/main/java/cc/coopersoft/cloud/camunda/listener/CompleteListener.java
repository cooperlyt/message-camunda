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


  public CompleteListener() {
    BeanInjectionHelper.autowireBean(this);
  }

  @Override
  public void notify(DelegateExecution execution) throws Exception {
    log.info("complete listener process: {}", execution.getProcessBusinessKey());
    
    processChangeService.statusChange(Long.parseLong(execution.getProcessBusinessKey()),
        WorkStatus.COMPLETED, execution.getProcessDefinitionId());
  }
}
