package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.TicketInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 投票管理 service层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
public interface ITicketInfoService extends IService<TicketInfo> {

    /**
     * 分页获取投票管理信息
     *
     * @param page       分页对象
     * @param ticketInfo 投票管理信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryTicketPage(Page<TicketInfo> page, TicketInfo ticketInfo);

    /**
     * 获取投票详情
     *
     * @param id 投票ID
     * @return 结果
     */
    LinkedHashMap<String, Object> queryDetail(Integer id);
}
