package com.pactera.chengguan.municipal.bean.municipal;


import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.model.municipal.PicData;
import com.pactera.chengguan.municipal.model.municipal.TaskDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取任务单详情Bean
 * Created by huang hua
 * 2016/5/9.
 */
public class TaskDetailBean extends BaseBean {

    public Data datas;

    public static class Data {
        //任务单ID
        public int workFormId;
        //编号
        public String numbering = "";
        //作业单位
        public int sectionId;
        //联系依据
        public String rationale = "";
        //资金来源
        public String sourcesFund = "";
        //完成时间
        public String finishDate = "";
        //工作任务描述
        public String description = "";
        //位置
        public String location = "";
        //业主单位
        public String owner = "";
        //部门负责人
        public String departmentHead = "";
        //下发时间
        public String issuedDate = "";
        //处理内容
        public String processContext = "";
        //作业账户
        public int companyAccount;
        //当前处理人
        public String currentRole = "";
        //状态
        public String status = "";
        //作业单位名称
        public String sectionName = "";
        //不通过理由
        public String reason = "";
        //返工日志列表
        public List<RefuseLog> refuseLogList;
        //处理前的图片
        public List<FileData> beforeTreatment;
        //处理后的图片
        public List<FileData> afterTreatment;
        //附件列表
        public List<FileData> attachmentFileList;

        public static class RefuseLog {
            public Long refuseId;
            public Integer objectId;
            public String objectType = "";
            //理由
            public String refuseOpinion = "";
            //时间
            public String createTime = "";

            public TaskDetail.RefuseLog transformToDetailLog() {
                TaskDetail.RefuseLog refuseLog = new TaskDetail.RefuseLog();
                refuseLog.setRefuseId(refuseId);
                refuseLog.setObjectId(objectId);
                refuseLog.setObjectType(objectType);
                refuseLog.setRefuseOpinion(refuseOpinion);
                refuseLog.setCreateTime(createTime);
                return refuseLog;
            }
        }

        public static class FileData {
            //任务单附件id
            public Integer taskFileId;
            //任务单id
            public Integer taskFormId;
            //文件名
            public String fileName = "";
            //文件类型
            public String fileType = "";
            //文件路径
            public String filePath = "";

            public TaskDetail.FileData transformToDetailData() {
                TaskDetail.FileData fileData = new TaskDetail.FileData();
                fileData.setTaskFileId(taskFileId);
                fileData.setTaskFormId(taskFormId);
                fileData.setFileName(fileName);
                fileData.setFileType(fileType);
                fileData.setFilePath(filePath);
                return fileData;
            }
        }

        public TaskDetail transformToTaskDetail(){
            TaskDetail taskDetail = new TaskDetail();
            taskDetail.setWorkFormId(workFormId);
            taskDetail.setNumbering(numbering);
            taskDetail.setSectionId(sectionId);
            taskDetail.setRationale(rationale);
            taskDetail.setSourcesFund(sourcesFund);
            taskDetail.setFinishDate(finishDate);
            taskDetail.setDescription(description);
            taskDetail.setLocation(location);
            taskDetail.setOwner(owner);
            taskDetail.setDepartmentHead(departmentHead);
            taskDetail.setIssuedDate(issuedDate);
            taskDetail.setProcessContext(processContext);
            taskDetail.setCompanyAccount(companyAccount);
            taskDetail.setCurrentRole(currentRole);
            taskDetail.setStatus(status);
            taskDetail.setSectionName(sectionName);
            taskDetail.setReason(reason);
            List<TaskDetail.RefuseLog> logList = new ArrayList<TaskDetail.RefuseLog>();
            if(refuseLogList != null && refuseLogList.size() > 0){
                for(RefuseLog log : refuseLogList){
                    TaskDetail.RefuseLog refuseLog = log.transformToDetailLog();
                    logList.add(refuseLog);
                }
            }
            taskDetail.setRefuseLogList(logList);
            ArrayList<PicData> beforeList = new ArrayList<PicData>();
            if(beforeTreatment != null && beforeTreatment.size() > 0){
                for(FileData fileData : beforeTreatment){
                    PicData picData = new PicData();
                    picData.setName(fileData.fileName);
                    picData.setUrl(fileData.filePath);
                    beforeList.add(picData);
                }
            }
            taskDetail.setBeforeTreatment(beforeList);
            ArrayList<PicData> afterList = new ArrayList<PicData>();
            if(afterTreatment != null && afterTreatment.size() > 0){
                for(FileData fileData : afterTreatment){
                    PicData picData = new PicData();
                    picData.setName(fileData.fileName);
                    picData.setUrl(fileData.filePath);
                    afterList.add(picData);
                }
            }
            taskDetail.setAfterTreatment(afterList);
            ArrayList<TaskDetail.FileData> fileList = new ArrayList<TaskDetail.FileData>();
            if(attachmentFileList != null && attachmentFileList.size() > 0){
                for(FileData fileData : attachmentFileList){
                    TaskDetail.FileData file = fileData.transformToDetailData();
                    fileList.add(file);
                }
            }
            taskDetail.setAttachmentFileList(fileList);
            return taskDetail;
        }
    }

}
