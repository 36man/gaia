package org.bravo.gaia.commons.base;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.bravo.gaia.commons.money.MultiCurrencyMoney;

import java.io.Serial;

/**
 * 多币种金额DTO
 * @author lijian
 * @since 2021/07/13
 */
@Setter
@Getter
public class MultiCurrencyMoneyDTO extends BaseDTO {

    /** serialVersionUID */
    @Serial
    private static final long serialVersionUID = 3617732088592394682L;

    /** 金额 */
    private String            amount;

    /** 货币 */
    private String            currency;

    /** 分 */
    private String            cent;

    /**
     * 将多币种金额对象转成VO
     */
    public static MultiCurrencyMoneyDTO convert2DTO(MultiCurrencyMoney multiCurrencyMoney) {
        if (multiCurrencyMoney == null) {
            return null;
        }

        MultiCurrencyMoneyDTO multiCurrencyMoneyVO = new MultiCurrencyMoneyDTO();

        multiCurrencyMoneyVO.setAmount(multiCurrencyMoney.getAmount().toString());
        multiCurrencyMoneyVO.setCurrency(multiCurrencyMoney.getCurrencyValue());
        multiCurrencyMoneyVO.setCent(String.valueOf(multiCurrencyMoney.getCent()));

        return multiCurrencyMoneyVO;
    }

    /**
     * 将多币种金额对象VO转成普通对象
     */
    public static MultiCurrencyMoney convert2Domain(MultiCurrencyMoneyDTO multiCurrencyMoneyVO) {
        if (multiCurrencyMoneyVO == null) {
            return null;
        }

        if (StringUtils.isEmpty(multiCurrencyMoneyVO.getCent())) {
            throw new IllegalArgumentException("cent is null");
        }
        if (StringUtils.isEmpty(multiCurrencyMoneyVO.getCurrency())) {
            throw new IllegalArgumentException("currency is null");
        }

        return new MultiCurrencyMoney(Long.parseLong(multiCurrencyMoneyVO.getCent()),
                multiCurrencyMoneyVO.getCurrency());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
