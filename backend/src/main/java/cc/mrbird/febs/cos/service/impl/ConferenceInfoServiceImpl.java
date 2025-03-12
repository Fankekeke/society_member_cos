package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ConferenceInfo;
import cc.mrbird.febs.cos.dao.ConferenceInfoMapper;
import cc.mrbird.febs.cos.entity.StaffInfo;
import cc.mrbird.febs.cos.service.IConferenceInfoService;
import cc.mrbird.febs.cos.service.IStaffInfoService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 会议记录 实现层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConferenceInfoServiceImpl extends ServiceImpl<ConferenceInfoMapper, ConferenceInfo> implements IConferenceInfoService {

    private final IStaffInfoService staffInfoService;

    /**
     * 分页获取会议记录
     *
     * @param page           分页对象
     * @param conferenceInfo 会议记录
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryConferencePage(Page<ConferenceInfo> page, ConferenceInfo conferenceInfo) {
        return baseMapper.queryConferencePage(page,conferenceInfo);
    }

    /**
     * 查询会议记录详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> queryDetail(Integer id) {
        // 会议信息
        ConferenceInfo conferenceInfo = this.getById(id);
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("conferenceInfo", conferenceInfo);
                put("staffList", Collections.emptyList());
            }
        };
        // 获取参加会议员工
        if (StrUtil.isNotEmpty(conferenceInfo.getStaffIds())) {
            List<String> staffIds = StrUtil.split(conferenceInfo.getStaffIds(), ",");
            List<StaffInfo> staffInfoList = new ArrayList<>(staffInfoService.listByIds(staffIds));
            result.put("staffList", staffInfoList);
        }
        return result;
    }
}
