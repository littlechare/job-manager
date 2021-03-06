package com.littlehow.job.manager.model.job;

import java.util.Date;

public class OperationLog {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job..operation_log.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job..operation_log.oper_id
     *
     * @mbggenerated
     */
    private String operId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job..operation_log.task_id
     *
     * @mbggenerated
     */
    private String taskId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job..operation_log.business_id
     *
     * @mbggenerated
     */
    private String businessId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job..operation_log.oper_type
     *
     * @mbggenerated
     */
    private Byte operType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job..operation_log.oper_time
     *
     * @mbggenerated
     */
    private Date operTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job..operation_log.id
     *
     * @return the value of job..operation_log.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job..operation_log.id
     *
     * @param id the value for job..operation_log.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job..operation_log.oper_id
     *
     * @return the value of job..operation_log.oper_id
     *
     * @mbggenerated
     */
    public String getOperId() {
        return operId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job..operation_log.oper_id
     *
     * @param operId the value for job..operation_log.oper_id
     *
     * @mbggenerated
     */
    public void setOperId(String operId) {
        this.operId = operId == null ? null : operId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job..operation_log.task_id
     *
     * @return the value of job..operation_log.task_id
     *
     * @mbggenerated
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job..operation_log.task_id
     *
     * @param taskId the value for job..operation_log.task_id
     *
     * @mbggenerated
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job..operation_log.business_id
     *
     * @return the value of job..operation_log.business_id
     *
     * @mbggenerated
     */
    public String getBusinessId() {
        return businessId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job..operation_log.business_id
     *
     * @param businessId the value for job..operation_log.business_id
     *
     * @mbggenerated
     */
    public void setBusinessId(String businessId) {
        this.businessId = businessId == null ? null : businessId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job..operation_log.oper_type
     *
     * @return the value of job..operation_log.oper_type
     *
     * @mbggenerated
     */
    public Byte getOperType() {
        return operType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job..operation_log.oper_type
     *
     * @param operType the value for job..operation_log.oper_type
     *
     * @mbggenerated
     */
    public void setOperType(Byte operType) {
        this.operType = operType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job..operation_log.oper_time
     *
     * @return the value of job..operation_log.oper_time
     *
     * @mbggenerated
     */
    public Date getOperTime() {
        return operTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job..operation_log.oper_time
     *
     * @param operTime the value for job..operation_log.oper_time
     *
     * @mbggenerated
     */
    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }
}