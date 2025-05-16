package com.commerce.inventory_service.dto;

import com.commerce.inventory_service.utils.NamingUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.UUID;

import java.lang.reflect.Field;
import java.util.Map;

@Getter
@Setter
public class ProductFilterInputDTO {

    private static final String UUID_REGEX =
            "^([0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})(\\|[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})*$";

    @Pattern(regexp = UUID_REGEX, message = "The format must be in the form 'uuid' or 'uuid|uuid'")
    private String brandId; // p_123:213735|581378

    @UUID
    private String departmentId;

    @Pattern(regexp = UUID_REGEX, message = "The format must be in the form 'uuid' or 'uuid|uuid'")
    private String productStatusId;

    @Pattern(regexp = "^([a-zA-Z0-9-]{4,20})(\\|[a-zA-Z0-9-]{4,20})*$", message = "The format must be in the form 'sku' or 'sku|sku'")
    private String sku;

    @Pattern(regexp = "^\\d+-\\d+$", message = "The format must be in the form 'numbers-numbers', for example, '700-1600'")
    private String price; // p_36:700-1600

    @Pattern(regexp = "^[a-z_]+-(asc|desc)$", message = "The format must be int the form 'created_at-desc'")
    private String orderBy = "created_at-desc"; // price-desc-rank o price=asc-rank

    @PositiveOrZero
    private Integer page = 0;

    @Positive
    private Integer size = 20;

    public String redisKey() {
        StringBuilder result = new StringBuilder();

        Field[] fields = this.getClass().getDeclaredFields();
        try {
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);

                if (fields[i].get(this) != null && fields[i].get(this) != UUID_REGEX) {
                    result.append(NamingUtil.toKebabCase(fields[i].getName())).append(":").append(fields[i].get(this));
                    if (i != fields.length - 1) {
                        result.append(":");
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}
