package cc.coopersoft.construction.data.work;

/**
 * 变更不对业务，只对具体数据
 */
public enum WorkStatus {
  PRIVATE,  //私有的，只有本人或本组可以看到
  PREPARE, //准备中，所有人可见，但还未运行
  RUNNING, //运行中
  VALID, //已生效，但业务还未完成
  COMPLETE, //业务已完成，且已生效
  REJECT, //业务被驳加
  ABORT, //业务未完成，被中止
  DELETED; //业务已完成，后被删除
}
