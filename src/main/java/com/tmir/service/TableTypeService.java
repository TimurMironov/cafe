package com.tmir.service;

import com.tmir.entity.TableType;
import com.tmir.exceptions.NotFoundException;
import com.tmir.repository.TableTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableTypeService implements CommonServiceMethods<TableType> {

    private final TableTypeRepository tableTypeRepository;

    @Autowired
    public TableTypeService(TableTypeRepository tableTypeRepository) {
        this.tableTypeRepository = tableTypeRepository;
    }

    @Override
    public List<TableType> list() {
        return tableTypeRepository.findAll();
    }

    @Override
    public TableType getById(Integer id) {
        return tableTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Не удалось найти вид столика по id" + id));
    }

    @Override
    public TableType save(TableType tableType) {
        return tableTypeRepository.save(tableType);
    }

    @Override
    public void delete(Integer id) {
        tableTypeRepository.deleteById(id);
    }
}
