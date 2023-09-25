package cc.coopersoft.cloud.message;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class WorkCreateMessage implements java.io.Serializable{

  private static final long serialVersionUID = 1L;

  private long workId;

  private Map<String,Object> data;
}
