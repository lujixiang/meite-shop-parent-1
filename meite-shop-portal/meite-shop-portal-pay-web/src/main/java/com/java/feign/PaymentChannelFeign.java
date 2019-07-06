package com.java.feign;

import com.java.pay.out.dto.PaymentChannelDTO;
import com.java.pay.service.PaymentChannelService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("app-pay")
public interface PaymentChannelFeign extends PaymentChannelService {

    @GetMapping("/selectAll")
    List<PaymentChannelDTO> selectAll();

}
