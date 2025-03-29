package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.TicketInfo;
import cc.mrbird.febs.cos.dao.TicketInfoMapper;
import cc.mrbird.febs.cos.entity.TicketOptionInfo;
import cc.mrbird.febs.cos.entity.TicketRecord;
import cc.mrbird.febs.cos.service.ITicketInfoService;
import cc.mrbird.febs.cos.service.ITicketOptionInfoService;
import cc.mrbird.febs.cos.service.ITicketRecordService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 投票管理 实现层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TicketInfoServiceImpl extends ServiceImpl<TicketInfoMapper, TicketInfo> implements ITicketInfoService {

    private final ITicketOptionInfoService ticketOptionInfoService;

    private final ITicketRecordService ticketRecordService;

    /**
     * 分页获取投票管理信息
     *
     * @param page       分页对象
     * @param ticketInfo 投票管理信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryTicketPage(Page<TicketInfo> page, TicketInfo ticketInfo) {
        return baseMapper.queryTicketPage(page, ticketInfo);
    }

    /**
     * 获取投票详情
     *
     * @param id 投票ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> queryDetail(Integer id) {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("ticketInfo", null);
                put("ticketOptionInfo", null);
            }
        };
        TicketInfo ticketInfo = this.getById(id);
        if (ticketInfo != null) {
            result.put("ticketInfo", ticketInfo);

            List<TicketOptionInfo> ticketOptionInfo = ticketOptionInfoService.list(Wrappers.<TicketOptionInfo>lambdaQuery().eq(TicketOptionInfo::getTicketId, id));
            List<TicketRecord> ticketRecord = ticketRecordService.list(Wrappers.<TicketRecord>lambdaQuery().eq(TicketRecord::getTicketId, id));
            Map<Integer, List<TicketRecord>> ticketMap = ticketRecord.stream().collect(Collectors.groupingBy(TicketRecord::getOptionId));

            result.put("ticketOptionInfo", ticketOptionInfo);
            List<LinkedHashMap<String, Object>> ticketResult = new ArrayList<>();
            // 处理投票结果
            ticketOptionInfo.forEach(item -> {
                LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>() {
                    {
                        put("name", item.getName());
                        put("count", ticketMap.get(item.getId()) == null ? 0 : ticketMap.get(item.getId()).size());
                    }
                };
                ticketResult.add(map);
            });
            result.put("ticketResult", ticketResult);
        }
        return result;
    }
}
