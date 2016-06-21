package com.pactera.chengguan.municipal.model.municipal;
import java.util.ArrayList;
import java.util.List;

/**
 * 任务联系单详情
 * Created by huang hua
 * 2016/5/9.
 */
public class ContactDetail {

    /**
     * 新增
     */
    public static final String CONTACTFORM_STATUS_NEW = "new";

    /**
     * 待审核
     */
    public static final String CONTACTFORM_STATUS_AUDIT = "audit";

    /**
     * 施工
     */
    public static final String CONTACTFORM_STATUS_BUILDE = "builde";

    /**
     * 待验收
     */
    public static final String CONTACTFORM_STATUS_ACCEPT = "accept";

    /**
     * 办结
     */
    public static final String CONTACTFORM_STATUS_FINISH = "finish";

    //任务联系单ID
    private int id;
    //作业单位ID
    private int sectionId;
    //任务联系标题
    private String title = "";
    //工期
    private String schedule = "";
    //工作任务描述
    private String description = "";
    //状态
    private String status = "";
    //项目经理
    private String projectManager = "";
    //上报日期
    private String reportDate = "";
    //业主单位
    private String owner = "";
    //签证情况描述
    private String ownerDescription = "";
    //下达作业单位id
    private String issuedCompanyId = "";
    //业主方的工期
    private String ownerSchedule = "";
    //部门负责人
    private String departmentHead = "";
    //资金来源
    private String sourcesFund = "";
    //分管领导意见
    private String leaderOpinion = "";
    //下发日期
    private String issuedDate = "";
    //处理内容
    private String processContext = "";
    //联系单类型
    private String contractType = "";
    //作业账户
    private Integer companyAccount;
    //处理账户
    private Integer dealOperator;
    //当前处理人
    private String currentRole = "";
    //作业单位名称
    private String sectionName = "";
    //不通过理由
    private String reason = "";
    //附件路径列表
    private ArrayList<FileData> attachmentList;
    //处理前的图片
    private ArrayList<PicData> beforeTreatment;
    //处理后的图片
    private ArrayList<PicData> afterTreatment;
    //返工日志列表
    private List<RefuseLog> refuseLogList;

    public static class FileData{
        //任务联系单附件id
        private Integer taskFileId;
        //任务联系单id
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

    public static class RefuseLog{
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerDescription() {
        return ownerDescription;
    }

    public void setOwnerDescription(String ownerDescription) {
        this.ownerDescription = ownerDescription;
    }

    public String getIssuedCompanyId() {
        return issuedCompanyId;
    }

    public void setIssuedCompanyId(String issuedCompanyId) {
        this.issuedCompanyId = issuedCompanyId;
    }

    public String getOwnerSchedule() {
        return ownerSchedule;
    }

    public void setOwnerSchedule(String ownerSchedule) {
        this.ownerSchedule = ownerSchedule;
    }

    public String getDepartmentHead() {
        return departmentHead;
    }

    public void setDepartmentHead(String departmentHead) {
        this.departmentHead = departmentHead;
    }

    public String getSourcesFund() {
        return sourcesFund;
    }

    public void setSourcesFund(String sourcesFund) {
        this.sourcesFund = sourcesFund;
    }

    public String getLeaderOpinion() {
        return leaderOpinion;
    }

    public void setLeaderOpinion(String leaderOpinion) {
        this.leaderOpinion = leaderOpinion;
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

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public Integer getCompanyAccount() {
        return companyAccount;
    }

    public void setCompanyAccount(Integer companyAccount) {
        this.companyAccount = companyAccount;
    }

    public Integer getDealOperator() {
        return dealOperator;
    }

    public void setDealOperator(Integer dealOperator) {
        this.dealOperator = dealOperator;
    }

    public String getCurrentRole() {
        return currentRole;
    }

    public void setCurrentRole(String currentRole) {
        this.currentRole = currentRole;
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

    public ArrayList<FileData> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(ArrayList<FileData> attachmentList) {
        this.attachmentList = attachmentList;
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

    public List<RefuseLog> getRefuseLogList() {
        return refuseLogList;
    }

    public void setRefuseLogList(List<RefuseLog> refuseLogList) {
        this.refuseLogList = refuseLogList;
    }
}
