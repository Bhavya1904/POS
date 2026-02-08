package com.pos.service;

import com.pos.exception.UserException;
import com.pos.payload.dto.ShiftReportDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ShiftReportService {

    /**
     * Start a new shift for the cashier at a specific branch.
     */
    ShiftReportDTO startShift() throws Exception;

    /**
     * End the shift and generate full summary report including:
     * - total sales, refunds, net sales
     * - payment breakdown
     * - top-selling products
     * - recent orders
     * - refunds processed
     */
    ShiftReportDTO endShift(Long shiftReportId, LocalDateTime shiftEnd) throws Exception;

    /**
     * Get a single shift report by ID.
     */
    ShiftReportDTO getShiftReportById(Long id) throws Exception;

    /**
     * Get all shift reports.
     */
    List<ShiftReportDTO> getAllShiftReports();

    /**
     * Get shift reports for a specific cashier.
     */
    List<ShiftReportDTO> getShiftReportsByCashierId(Long cashierId)throws Exception;

    /**
     * Get current shift progress without ending the shift.
     */
    ShiftReportDTO getCurrentShiftProgress(Long cashierId) throws Exception;

    /**
     * Get shift reports for a specific branch.
     */
    List<ShiftReportDTO> getShiftReportsByBranchId(Long branchId) throws Exception;
    /**
     * Get a cashier's shift report for a specific date.
     */
    ShiftReportDTO getShiftReportByCashierAndDate(Long cashierId, LocalDateTime date) throws Exception;
    /**
     * Delete a shift report by ID.
     */
    void deleteShiftReport(Long id);
}
