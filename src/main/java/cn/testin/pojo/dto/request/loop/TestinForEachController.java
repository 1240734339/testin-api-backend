package cn.testin.pojo.dto.request.loop;

import lombok.Data;

@Data
public class TestinForEachController {
    private String inputVal;
    private String returnVal;
    private String interval;
    private Object requestResult;

}
