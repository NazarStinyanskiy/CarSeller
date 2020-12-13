package com.company.carSeller.controllers;
import com.company.carSeller.modelClasses.Filter;
import com.company.carSeller.entities.CarInfoEntity;
import com.company.carSeller.entities.InvoiceEntity;
import com.company.carSeller.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
public class MainController {
    private final CarInfoService carInfoService;
    private final InvoiceService invoiceService;
    private final ColorService colorService;
    private final EngineService engineService;
    private final MarkaService markaService;

    @Autowired
    public MainController(CarInfoService carInfoService, InvoiceService invoiceService, ColorService colorService, EngineService engineService, MarkaService markaService) {
        this.carInfoService = carInfoService;
        this.invoiceService = invoiceService;
        this.colorService = colorService;
        this.engineService = engineService;
        this.markaService = markaService;
    }


    @GetMapping
    public String index(Model model){
        model.addAttribute("mostExpensiveCars", carInfoService.getMostExpensiveLimit5());
        model.addAttribute("filter", new Filter());
        model.addAttribute("colorList", colorService.getAll());
        model.addAttribute("engineList", engineService.getAll());
        model.addAttribute("markaList", markaService.getAll());
        return "index";
    }

    @GetMapping("/auto")
    public String showAll(@ModelAttribute("filter") Filter filter, Model model){

        List<CarInfoEntity> carInfoServiceList = carInfoService.getAllDesc();

        if(filter.getColor_id() != 0){
            carInfoServiceList = carInfoService.getAllByColor_id(carInfoServiceList, filter.getColor_id());
        }

        if(filter.getEngine_id() != 0){
            carInfoServiceList = carInfoService.getAllByEngine_id(carInfoServiceList, filter.getEngine_id());
        }

        if (filter.getMarka_id() != 0) {
            carInfoServiceList = carInfoService.getAllByMarka_id(carInfoServiceList, filter.getMarka_id());
        }

        if(filter.getLowerPrice() != 0 || filter.getHigherPrice() != 0){
            carInfoServiceList = carInfoService.getAllBetweenPrice(carInfoServiceList, filter.getLowerPrice(), filter.getHigherPrice());
        }
        model.addAttribute("search", carInfoServiceList);

        return "list";
    }

    @GetMapping("/auto/{id}")
    public String carByID(@PathVariable(name = "id") String id, Model model){
        model.addAttribute("car", carInfoService.getById(Integer.parseInt(id)));
        return "announcement";
    }

    @GetMapping("/auto/{id}/buy")
    public String buyForm(@PathVariable(name = "id") String id, Model model){
        model.addAttribute("car", carInfoService.getById(Integer.parseInt(id)));
        model.addAttribute("invoice", new InvoiceEntity());

        return "invoiceForm";
    }
}