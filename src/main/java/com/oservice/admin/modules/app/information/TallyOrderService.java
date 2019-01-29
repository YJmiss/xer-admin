package com.oservice.admin.modules.app.information;

import com.oservice.admin.common.utils.ConfigConstant;
import com.oservice.admin.modules.sys.service.SysConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;

/**
 * @program: oservice
 * @description: 结算订单
 * @author: YJmiss
 * @create: 2019-01-10 14:50
 **/
@Service("tallyOrderService")
public class TallyOrderService {
    private final static String KEY = ConfigConstant.DISTRIBUTIONCONFIG_CONFIG_KEY;
    @Resource
    private SysConfigService sysConfigService;

    /**
     * @Description: 获取分销佣金
     * @Param: id:课程ID status:订单状态
     * @return:
     * @Author: YJmiss
     * @Date: 2019/1/10
     */
    public Long getBrokerage(Long price) {
        DistributionConfig config = sysConfigService.getConfigObject(KEY, DistributionConfig.class);
        int distributeFee = Integer.parseInt(config.getDistributeFee());
        long df = 0l;
        /*全部佣金(分)*/
        df = (long) (Double.parseDouble(txfloat(distributeFee, 100)) * price);
        return df;
    }

    /**
     * TODO 除法运算，保留小数
     *
     * @param a 被除数
     * @param b 除数
     * @return 商
     * @author YJmiss
     * @date 2019/1/10
     */
    public static String txfloat(int a, int b) {
        // TODO 自动生成的方法存根
        DecimalFormat df = new DecimalFormat("0.00");//设置保留位数
        return df.format((float) a / b);
    }

}
