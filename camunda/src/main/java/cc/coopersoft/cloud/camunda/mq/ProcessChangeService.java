package cc.coopersoft.cloud.camunda.mq;

import cc.coopersoft.cloud.camunda.Constants;
import cc.coopersoft.cloud.message.StatusChangeMessage;
import cc.coopersoft.cloud.message.WorkChangeMessage;
import cc.coopersoft.cloud.message.WorkStatus;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProcessChangeService {

  private final StreamBridge streamBridge;

  private final RepositoryService repositoryService;


  public ProcessChangeService(StreamBridge streamBridge, RepositoryService repositoryService) {
    this.streamBridge = streamBridge;
    this.repositoryService = repositoryService;
  }

  public void statusChange(Long workId, WorkStatus status,String processDefinitionId) throws Exception {
    var msg = MessageBuilder.withPayload(StatusChangeMessage.builder().status(status).workId(workId).build())
        .setHeader(Constants.DEFINE_KEY_NAME,getDefineId(processDefinitionId))
        .build();
    if (!streamBridge.send("statusChangeChannel-out-0",msg)){
      throw new Exception("message send fail!");
    }
    log.info("status change mq is send: {} -> {}",workId,status);
  }

  public void processChange(WorkChangeMessage changeMessage,String processDefinitionId) throws Exception {
    var msg = MessageBuilder.withPayload(changeMessage)
        .setHeader(Constants.DEFINE_KEY_NAME,getDefineId(processDefinitionId))
        .build();
    if (!streamBridge.send("processChangeChannel-out-0",msg)){
      throw new Exception("message send fail!");
    }
    log.info("process change  mq is send : {}",changeMessage);
  }

  private String getDefineId(String processDefinitionId){
    ProcessDefinition definition = repositoryService.createProcessDefinitionQuery()
        .processDefinitionId(processDefinitionId)
        .singleResult();
    log.info("process define id: {}, key: {}",definition.getId(),definition.getKey());
    return definition.getKey();
  }

}
