package com.mroczkowski.map.controller;

import com.mroczkowski.map.db.PointRepo;
import com.mroczkowski.map.exceptions.PointNotFoundException;
import com.mroczkowski.map.model.AppUser;
import com.mroczkowski.map.model.Point;
import com.mroczkowski.map.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class MapController {

    private final PointRepo pointRepo;
    private final PointService pointService;
    private AppUser appUser;

    @Autowired
    public MapController(PointRepo pointRepo, PointService pointService) {
        this.pointRepo = pointRepo;
        this.pointService = pointService;
    }

    @GetMapping("/map")
    public String getMap(Principal principal, Model model) {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        model.addAttribute("userPoints", pointRepo.findAllPointsFromActiveUser(appUser.getId()));
        model.addAttribute("othersPoints", pointRepo.findAllPointsFromOthers(appUser.getId()));
        model.addAttribute("allPoints", pointRepo.findAll());
        model.addAttribute("point", new Point());
        model.addAttribute("appUser", appUser);
        model.addAttribute("user", principal.getName());
        return "map";
    }

    @GetMapping("/showFormForAddPoint")
    public String formForAddPoint(Model model) {
        Point point = new Point();
        model.addAttribute("point", point);
        return "add-point-form";
    }

    @GetMapping("/showFormForUpdatePoint")
    public String formForUpdatePoint(@RequestParam("pointId") Long id, Model model) throws PointNotFoundException {
        Point point = pointService.findById(id);
            model.addAttribute("point", point);

        return "add-point-form";
    }

    @PostMapping("/add-point")
    public String addPoint(@Valid @ModelAttribute Point point,
                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            point.setAppUser(appUser);
            System.out.println("error" + bindingResult);
            return "/add-point-form";
        } else {
            System.out.println("no errors" + bindingResult);
            System.out.println(point);
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            }
            point.setAppUser(appUser);
            pointRepo.save(point);
            return "redirect:/map";
        }
    }

    @GetMapping("/delete-point")
    public String deletePoint(@RequestParam("pointId") Long id) throws PointNotFoundException {

        pointService.deletePoint(id);

        return "redirect:/map";
    }
}
