package cc.coopersoft.cloud.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatusChangeMessage {

  private WorkStatus status;

  private long workId;

}
