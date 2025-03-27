package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.TicketInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 投票管理 mapper层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
public interface TicketInfoMapper extends BaseMapper<TicketInfo> {

    /**
     * 分页获取投票管理信息
     *
     * @param page       分页对象
     * @param ticketInfo 投票管理信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryTicketPage(Page<TicketInfo> page, @Param("ticketInfo") TicketInfo ticketInfo);
}
