package cc.coopersoft.cloud.camunda.mq;

import cc.coopersoft.cloud.camunda.Constants;
import cc.coopersoft.cloud.message.StatusChangeMessage;
import cc.coopersoft.cloud.message.WorkChangeMessage;
import cc.coopersoft.cloud.message.WorkStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProcessChangeService {

  private final StreamBridge streamBridge;

  public ProcessChangeService(StreamBridge streamBridge) {
    this.streamBridge = streamBridge;
  }

  public void statusChange(Long workId, WorkStatus status) throws Exception {
    var msg = MessageBuilder.withPayload(StatusChangeMessage.builder().status(status).workId(workId).build())
        .build();
    if (!streamBridge.send("statusChangeChannel-out-0",msg)){
      throw new Exception("message send fail!");
    }
  }

  public void processChange(WorkChangeMessage changeMessage,String define) throws Exception {
    var msg = MessageBuilder.withPayload(changeMessage)
        .setHeader(Constants.DEFINE_KEY_NAME,define)
        .build();
    if (!streamBridge.send("processChangeChannel-out-0",msg)){
      throw new Exception("message send fail!");
    }
  }

}
