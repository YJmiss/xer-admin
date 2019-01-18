package com.oservice.admin.modules.app.information;

import com.oservice.admin.common.validator.group.DistributionGroup;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @program: oservice
 * @description: 推銷配置信息
 * @author: YJmiss
 * @create: 2019-01-10 09:27
 **/
public class DistributionConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    //分销占比distributeFee
    @NotBlank(message = "分销占比distributeFee不能为空", groups = DistributionGroup.class)
    private String distributeFee;
    //可提现金额cashWithdrawal
    @NotBlank(message = "可提现金额cashWithdrawal不能为空", groups = DistributionGroup.class)
    private String cashWithdrawal;
    //配置时间configTime
    private String configTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDistributeFee() {
        return distributeFee;
    }

    public void setDistributeFee(String distributeFee) {
        this.distributeFee = distributeFee;
    }

    public String getCashWithdrawal() {
        return cashWithdrawal;
    }

    public void setCashWithdrawal(String cashWithdrawal) {
        this.cashWithdrawal = cashWithdrawal;
    }

    public String getConfigTime() {
        return configTime;
    }

    public void setConfigTime(String configTime) {
        this.configTime = configTime;
    }
}
