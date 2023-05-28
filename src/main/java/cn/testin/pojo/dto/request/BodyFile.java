package cn.testin.pojo.dto.request;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * @title: BodyFile
 * @description:
 */
@Data
public class BodyFile {

    private String id;
    private String name;
    // LOCAL 和 引用(FILE_REF) / GIT
    private String storage;
    private String fileId;
    private String projectId;
    private String fileType;
    // 正常/已删除
    private String status;

    public boolean isRef() {
        return StringUtils.equals(storage, "file_ref") && StringUtils.isNotEmpty(fileId);
    }
}
