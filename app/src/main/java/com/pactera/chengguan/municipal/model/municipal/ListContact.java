package com.pactera.chengguan.municipal.model.municipal;

/**
 * 任务联系单列表数据
 * Created by huang hua
 * 2016/5/5.
 */
public class ListContact {

    /**
     * 重要
     */
    public static final String CONTACTFORM_TYPE_IMPORTANT = "important";

    /**
     * 一般
     */
    public static final String CONTACTFORM_TYPE_COMMONLY = "commonly";

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

    //任务联系单id
    private int id;
    //作业单位ID
    private int sectionId;
    //标题
    private String title = "";
    //工期
    private String schedule = "";
    //工作任务描述
    private String description = "";
    //状态 新增，待审核，施工，待验收，办结
    private String status = "";
    //项目经理
    private String projectManager = "";
    //上报日期
    private String reportDate = "";
    //联系单类型 重要或一般
    private String contractType = "";

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

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    /**
     * 是否是重要任务联系单
     * @return
     */
    public boolean hasImportantLogo(){
        return contractType.equals(CONTACTFORM_TYPE_IMPORTANT);
    }
}
