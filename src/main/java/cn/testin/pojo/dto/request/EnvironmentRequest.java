package cn.testin.pojo.dto.request;

import cn.testin.pojo.vo.request.base.BaseQueryRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnvironmentRequest extends BaseQueryRequest {

    private String name;
}
