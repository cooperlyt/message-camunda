package cc.coopersoft.cloud.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkMessage implements java.io.Serializable{

  public static final String MESSAGE_HEADER_WORK_TYPE = "type";
  public static final String MESSAGE_HEADER_WORK_DEFINE = "define";
  public static final String MESSAGE_HEADER_DATA_ID = "data_id";
  private long workId;
  private String empId;
  private String empName;
  private long orgId;
}
