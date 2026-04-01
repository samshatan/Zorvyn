package Zorvyn_Test.Backend.service;

import Zorvyn_Test.Backend.dto.FinancialRecordRequest;
import Zorvyn_Test.Backend.model.FinancialRecord;
import Zorvyn_Test.Backend.model.RecordType;
import Zorvyn_Test.Backend.repository.FinancialRecordRepository;
import Zorvyn_Test.Backend.repository.FinancialRecordSpecification;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public List<FinancialRecord> getAllActiveRecords(RecordType type, String category, LocalDate startDate, LocalDate endDate) {
        Specification<FinancialRecord> spec = FinancialRecordSpecification.getFilteredRecords(type, category, startDate, endDate);
        return recordRepository.findAll(spec, Sort.by(Sort.Direction.DESC, "date"));
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

    public void deleteRecord(UUID id) {
        FinancialRecord existing = recordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Record not found with ID: " + id));
        existing.setDeleted(true);
        recordRepository.save(existing);
    }
}
