package com.springboot.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈规则请求参数〉
 *
 * @author v-zhaojingbo.ea
 * @create 2019/4/1
 * @since 1.0.0
 */
@Data
@ApiModel("根据规则查询员工")
public class RuleRequest {
    @ApiModelProperty("规则编码")
    @NotNull
    private String ruleCode;
    @NotEmpty
    private List<RuleData> ruleData;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RuleData{
        @ApiModelProperty("规则编码")
        private Integer area;
        @ApiModelProperty("规则编码")
        private String departmentId;
        @ApiModelProperty("规则编码")
        private String paymentCode;

        public RuleData(Integer area) {
            this.area = area;
        }
    }

}
