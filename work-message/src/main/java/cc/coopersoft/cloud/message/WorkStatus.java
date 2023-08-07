package cc.coopersoft.cloud.message;

import java.util.EnumSet;

public enum WorkStatus {

  PREPARE, //准备中
  PRIVATE, //私有的，只有本人或本组可以看到

  RUNNING, //运行中

  COMPLETED, //已完成

  VALID, //已生效，但业务还未完成
  REJECT, //业务被驳加
  ABORT, //业务未完成，被中止
  DELETED; //业务已完成，后被删除

  static EnumSet<WorkStatus> create = EnumSet.of(PREPARE,PRIVATE,RUNNING,COMPLETED);

  static EnumSet<WorkStatus> change = EnumSet.of(VALID,COMPLETED,REJECT,ABORT,DELETED);

}
