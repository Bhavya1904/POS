package com.pos.service.impl;

import com.pos.mapper.RefundMapper;
import com.pos.model.Branch;
import com.pos.model.Order;
import com.pos.model.Refund;
import com.pos.model.User;
import com.pos.payload.dto.RefundDTO;
import com.pos.repository.OrderRepository;
import com.pos.repository.RefundRepository;
import com.pos.service.RefundService;
import com.pos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {

    private final UserService userService;
    private final RefundRepository refundRepository;
    private final OrderRepository orderRepository;

    @Override
    public RefundDTO createRefund(RefundDTO refundDTO) throws Exception {
        User cashier = userService.getCurrentUser();

        Order order = orderRepository.findById(refundDTO.getOrderId()).orElseThrow(
                () -> new Exception("Order not found")
        );

        Branch branch = cashier.getBranch();

        Refund createdRefund = Refund.builder()
                .order(order)
                .cashier(cashier)
                .branch(branch)
                .reason(refundDTO.getReason())
                .amount(refundDTO.getAmount())
                .createdAt(refundDTO.getCreatedAt())
                .build();
        Refund savedRefund = refundRepository.save(createdRefund);
        return RefundMapper.toDTO(savedRefund);
    }

    @Override
    public List<RefundDTO> getAllRefunds() throws Exception {
        return refundRepository.findAll().stream()
                .map(RefundMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<RefundDTO> getRefundByCashierId(Long cashierId) throws Exception {
        return refundRepository.findByCashierId(cashierId).stream()
                .map(RefundMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<RefundDTO> getRefundByShiftReport(Long shiftReportId) throws Exception {
        return refundRepository.findByShiftReportId(shiftReportId).stream()
                .map(RefundMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<RefundDTO> getRefundByCashierIdAndDateRange(Long cashierId,
                                                          LocalDateTime startDate,
                                                          LocalDateTime endDate) throws Exception {
        return refundRepository.findByCashierIdAndCreatedAtBetween(
                cashierId, startDate, endDate
        ).stream().map(RefundMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<RefundDTO> getRefundByBranch(Long branchId) throws Exception {
        return refundRepository.findByBranchId(branchId).stream()
                .map(RefundMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public RefundDTO getRefundById(Long id) throws Exception {
        return refundRepository.findById(id).map(RefundMapper :: toDTO).orElseThrow(
                () -> new Exception("Refund not found")
        );
    }

    @Override
    public void deleteRefund(Long id) throws Exception {
        this.getRefundById(id);
        refundRepository.deleteById(id);
    }
}



