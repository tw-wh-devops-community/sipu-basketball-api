package com.pusi.basketball.service;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourtPriceServiceTest {

    @Test
    void should_return_court_price() {
        assertEquals(BigDecimal.valueOf(50), CourtPriceService.calPriceByHourAndIsSubCourt(15, true));
        assertEquals(BigDecimal.valueOf(90), CourtPriceService.calPriceByHourAndIsSubCourt(15, false));
        assertEquals(BigDecimal.valueOf(80), CourtPriceService.calPriceByHourAndIsSubCourt(18, true));
        assertEquals(BigDecimal.valueOf(150), CourtPriceService.calPriceByHourAndIsSubCourt(18, false));
        assertEquals(BigDecimal.valueOf(60), CourtPriceService.calPriceByHourAndIsSubCourt(21, true));
        assertEquals(BigDecimal.valueOf(100), CourtPriceService.calPriceByHourAndIsSubCourt(21, false));
    }
}
