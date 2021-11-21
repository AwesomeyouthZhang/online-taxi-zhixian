package com.zhixian.servicesms.service.impl;

import com.zhixian.internalcommon.dto.ResponseResult;
import com.zhixian.internalcommon.dto.servicesms.SmsTemplateDto;
import com.zhixian.internalcommon.dto.servicesms.request.SmsSendRequest;
import com.zhixian.servicesms.constant.SmsStatusEnum;
import com.zhixian.servicesms.dao.ServiceSmsRecordDao;
import com.zhixian.servicesms.dao.ServiceSmsTemplateCustomDao;
import com.zhixian.servicesms.entity.ServiceSmsRecord;
import com.zhixian.servicesms.entity.ServiceSmsTemplate;
import com.zhixian.servicesms.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService {
    /**
     * 为社么不把短信缓存于Redis中？
     * 没必要，因为短信内容大小相对固定，直接存数据库中
     */
    private Map<String, String> templateMaps = new HashMap<String, String>();

    @Autowired
    private ServiceSmsTemplateCustomDao serviceSmsTemplateCustomDao;

    @Autowired
    private ServiceSmsRecordDao serviceSmsRecordDao;


    /**
     * @param smsSendRequest
     * @return
     */
    @Override
    public ResponseResult sendSms(SmsSendRequest smsSendRequest) {
        log.info(smsSendRequest.toString());
        List<SmsTemplateDto> templates = smsSendRequest.getData();

        for (String receiverPhoneNumber : smsSendRequest.getReceivers()) {
            ServiceSmsRecord serviceSmsRecord = new ServiceSmsRecord();
            serviceSmsRecord.setPhoneNumber(receiverPhoneNumber);
            for (SmsTemplateDto template : templates) {
                //从数据库加载到缓存中来
                if (!templateMaps.containsKey(template.getId())) {
                    log.info("缓存db短信内容");
                    ServiceSmsTemplate serviceSmsTemplate = serviceSmsTemplateCustomDao.selectByTemplateId(template.getId());
                    templateMaps.put(template.getId(), serviceSmsTemplate.getTemplateContent());


                }

                //替换短信模板中的占位符
                String originContent = templateMaps.get(template.getId());
                for (Map.Entry<String, Object> stringObjectEntry : template.getTemplateMap().entrySet()) {
                    originContent = StringUtils.replace(originContent, "${" + stringObjectEntry.getKey() + "}", "" + stringObjectEntry.getValue());

                }
                //发生错误时为了不影响其他手机号的发送，使用try catch
                try {
                    int result = send(receiverPhoneNumber, template.getId(), template.getTemplateMap());
                    // 组装SMS对象
                    serviceSmsRecord.setSendTime(new Date());
                    serviceSmsRecord.setOperatorName("");
                    serviceSmsRecord.setSendFlag(1);
                    serviceSmsRecord.setSendNumber(0);
                    serviceSmsRecord.setSmsContent(originContent);
                    if (result != SmsStatusEnum.SEND_SUCCESS.getCode()) {
                        throw new Exception("短信发送失败");
                    }
                } catch (Exception e) {
                    serviceSmsRecord.setSendFlag(0);
                    serviceSmsRecord.setSendNumber(1);
                    log.error("发送短信（" + template.getId() + "）失败：" + receiverPhoneNumber, e);
                } finally {
                    serviceSmsRecord.setCreateTime(new Date());
                    serviceSmsRecordDao.insert(serviceSmsRecord);
                }
            }
        }

        return ResponseResult.success("");
    }

    private int send(String receiverPhoneNumber, String templateId, Map<String, Object> templateMap) {
        JSONObject param = new JSONObject();
        param.putAll(templateMap);
        return sendMsg(receiverPhoneNumber, templateId, param.toString());

    }

    /**
     * 供应商 发 短信
     */
    private int sendMsg(String receiverPhoneNumber, String templateId, String toString) {
        return SmsStatusEnum.SEND_SUCCESS.getCode();
    }
}
