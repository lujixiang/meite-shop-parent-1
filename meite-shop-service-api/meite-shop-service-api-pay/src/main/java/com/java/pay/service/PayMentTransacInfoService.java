package com.java.pay.service;

import com.java.core.base.Result;
import com.java.pay.out.dto.PayMentTransacDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author jiangli
 * @date 2019/7/6 12:21
 */
public interface PayMentTransacInfoService {

    @GetMapping("/tokenByPayMentTransac")
    Result<PayMentTransacDTO> tokenByPayMentTransac(@RequestParam("token") String token);
}
