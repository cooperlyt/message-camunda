package cc.coopersoft.cloud.message;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class WorkCreateMessage extends WorkMessage implements java.io.Serializable{

  private static final long serialVersionUID = 1L;

  private Map<String,Object> data;
}
