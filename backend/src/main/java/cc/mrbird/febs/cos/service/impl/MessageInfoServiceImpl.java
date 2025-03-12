package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.MessageInfo;
import cc.mrbird.febs.cos.dao.MessageInfoMapper;
import cc.mrbird.febs.cos.service.IMessageInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com
 */
@Service
public class MessageInfoServiceImpl extends ServiceImpl<MessageInfoMapper, MessageInfo> implements IMessageInfoService {

    /**
     * 分页获取消息信息
     *
     * @param page        分页对象
     * @param messageInfo 消息信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryMessagePage(Page<MessageInfo> page, MessageInfo messageInfo) {
        return baseMapper.queryMessagePage(page, messageInfo);
    }

    /**
     * 查询消息信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> messageListById(Integer userId) {
        return baseMapper.messageListById(userId);
    }

    /**
     * 查找聊天记录
     *
     * @param takeUser 发送者
     * @param sendUser 接收人
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> getMessageDetail(Integer takeUser, Integer sendUser) {
        return baseMapper.getMessageDetail(takeUser, sendUser);
    }

    /**
     * 根据用户编号获取联系人
     *
     * @param userId 用户编号
     * @param flag
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectContactPerson(Integer userId, Integer flag) {
        return baseMapper.selectContactPerson(userId, flag);
    }

    /**
     * 查询聊天记录
     *
     * @param expertCode
     * @param enterpriseCode
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectChatList(String expertCode, String enterpriseCode) {
        return baseMapper.selectChatList(expertCode, enterpriseCode);
    }

}
