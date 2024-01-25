package com.tmir.service;

import com.tmir.entity.Booking;
import com.tmir.entity.Table;
import com.tmir.exceptions.NotFoundException;
import com.tmir.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TableService implements CommonServiceMethods<Table> {

    private final TableRepository tableRepository;


    @Autowired
    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @Override
    public List<Table> list() {
        return tableRepository.findAll();
    }

    @Override
    public Table getById(Integer id) {
        return tableRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Не удалось найти столик с id " + id));
    }

    @Override
    public Table save(Table table) {
        return tableRepository.save(table);
    }

    @Override
    public void delete(Integer id) {
        tableRepository.deleteById(id);
    }

    public List<Table> findFreeTable(LocalDateTime dateTimeOfVisit, Long forHowLong) {
        LocalDateTime endOfVisit = dateTimeOfVisit.plusHours(forHowLong);
        List<Table> freeTables = new ArrayList<>();

        List<Table> tableList = list();

        boolean isFree;

        for (Table table : tableList) {
            isFree = true;
                for (Booking booking : table.getBookingList()) {
                    if (booking.getVisitTime().isBefore(dateTimeOfVisit)
                            && booking.getVisitTime().plusHours(booking.getForHowLong()).isBefore(dateTimeOfVisit)
                            || booking.getVisitTime().isAfter(endOfVisit)
                            && booking.getVisitTime().plusHours(booking.getForHowLong()).isAfter(endOfVisit)) {
                    } else {
                        if (!booking.getCancelled()) {
                            isFree = false;
                            break;
                        }
                    }
                }
                if (isFree) {
                    freeTables.add(table);
                }
            }

        return freeTables;
    }

    public Table findByNumber(Integer number) {
        return tableRepository.findByNumber(number);
    }
}
