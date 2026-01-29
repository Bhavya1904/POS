package com.pos.service;

import com.pos.model.Refund;
import com.pos.payload.dto.RefundDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface RefundService {

    RefundDTO createRefund(RefundDTO refundDTO) throws Exception;
    List<RefundDTO> getAllRefunds() throws Exception;
    List<RefundDTO> getRefundByCashierId(Long cashierId) throws Exception;
    List<RefundDTO> getRefundByShiftReport(Long shiftReportId) throws Exception;
    List<RefundDTO> getRefundByCashierIdAndDateRange(Long cashierId,
                                                   LocalDateTime startDate,
                                                   LocalDateTime endDate) throws Exception;
    List<RefundDTO> getRefundByBranch(Long branchId) throws Exception;
    RefundDTO getRefundById(Long id) throws Exception;
    void deleteRefund(Long id) throws Exception;

}


