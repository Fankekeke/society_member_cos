package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.NotifyInfo;
import cc.mrbird.febs.cos.dao.NotifyInfoMapper;
import cc.mrbird.febs.cos.entity.StaffInfo;
import cc.mrbird.febs.cos.service.INotifyInfoService;
import cc.mrbird.febs.cos.service.IStaffInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NotifyInfoServiceImpl extends ServiceImpl<NotifyInfoMapper, NotifyInfo> implements INotifyInfoService {

    private final IStaffInfoService staffInfoService;

    /**
     * 分页获取消息通知信息
     *
     * @param page       分页对象
     * @param notifyInfo 消息通知信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryNotifyPage(Page<NotifyInfo> page, NotifyInfo notifyInfo) {
        return baseMapper.queryNotifyPage(page, notifyInfo);
    }

    /**
     * 添加消息通知
     *
     * @param userId  用户ID
     * @param content 内容
     * @return 结果
     */
    @Override
    public boolean addNotify(Integer userId, String content) {
        // 获取员工信息
        StaffInfo staffInfo = staffInfoService.getById(userId);
        if (staffInfo == null) {
            return false;
        }

        NotifyInfo notifyInfo = new NotifyInfo();
        notifyInfo.setUserCode(staffInfo.getCode());
        notifyInfo.setUserId(staffInfo.getId());
        notifyInfo.setStaffId(staffInfo.getId());
        notifyInfo.setStatus("0");
        notifyInfo.setContent(content);
        notifyInfo.setDelFlag(0);
        notifyInfo.setCreateDate(DateUtil.formatDateTime(new Date()));

        return this.save(notifyInfo);
    }

    /**
     * 根据用户ID获取消息通知信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> queryNotifyByUser(Integer userId) {
        return baseMapper.queryNotifyByUser(userId);
    }
}
