package com.jack.jvm.oa.impl;

import com.jack.jvm.oa.SalaryCalService;

public class SalaryCalServiceImpl implements SalaryCalService {
    @Override
    public Double cal(Double salary) {
        return salary * 2;
    }
}
