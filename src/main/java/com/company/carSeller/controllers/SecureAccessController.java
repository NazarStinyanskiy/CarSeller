package com.company.carSeller.controllers;

import com.company.carSeller.entities.*;
import com.company.carSeller.modelClasses.CarAnnouncementInfo;
import com.company.carSeller.modelClasses.UserLoginInfo;
import com.company.carSeller.services.*;
import org.apache.catalina.connector.CoyoteInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
//@SessionAttributes("userLoginInfo") Why do not work with this annotation?!?!?!
public class SecureAccessController {

    private final UserInfoService userInfoService;
    private final CarInfoService carInfoService;
    private final ColorService colorService;
    private final EngineService engineService;
    private final MarkaService markaService;
    private final ModificationService modificationService;
    private final InvoiceService invoiceService;


    @Autowired
    public SecureAccessController(UserInfoService userInfoService, CarInfoService carInfoService,
                                  ColorService colorService, EngineService engineService,
                                  MarkaService markaService, ModificationService modificationService,
                                  InvoiceService invoiceService) {

        this.userInfoService = userInfoService;
        this.carInfoService = carInfoService;
        this.colorService = colorService;
        this.engineService = engineService;
        this.markaService = markaService;
        this.modificationService = modificationService;
        this.invoiceService = invoiceService;
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("info", new UserLoginInfo());
        return "authorization/loginPage";
    }

    @PostMapping("/login")
    public String login(HttpSession httpSession, @ModelAttribute("info") UserLoginInfo userLoginInfo){
        UserInfoEntity userInfoEntity = userInfoService.findByLogin(userLoginInfo.getLogin());
        if(userInfoEntity == null) return "redirect:/login";

        httpSession.setAttribute("userLoginInfo", userLoginInfo);

        if(!authorize(httpSession, userInfoEntity)) return "redirect:/login";

        return "redirect:/account/" + userInfoEntity.getId();
    }

    @PostMapping("/registration")
    public String registration(HttpSession httpSession, @ModelAttribute("user") UserInfoEntity userInfoEntity, Model model){
        if(userInfoService.findByLogin(userInfoEntity.getLogin()) != null){
            model.addAttribute("errorMessage", "User with login " + userInfoEntity.getLogin() + " already exist");
            return "authorization/registration";
        }
        if(userInfoEntity.getPassword().length() < 5){
            model.addAttribute("errorMessage", "Your login is too short");
            return "authorization/registration";
        }

        httpSession.setAttribute("userLoginInfo",
                new UserLoginInfo(userInfoEntity.getLogin(), userInfoEntity.getPassword()));

        userInfoService.save(userInfoEntity);

        return "redirect:/account/" + userInfoService.findByLogin(userInfoEntity.getLogin()).getId();
    }

