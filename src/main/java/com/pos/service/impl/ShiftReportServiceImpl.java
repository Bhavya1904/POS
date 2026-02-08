package com.pos.service.impl;

import com.pos.domain.PaymentType;
import com.pos.exception.UserException;
import com.pos.mapper.ShiftReportMapper;
import com.pos.model.*;
import com.pos.payload.dto.ShiftReportDTO;
import com.pos.repository.*;
import com.pos.service.ShiftReportService;
import com.pos.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftReportServiceImpl implements ShiftReportService {

    private final ShiftReportRepository shiftReportRepository;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final OrderRepository orderRepository;
    private final RefundRepository refundRepository;
    private final UserService userService;

    @Override
    public ShiftReportDTO startShift() throws Exception {
        User currentUser=userService.getCurrentUser();
        LocalDateTime shiftStart=LocalDateTime.now();

//        User cashier = userRepository.findById(cashierId).orElseThrow(() ->
//                new RuntimeException("Cashier not found with ID:" + cashierId));

//        Branch branch = branchRepository.findById(branchId).orElseThrow(() ->
//                new RuntimeException("Branch not found with ID: " + branchId));

        // Prevent duplicate shifts on the same day
        LocalDateTime startOfDay = shiftStart.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = shiftStart.withHour(23).withMinute(59).withSecond(59);

        Optional<ShiftReport> existing = shiftReportRepository
                .findByCashierAndShiftStartBetween(currentUser, startOfDay, endOfDay);

        if (existing.isPresent()) {
            throw new RuntimeException("Shift already started today.");
        }

        Branch branch = currentUser.getBranch();
        if (branch == null) {
            throw new RuntimeException("Branch not found");
        }
        ShiftReport shift = ShiftReport.builder()
                .cashier(currentUser)
                .branch(branch)
                .shiftStart(shiftStart)
                .build();

        ShiftReport savedReport = shiftReportRepository.save(shift);
        return ShiftReportMapper.toDTO(savedReport);
    }

    @Override
    @Transactional
    public ShiftReportDTO endShift(Long shiftReportId, LocalDateTime shiftEnd) throws UserException {
        User currentUser=userService.getCurrentUser();

//        ShiftReport shift = shiftReportRepository.findById(shiftReportId)
//                .orElseThrow(() -> new RuntimeException("Shift report not found"));

        ShiftReport shift=shiftReportRepository
                .findTopByCashierAndShiftEndIsNullOrderByShiftStartDesc(currentUser)
                .orElseThrow(
                        ()-> new EntityNotFoundException("shift report not found")
                );

        shift.setShiftEnd(shiftEnd);

        List<Order> orders = orderRepository.findByCashierAndCreatedAtBetween(
                currentUser, shift.getShiftStart(), shift.getShiftEnd()
        );

        List<Refund> refunds = refundRepository.findByCashierIdAndCreatedAtBetween(
                currentUser.getId(), shift.getShiftStart(), shiftEnd
        );

        double totalRefunds = refunds.stream()
                .mapToDouble(refund -> refund.getAmount() != null ? refund.getAmount() : 0.0)
                .sum();

        double totalSales = orders.stream().mapToDouble(Order::getTotalAmount).sum();
        int totalOrders = orders.size();
        double netSales = totalSales - totalRefunds;

        shift.setTotalSales(totalSales);
        shift.setTotalOrders(totalOrders);
        shift.setTotalRefunds(totalRefunds);
        shift.setNetSales(netSales);
        shift.setRecentOrders(getRecentOrders(orders));
        shift.setTopSellingProducts(getTopSellingProducts(orders));
        shift.setPaymentSummaries(getPaymentSummaries(orders, totalSales));
        shift.setRefunds(refunds);

        ShiftReport savedReport = shiftReportRepository.save(shift);
        return ShiftReportMapper.toDTO(savedReport);
    }

    @Override
    public ShiftReportDTO getShiftReportById(Long id) throws Exception {
        return shiftReportRepository.findById(id)
                .map(ShiftReportMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Shift report not found"));
    }

    @Override
    public List<ShiftReportDTO> getAllShiftReports() {
        List<ShiftReport> allShiftReports = shiftReportRepository.findAll();
        return allShiftReports.stream().map(ShiftReportMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ShiftReportDTO> getShiftReportsByCashierId(Long cashierId) throws Exception {
        User cashier = userRepository.findById(cashierId)
                .orElseThrow(() -> new RuntimeException("Cashier not found"));
        List<ShiftReport> allShiftReports = shiftReportRepository.findByCashierId(cashierId);
        return allShiftReports.stream().map(ShiftReportMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ShiftReportDTO> getShiftReportsByBranchId(Long branchId) throws Exception{
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Branch not found"));
        List<ShiftReport> allShiftReports = shiftReportRepository.findByBranchId(branchId);
        return allShiftReports.stream().map(ShiftReportMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ShiftReportDTO getCurrentShiftProgress(Long cashierId) throws UserException {
        User cashier=userService.getCurrentUser();
//        User cashier = userRepository.findById(cashierId)
//                .orElseThrow(() -> new RuntimeException("Cashier not found"));

        ShiftReport shift = shiftReportRepository
                .findTopByCashierAndShiftEndIsNullOrderByShiftStartDesc(cashier)
                .orElseThrow(() -> new RuntimeException("No active shift found for this cashier"));

        LocalDateTime now = LocalDateTime.now();

        List<Order> orders = orderRepository.findByCashierAndCreatedAtBetween(
                cashier, shift.getShiftStart(), now
        );

        List<Refund> refunds = refundRepository.findByCashierIdAndCreatedAtBetween(
                cashier.getId(), shift.getShiftStart(), now
        );

        double totalSales = orders.stream().mapToDouble(Order::getTotalAmount).sum();
        int totalOrders = orders.size();
//        double totalRefunds = refunds.stream().mapToDouble(Refund::getAmount).sum();
        double totalRefunds = refunds.stream()
                .mapToDouble(refund -> refund.getAmount() != null ? refund.getAmount() : 0.0)
                .sum();

        double netSales = totalSales - totalRefunds;

        shift.setTotalSales(totalSales);
        shift.setTotalOrders(totalOrders);
        shift.setTotalRefunds(totalRefunds);
        shift.setNetSales(netSales);
        shift.setRecentOrders(getRecentOrders(orders));
        shift.setTopSellingProducts(getTopSellingProducts(orders));

        shift.setPaymentSummaries(getPaymentSummaries(orders, totalSales));
        shift.setRefunds(refunds);

        ShiftReport savedReport = shiftReportRepository.save(shift);

        return ShiftReportMapper.toDTO(savedReport);
    }

    @Override
    public ShiftReportDTO getShiftReportByCashierAndDate(Long cashierId, LocalDateTime date) throws Exception {
        User cashier = userRepository.findById(cashierId)
                .orElseThrow(() -> new RuntimeException("Cashier not found"));

        LocalDateTime start = date.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime end = date.withHour(23).withMinute(59).withSecond(59);

        ShiftReport report = shiftReportRepository.findByCashierAndShiftStartBetween(
                        cashier, start, end)
                .orElseThrow(()
                        -> new RuntimeException("No shift report found on this date")
                );

        return ShiftReportMapper.toDTO(report);
    }

    @Override
    public void deleteShiftReport(Long id) {
        if (!shiftReportRepository.existsById(id)) {
            throw new RuntimeException("Shift report not found");
        }
        shiftReportRepository.deleteById(id);
    }

    // ----------------- HELPER METHODS -----------------

    private List<Order> getRecentOrders(List<Order> orders) {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getCreatedAt).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    private List<Product> getTopSellingProducts(List<Order> orders) {
        Map<Product, Integer> productSalesMap = new HashMap<>();

        for (Order order : orders) {
            for (OrderItem item : order.getItems()) {
                Product product = item.getProduct();
                productSalesMap.put(product, productSalesMap.getOrDefault(product, 0) + item.getQuantity());
            }
        }

        return productSalesMap.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<PaymentSummary> getPaymentSummaries(List<Order> orders,
                                                     double totalSales) {
//        Map<PaymentType, List<Order>> grouped = orders.stream()
//                .collect(Collectors.groupingBy(Order::getPaymentType));

        Map<PaymentType, List<Order>> grouped = orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getPaymentType() != null ?
                                order.getPaymentType() : PaymentType.CASH
                ));


        List<PaymentSummary> summaries = new ArrayList<>();

        for (Map.Entry<PaymentType, List<Order>> entry : grouped.entrySet()) {
            double amount = entry.getValue()
                    .stream()
                    .mapToDouble(Order::getTotalAmount)
                    .sum();
            int transactions = entry.getValue().size();
            double percent = (amount / totalSales) * 100;

            PaymentSummary ps = new PaymentSummary();
            ps.setPaymentType(entry.getKey());
            ps.setTotalAmount(amount);
            ps.setTransactionCount(transactions);
            ps.setPercentage(percent);
            summaries.add(ps);
        }

        return summaries;
    }
}


