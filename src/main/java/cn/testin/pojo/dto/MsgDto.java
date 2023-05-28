package cn.testin.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @title: MsgDto
 * @description: 用来传递消息
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MsgDto {

    private String reportId;
    private String toReport;
    private boolean execEnd;
    private String content;
}