    @GetMapping("/account/{id}")
    public String account(HttpSession httpSession, @PathVariable("id") String id, Model model){
        UserInfoEntity userInfoEntity = userInfoService.findById(Integer.parseInt(id));

        if(!authorize(httpSession, userInfoEntity)){
            return "redirect:/login";
        }

        model.addAttribute("user_info", userInfoEntity);
        model.addAttribute("cars", carInfoService.getAllOrderedDescForUserId(Integer.parseInt(id)));
        return "userPages/account";
    }

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("user", new UserInfoEntity());
        return "authorization/registration";
    }

    @GetMapping("/account/{id}/newAnnouncement")
    public String newAnnouncementForm(@PathVariable("id") String id, HttpSession httpSession, Model model){
        UserInfoEntity userInfoEntity = userInfoService.findById(Integer.parseInt(id));

        if(!authorize(httpSession, userInfoEntity)){
            return "redirect:/login";
        }

        List<ModificationEntity> modificationEntities = modificationService.getAll();

        String[] modifications = new String[modificationService.getAll().size()];
        for (int i = 0; i < modifications.length; i++) {
            modifications[i] = modificationEntities.get(i).getTitle();
        }

        model.addAttribute("car_info", new CarAnnouncementInfo());
        model.addAttribute("user_info", userInfoEntity);
        model.addAttribute("colorList", colorService.getAll());
        model.addAttribute("engineList", engineService.getAll());
        model.addAttribute("markaList", markaService.getAll());
        model.addAttribute("modifications", modifications);
        return "userPages/newAnnouncementForm";
    }

    @PostMapping("/account/{id}/newAnnouncement")
    public String newAnnouncement(HttpSession httpSession,
                                  @PathVariable("id") String id,
                                  @ModelAttribute("car_info") CarAnnouncementInfo carAnnouncementInfo,
                                  @RequestParam("image") MultipartFile image){

        UserInfoEntity userInfoEntity = userInfoService.findById(Integer.parseInt(id));

        if(!authorize(httpSession, userInfoEntity)){
            return "redirect:/login";
        }

        CarInfoEntity carInfoEntity = new CarInfoEntity();
        carInfoEntity.setTitle(carAnnouncementInfo.getTitle());
        carInfoEntity.setPrice(carAnnouncementInfo.getPrice());
        carInfoEntity.setDescription(carAnnouncementInfo.getDescription());
        carInfoEntity.setColorEntity(colorService.getById(carAnnouncementInfo.getColor_id()));
        carInfoEntity.setMarkaEntity(markaService.getById(carAnnouncementInfo.getMarka_id()));
        carInfoEntity.setEngineEntity(engineService.getById(carAnnouncementInfo.getEngine_id()));
        carInfoEntity.setUserInfoEntity(userInfoEntity);

        Set<ModificationEntity> set = new HashSet<>();
        set.add(modificationService.getByTitle(carAnnouncementInfo.getFirstModification()));
        set.add(modificationService.getByTitle(carAnnouncementInfo.getSecondModification()));
        set.add(modificationService.getByTitle(carAnnouncementInfo.getThirdModification()));
        set.add(modificationService.getByTitle(carAnnouncementInfo.getFourthModification()));

        carInfoEntity.setModificationEntity(set);

        int car_id = carInfoService.save(carInfoEntity).getId();

        try {
            image.transferTo(new File(System.getProperty("user.dir")
                    + "/src/main/webapp/images/" + car_id + ".jpeg"));
            System.out.println(System.getProperty("user.dir")
                    + "/src/main/webapp/images/" + car_id + ".jpeg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/auto/" + car_id;
    }

    @GetMapping("/auto/{id}/edit")
    public String editAnnouncementForm(HttpSession httpSession, @PathVariable("id") String id, Model model){
        CarInfoEntity carInfoEntity = carInfoService.getById(Integer.parseInt(id));
        UserInfoEntity userInfoEntity = carInfoEntity.getUserInfoEntity();

        if(!authorize(httpSession, userInfoEntity)){
            return "redirect:/login";
        }

        CarAnnouncementInfo carAnnouncementInfo = new CarAnnouncementInfo();
        carAnnouncementInfo.setTitle(carInfoEntity.getTitle());
        carAnnouncementInfo.setPrice(carInfoEntity.getPrice());
        carAnnouncementInfo.setDescription(carInfoEntity.getDescription());
        carAnnouncementInfo.setUser_info_id(carInfoEntity.getUserInfoEntity().getId());
        carAnnouncementInfo.setColor_id(carInfoEntity.getColorEntity().getId());
        carAnnouncementInfo.setEngine_id(carInfoEntity.getEngineEntity().getId());
        carAnnouncementInfo.setMarka_id(carInfoEntity.getMarkaEntity().getId());

        List<ModificationEntity> modificationEntities = modificationService.getAll();

        String[] modifications = new String[modificationService.getAll().size()];
        for (int i = 0; i < modifications.length; i++) {
            modifications[i] = modificationEntities.get(i).getTitle();
        }

        model.addAttribute("car_id", id);
        model.addAttribute("car_info", carAnnouncementInfo);
        model.addAttribute("user_info", userInfoEntity);
        model.addAttribute("colorList", colorService.getAll());
        model.addAttribute("engineList", engineService.getAll());
        model.addAttribute("markaList", markaService.getAll());
        model.addAttribute("modifications", modifications);

        List<ModificationEntity> list = modificationService.getAllByCarInfo(carInfoEntity);

        model.addAttribute("selectedModifications", list);


        return "userPages/editPage";
    }


    @PatchMapping("/auto/{id}/edit")
    public String editAnnouncement(HttpSession httpSession, @PathVariable("id") String id,
                                   @ModelAttribute("car_info") CarAnnouncementInfo carAnnouncementInfo){
        CarInfoEntity carInfoEntity = carInfoService.getById(Integer.parseInt(id));
        UserInfoEntity userInfoEntity = carInfoEntity.getUserInfoEntity();

        if(!authorize(httpSession, userInfoEntity)){
            return "redirect:/login";
        }

        CarInfoEntity car = new CarInfoEntity();
        car.setId(carInfoEntity.getId());
        car.setTitle(carAnnouncementInfo.getTitle());
        car.setPrice(carAnnouncementInfo.getPrice());
        car.setDescription(carAnnouncementInfo.getDescription());
        car.setColorEntity(colorService.getById(carAnnouncementInfo.getColor_id()));
        car.setMarkaEntity(markaService.getById(carAnnouncementInfo.getMarka_id()));
        car.setEngineEntity(engineService.getById(carAnnouncementInfo.getEngine_id()));
        car.setUserInfoEntity(userInfoEntity);

        Set<ModificationEntity> set = new HashSet<>();
        set.add(modificationService.getByTitle(carAnnouncementInfo.getFirstModification()));
        set.add(modificationService.getByTitle(carAnnouncementInfo.getSecondModification()));
        set.add(modificationService.getByTitle(carAnnouncementInfo.getThirdModification()));
        set.add(modificationService.getByTitle(carAnnouncementInfo.getFourthModification()));

        car.setModificationEntity(set);

        carInfoService.save(car);

        return "redirect:/auto/" + id;
    }

    @DeleteMapping("/auto/{id}/delete")
    public String deleteAnnouncement(HttpSession httpSession, @PathVariable("id") String id){
        CarInfoEntity carInfoEntity = carInfoService.getById(Integer.parseInt(id));
        UserInfoEntity userInfoEntity = carInfoEntity.getUserInfoEntity();

        if(!authorize(httpSession, userInfoEntity)){
            return "redirect:/login";
        }

        carInfoService.deleteById(Integer.parseInt(id));
        return "redirect:/account/" + userInfoEntity.getId();
    }

    @PostMapping("/auto/{id}/buy/success")
    public String successBuy(@ModelAttribute("invoice") InvoiceEntity invoice,
                             @PathVariable(name = "id") String id){

        CarInfoEntity carInfoEntity = carInfoService.getById(Integer.parseInt(id));

        invoice.setId(carInfoEntity.getId());
        invoice.setCarInfoEntity(carInfoEntity);
        invoice.setSellerName(carInfoEntity.getUserInfoEntity().getName());
        invoice.setSellerPhone(carInfoEntity.getUserInfoEntity().getPhone());

        if(!invoiceService.save(invoice)) {
            return "errors/invoiceErrorPage";
        }

        return "success/buySuccessPage";
    }

    private boolean authorize(HttpSession httpSession, UserInfoEntity userInfoEntity){
        UserLoginInfo userLoginInfo = (UserLoginInfo) httpSession.getAttribute("userLoginInfo");
        if(userLoginInfo == null) return false;
        return userInfoService.validate(userLoginInfo.getLogin(),
                userLoginInfo.getPassword(),
                userInfoEntity.getLogin(),
                userInfoEntity.getPassword());
    }
}