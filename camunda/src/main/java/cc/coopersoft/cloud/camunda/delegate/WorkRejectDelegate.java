package cc.coopersoft.cloud.camunda.delegate;

import cc.coopersoft.cloud.camunda.Constants;
import cc.coopersoft.cloud.camunda.messages.ProcessChangeService;
import cc.coopersoft.cloud.message.WorkChangeMessage;
import cc.coopersoft.cloud.message.WorkStatus;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.rest.ProcessDefinitionRestService;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class WorkRejectDelegate implements JavaDelegate {

  public WorkRejectDelegate() {
    BeanInjectionHelper.autowireBean(this);
  }

  @Autowired
  private ProcessChangeService processChangeService;



  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    log.debug("work reject define: {}", delegateExecution.getProcessBusinessKey());

    processChangeService.statusChange(Long.parseLong(delegateExecution.getProcessBusinessKey()), WorkStatus.REJECT);

  }
}
