package cn.testin.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class TreeNodeDTO<T> {
    private String id;

    private String projectId;

    private String name;

    private String parentId;

    private Integer level;

    private Long createTime;

    private Long updateTime;

    private Double pos;

    private String label;

    private List<T> children;

    private Integer caseNum;

    private String type;

    private static final long serialVersionUID = 1L;
}
