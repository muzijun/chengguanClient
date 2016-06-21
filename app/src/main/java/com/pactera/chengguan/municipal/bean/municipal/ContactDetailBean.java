package com.pactera.chengguan.municipal.bean.municipal;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.model.municipal.ContactDetail;
import com.pactera.chengguan.municipal.model.municipal.PicData;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取任务联系单详情
 * Created by huang hua
 * 2016/5/5.
 */
public class ContactDetailBean extends BaseBean {

    public Data datas;

    public static class Data{
        //任务联系单ID
        public int id;
        //作业单位ID
        public int sectionId;
        //任务联系标题
        public String title = "";
        //工期
        public String schedule = "";
        //工作任务描述
        public String description = "";
        //状态
        public String status = "";
        //项目经理
        public String projectManager = "";
        //上报日期
        public String reportDate = "";
        //业主单位
        public String owner = "";
        //签证情况描述
        public String ownerDescription = "";
        //下达作业单位id
        public String issuedCompanyId = "";
        //业主方的工期
        public String ownerSchedule = "";
        //部门负责人
        public String departmentHead = "";
        //资金来源
        public String sourcesFund = "";
        //分管领导意见
        public String leaderOpinion = "";
        //下发日期
        public String issuedDate = "";
        //处理内容
        public String processContext = "";
        //联系单类型
        public String contractType = "";
        //作业账户
        public Integer companyAccount;
        //处理账户
        public Integer dealOperator;
        //当前处理人
        public String currentRole = "";
        //作业单位名称
        public String sectionName = "";
        //不通过理由
        public String reason = "";
        //附件路径列表
        public List<FileData> attachedList;
        //处理前的图片
        public List<PhotoData> beforeTreatment;
        //处理后的图片
        public List<PhotoData> afterTreatment;
        //返工日志列表
        public List<RefuseLog> refuseLogList;

        public static class FileData{
            //任务联系单附件id
            public Integer taskFileId;
            //任务联系单id
            public Integer taskFormId;
            //文件名
            public String fileName = "";
            //文件类型
            public String fileType = "";
            //文件路径
            public String filePath = "";

            public ContactDetail.FileData transformToDetailFile(){
                ContactDetail.FileData file = new ContactDetail.FileData();
                file.setTaskFileId(taskFileId);
                file.setTaskFormId(taskFormId);
                file.setFileName(fileName);
                file.setFileType(fileType);
                file.setFilePath(filePath);
                return file;
            }
        }

        public static class PhotoData{
            public Integer contactFileId;
            public Integer contactFormId;
            public String fileName = "";
            public String fileType = "";
            public String filePath = "";

            public PicData transformToPicData(){
                PicData picData = new PicData();
                picData.setUrl(filePath);
                picData.setName(fileName);
                return picData;
            }
        }

        public static class RefuseLog{
            public Long refuseId;
            public Integer objectId;
            public String objectType = "";
            //理由
            public String refuseOpinion = "";
            //时间
            public String createTime = "";

            public ContactDetail.RefuseLog transformToRefuseLog(){
                ContactDetail.RefuseLog log = new ContactDetail.RefuseLog();
                log.setRefuseId(refuseId);
                log.setObjectId(objectId);
                log.setObjectType(objectType);
                log.setRefuseOpinion(refuseOpinion);
                log.setCreateTime(createTime);
                return log;
            }
        }

        public ContactDetail transformToDetail(){
            ContactDetail detail = new ContactDetail();
            detail.setId(id);
            detail.setSectionId(sectionId);
            detail.setTitle(title);
            detail.setSchedule(schedule);
            detail.setDescription(description);
            detail.setStatus(status);
            detail.setProjectManager(projectManager);
            detail.setReportDate(reportDate);
            detail.setOwner(owner);
            detail.setOwnerDescription(ownerDescription);
            detail.setIssuedCompanyId(issuedCompanyId);
            detail.setOwnerSchedule(ownerSchedule);
            detail.setDepartmentHead(departmentHead);
            detail.setSourcesFund(sourcesFund);
            detail.setLeaderOpinion(leaderOpinion);
            detail.setIssuedDate(issuedDate);
            detail.setProcessContext(processContext);
            detail.setContractType(contractType);
            detail.setCompanyAccount(companyAccount);
            detail.setDealOperator(dealOperator);
            detail.setCurrentRole(currentRole);
            detail.setSectionName(sectionName);
            detail.setReason(reason);
            ArrayList<ContactDetail.FileData> fileList = new ArrayList<ContactDetail.FileData>();
            if(attachedList != null && attachedList.size() > 0){
                for(FileData fileData : attachedList){
                    ContactDetail.FileData file = fileData.transformToDetailFile();
                    fileList.add(file);
                }
            }
            detail.setAttachmentList(fileList);
            ArrayList<PicData> beforeList = new ArrayList<PicData>();
            if(beforeTreatment != null && beforeTreatment.size() > 0){
                for(PhotoData photo : beforeTreatment){
                    PicData picData = photo.transformToPicData();
                    beforeList.add(picData);
                }
            }
            detail.setBeforeTreatment(beforeList);
            ArrayList<PicData> afterList = new ArrayList<PicData>();
            if(afterTreatment != null && afterTreatment.size() > 0){
                for(PhotoData photo : afterTreatment){
                    PicData picData = photo.transformToPicData();
                    afterList.add(picData);
                }
            }
            detail.setAfterTreatment(afterList);
            List<ContactDetail.RefuseLog> refuseList = new ArrayList<ContactDetail.RefuseLog>();
            if(refuseLogList != null && refuseLogList.size() > 0){
                for(RefuseLog log : refuseLogList){
                    ContactDetail.RefuseLog refuseLog = log.transformToRefuseLog();
                    refuseList.add(refuseLog);
                }
            }
            detail.setRefuseLogList(refuseList);
            return detail;
        }

    }

}
