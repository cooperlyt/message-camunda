package cc.coopersoft.cloud.message;

public enum WorkItemType {
  WORKER, //操作人
  APPLY, //申请
  INPUT, //录入
  ACCEPT,//受理
  FIRST_CHECK, // 初审
  SECOND_CHECK,//复初
  CHECKED, // 审批
  VALID //盖章
}
