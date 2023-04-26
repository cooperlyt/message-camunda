package cc.coopersoft.construction.data.work;

public enum WorkActionType {

  CREATE_PRIVATE(false, WorkItemType.INPUT, WorkStatus.PRIVATE),
  CREATE_PREPARE(false, WorkItemType.INPUT, WorkStatus.PREPARE),
  CREATE_COMPLETE(false, WorkItemType.WORKER, WorkStatus.COMPLETE),
  CREATE_RUN(false, WorkItemType.INPUT, WorkStatus.RUNNING),

  PREPARE(true, WorkItemType.APPLY, WorkStatus.PREPARE),

  PREPARE_RUN(true, WorkItemType.ACCEPT, WorkStatus.RUNNING),
  RUN(true, WorkItemType.APPLY, WorkStatus.RUNNING),

  ACCEPT(true, WorkItemType.ACCEPT, WorkStatus.RUNNING),

  FIRST_CHECK(true, WorkItemType.FIRST_CHECK, WorkStatus.RUNNING),

  SECOND_CHECK(true, WorkItemType.SECOND_CHECK, WorkStatus.RUNNING),

  CHECKED(true, WorkItemType.CHECKED, WorkStatus.RUNNING),

  REJECT(true,null, WorkStatus.REJECT),

  VALID(true,null, WorkStatus.RUNNING),

  COMPLETE(true, null, WorkStatus.COMPLETE);

  public final boolean created;

  public final WorkItemType workType;

  public final WorkStatus status;

  WorkActionType(boolean created, WorkItemType workType, WorkStatus status) {
    this.created = created;
    this.workType = workType;
    this.status = status;
  }
}
