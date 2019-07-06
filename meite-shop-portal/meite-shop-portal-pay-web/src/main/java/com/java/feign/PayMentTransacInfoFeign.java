package com.java.feign;

import com.java.core.base.Result;
import com.java.pay.out.dto.PayMentTransacDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("app-pay")
public interface PayMentTransacInfoFeign {

    @GetMapping("/tokenByPayMentTransac")
    Result<PayMentTransacDTO> tokenByPayMentTransac(@RequestParam("token") String token);

}
