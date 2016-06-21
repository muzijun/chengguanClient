package com.pactera.chengguan.municipal.config;

import com.pactera.chengguan.municipal.model.municipal.PatrolRecord;

/**巡查监控接口
 * Created by lijun on 2016/4/12.
 */
public interface MonitorListener {
    //改变状态
    public abstract void Clickstate(PatrolRecord patrolRecord, int position);
    //扣分
    public abstract void Clickpoints(PatrolRecord patrolRecord);
}
