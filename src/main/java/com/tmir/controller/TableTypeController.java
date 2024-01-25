package com.tmir.controller;

import com.tmir.entity.TableType;
import com.tmir.service.TableTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TableTypeController {

    private final TableTypeService tableTypeService;

    public TableTypeController(TableTypeService tableTypeService) {
        this.tableTypeService = tableTypeService;
    }

    @RequestMapping("/types")
    public String listTables(Model model){
        model.addAttribute("types", tableTypeService.list().stream().sorted().toList());
        return "types/list";
    }

    @RequestMapping("/types/new")
    public String createNewTableType(Model model){
        model.addAttribute("type", new TableType());
        return "types/form";
    }

    @RequestMapping(value = "/types/save", method = RequestMethod.POST)
    public String saveTypeTable(@ModelAttribute("type") TableType tableType){
        tableTypeService.save(tableType);
        return "redirect:/types";
    }

    @RequestMapping("/types/delete/{id}")
    public String deleteTableType(@PathVariable(name = "id") Integer id){
        tableTypeService.delete(id);
        return "redirect:/types";
    }

    @RequestMapping("/types/edit/{id}")
    public String updateTableType(@PathVariable(name = "id") Integer id, Model model){
        model.addAttribute("type", tableTypeService.getById(id));
        return "types/form";
    }
}
