package com.pos.controller;

import com.pos.exception.UserException;
import com.pos.mapper.ShiftReportMapper;
import com.pos.model.ShiftReport;
import com.pos.payload.dto.ShiftReportDTO;
import com.pos.service.ShiftReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shift-reports")
public class ShiftReportController {

    private final ShiftReportService shiftReportService;
    private final ShiftReportMapper shiftReportMapper;

    /**
     * 🔄 Start a new shift (only once per day)
     */
    @PostMapping("/start")
    public ResponseEntity<ShiftReportDTO> startShift() throws Exception {
        // the current user will be auto-fetched from the session in service
        return ResponseEntity.ok(shiftReportService.startShift());
    }

    /**
     * 🛑 End the current shift for logged-in cashier
     */
    @PatchMapping("/end")
    public ResponseEntity<ShiftReportDTO> endShift() throws Exception {
        ShiftReportDTO ended = shiftReportService.endShift(
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(ended);
    }

    /**
     * 📊 Get current shift progress (live data) by cashierId
     */
    @GetMapping("/current")
    public ResponseEntity<ShiftReportDTO> getCurrentShiftProgress() throws Exception {
        ShiftReportDTO shift = shiftReportService.getCurrentShiftProgress(null);
        return ResponseEntity.ok(shift);
    }

    /**
     * 📅 Get shift report by date (for cashier)
     */
    @GetMapping("/cashier/{cashierId}/by-date")
    public ResponseEntity<ShiftReportDTO> getShiftReportByDate(
            @PathVariable Long cashierId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date
    ) throws Exception {
        ShiftReportDTO shift = shiftReportService.getShiftReportByCashierAndDate(
                cashierId, date);

        return ResponseEntity.ok(shift);
    }

    /**
     * 👤 Get all shift reports for a cashier
     */
    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<ShiftReportDTO>> getShiftsByCashier(
            @PathVariable Long cashierId
    ) throws Exception {
        List<ShiftReportDTO> shift = shiftReportService
                .getShiftReportsByCashierId(cashierId);
        return ResponseEntity.ok(shift);
    }

    /**
     * 🏬 Get all shift reports for a branch
     */
    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<ShiftReportDTO>> getShiftsByBranch(
            @PathVariable Long branchId
    ) throws Exception {
        List<ShiftReportDTO> shifts = shiftReportService.getShiftReportsByBranchId(branchId);
        return ResponseEntity.ok(shifts);
    }

    /**
     * 📋 Get all shift reports (admin use)
     */
    @GetMapping
    public ResponseEntity<List<ShiftReportDTO>> getAllShifts() {
        List<ShiftReportDTO> shifts=shiftReportService.getAllShiftReports();

        return ResponseEntity.ok(shifts);
    }

    /**
     * 🔍 Get a shift by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShiftReportDTO> getShiftById(@PathVariable Long id) throws Exception {
        ShiftReportDTO shifts=shiftReportService.getShiftReportById(id);

        return ResponseEntity.ok(shifts);
    }

    /**
     * ❌ Delete a shift report (admin use)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShift(@PathVariable Long id) {
        shiftReportService.deleteShiftReport(id);
        return ResponseEntity.ok().build();
    }
}
