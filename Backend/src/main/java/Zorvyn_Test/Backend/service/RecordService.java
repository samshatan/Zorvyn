package Zorvyn_Test.Backend.service;

import Zorvyn_Test.Backend.dto.FinancialRecordRequest;
import Zorvyn_Test.Backend.model.FinancialRecord;
import Zorvyn_Test.Backend.repository.FinancialRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RecordService {

    private final FinancialRecordRepository recordRepository;

    public RecordService(FinancialRecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public FinancialRecord createRecord(FinancialRecordRequest request) {
        FinancialRecord record = FinancialRecord.builder()
                .amount(request.getAmount())
                .type(request.getType())
                .category(request.getCategory())
                .date(request.getDate())
                .notes(request.getNotes())
                .deleted(false)
                .build();
        return recordRepository.save(record);
    }

    public List<FinancialRecord> getAllActiveRecords() {
        return recordRepository.findByDeletedFalseOrderByDateDesc();
    }

    public FinancialRecord updateRecord(UUID id, FinancialRecordRequest request) {
        FinancialRecord existing = recordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Record not found with ID: " + id));

        if (existing.isDeleted()) {
            throw new IllegalArgumentException("Cannot update a deleted record.");
        }

        existing.setAmount(request.getAmount());
        existing.setType(request.getType());
        existing.setCategory(request.getCategory());
        existing.setDate(request.getDate());
        existing.setNotes(request.getNotes());

        return recordRepository.save(existing);
    }

    // Implementing Soft Delete as a thoughtful addition
    public void deleteRecord(UUID id) {
        FinancialRecord existing = recordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Record not found with ID: " + id));
        existing.setDeleted(true);
        recordRepository.save(existing);
    }
}
