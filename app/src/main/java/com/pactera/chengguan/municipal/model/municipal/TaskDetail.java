package com.pactera.chengguan.municipal.model.municipal;
import java.util.ArrayList;
import java.util.List;

/**
 * 任务单详情
 * Created by huang hua
 * 2016/5/9.
 */
public class TaskDetail {

    /**
     * 新增
     */
    public static final String TASKFORM_STATUS_NEW = "new";

    /**
     * 施工
     */
    public static final String TASKFORM_STATUS_BUILDE = "builde";

    /**
     * 待验收
     */
    public static final String TASKFORM_STATUS_ACCEPT = "accept";

    /**
     * 办结
     */
    public static final String TASKFORM_STATUS_FINISH = "finish";

    //任务单ID
    private int workFormId;
    //编号
    private String numbering = "";
    //作业单位
    private int sectionId;
    //联系依据
    private String rationale = "";
    //资金来源
    private String sourcesFund = "";
    //完成时间
    private String finishDate = "";
    //工作任务描述
    private String description = "";
    //位置
    private String location = "";
    //业主单位
    private String owner = "";
    //部门负责人
    private String departmentHead = "";
    //下发时间
    private String issuedDate = "";
    //处理内容
    private String processContext = "";
    //作业账户
    private int companyAccount;
    //当前处理人
    private String currentRole = "";
    //状态
    private String status = "";
    //作业单位名称
    private String sectionName = "";
    //不通过理由
    private String reason = "";
    //返工日志列表
    private List<RefuseLog> refuseLogList;
    //处理前的图片
    private ArrayList<PicData> beforeTreatment;
    //处理后的图片
    private ArrayList<PicData> afterTreatment;
    //附件列表
    private ArrayList<FileData> attachmentFileList;

    public static class RefuseLog {
        private Long refuseId;
        private Integer objectId;
        private String objectType = "";
        //理由
        private String refuseOpinion = "";
        //时间
        private String createTime = "";

        public Long getRefuseId() {
            return refuseId;
        }

        public void setRefuseId(Long refuseId) {
            this.refuseId = refuseId;
        }

        public Integer getObjectId() {
            return objectId;
        }

        public void setObjectId(Integer objectId) {
            this.objectId = objectId;
        }

        public String getObjectType() {
            return objectType;
        }

        public void setObjectType(String objectType) {
            this.objectType = objectType;
        }

        public String getRefuseOpinion() {
            return refuseOpinion;
        }

        public void setRefuseOpinion(String refuseOpinion) {
            this.refuseOpinion = refuseOpinion;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }

    public static class FileData{
        //任务单附件id
        private Integer taskFileId;
        //任务单id
        private Integer taskFormId;
        //文件名
        private String fileName = "";
        //文件类型
        private String fileType = "";
        //文件路径
        private String filePath = "";

        public Integer getTaskFileId() {
            return taskFileId;
        }

        public void setTaskFileId(Integer taskFileId) {
            this.taskFileId = taskFileId;
        }

        public Integer getTaskFormId() {
            return taskFormId;
        }

        public void setTaskFormId(Integer taskFormId) {
            this.taskFormId = taskFormId;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }
    }

    public int getWorkFormId() {
        return workFormId;
    }

    public void setWorkFormId(int workFormId) {
        this.workFormId = workFormId;
    }

    public String getNumbering() {
        return numbering;
    }

    public void setNumbering(String numbering) {
        this.numbering = numbering;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getRationale() {
        return rationale;
    }

    public void setRationale(String rationale) {
        this.rationale = rationale;
    }

    public String getSourcesFund() {
        return sourcesFund;
    }

    public void setSourcesFund(String sourcesFund) {
        this.sourcesFund = sourcesFund;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDepartmentHead() {
        return departmentHead;
    }

    public void setDepartmentHead(String departmentHead) {
        this.departmentHead = departmentHead;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getProcessContext() {
        return processContext;
    }

    public void setProcessContext(String processContext) {
        this.processContext = processContext;
    }

    public int getCompanyAccount() {
        return companyAccount;
    }

    public void setCompanyAccount(int companyAccount) {
        this.companyAccount = companyAccount;
    }

    public String getCurrentRole() {
        return currentRole;
    }

    public void setCurrentRole(String currentRole) {
        this.currentRole = currentRole;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<RefuseLog> getRefuseLogList() {
        return refuseLogList;
    }

    public void setRefuseLogList(List<RefuseLog> refuseLogList) {
        this.refuseLogList = refuseLogList;
    }

    public ArrayList<PicData> getBeforeTreatment() {
        return beforeTreatment;
    }

    public void setBeforeTreatment(ArrayList<PicData> beforeTreatment) {
        this.beforeTreatment = beforeTreatment;
    }

    public ArrayList<PicData> getAfterTreatment() {
        return afterTreatment;
    }

    public void setAfterTreatment(ArrayList<PicData> afterTreatment) {
        this.afterTreatment = afterTreatment;
    }

    public ArrayList<FileData> getAttachmentFileList() {
        return attachmentFileList;
    }

    public void setAttachmentFileList(ArrayList<FileData> attachmentFileList) {
        this.attachmentFileList = attachmentFileList;
    }
}
