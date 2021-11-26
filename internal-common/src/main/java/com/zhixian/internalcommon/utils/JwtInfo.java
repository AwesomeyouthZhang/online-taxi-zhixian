package com.zhixian.internalcommon.utils;

import lombok.Builder;
import lombok.Data;

/**
 * jwt实体类
 */
@Data
@Builder
public class JwtInfo {
    String subject;
    Long issueDate;
}
