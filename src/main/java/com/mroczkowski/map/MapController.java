package com.mroczkowski.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class MapController {

    private PointRepo pointRepo;
    private AppUser appUser;


    @Autowired
    public MapController(PointRepo pointRepo) {
        this.pointRepo = pointRepo;

    }

    @GetMapping("/map")
    public String getMap(Principal principal, Model model) {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        model.addAttribute("userPoints", pointRepo.findAllPointsFromActiveUser(appUser.getId()));
        model.addAttribute("othersPoints", pointRepo.findAllPointsFromOthers(appUser.getId()));
        model.addAttribute("newPoint", new Point());
        model.addAttribute("pointId", new Point());
        model.addAttribute("user", principal.getName());
        return "map";
    }

    @PostMapping("/add-point")
    public String addPoint(@ModelAttribute Point point) {
        System.out.println(point);
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        point.setAppUser(appUser);
        //appUser.getPoints().add(point);
        pointRepo.save(point);
        return "redirect:/map";
    }

    @PostMapping("/delete-point")
    public String deletePoint(@ModelAttribute Point point) {
/*        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }*/
        Optional<Point> byId = pointRepo.findById(point.getId());
        Point point1 = byId.get();
        System.out.println("delete point with id: " + point.getId());
        if (point.getId() < 1) {
            System.out.println("Id is lower than 0: " + point.getId());
        } else {
            System.out.println("OK id: " + point.getId());
            if (appUser.getId().equals(point1.getAppUser().getId())) {
                System.out.println("Point belongs to the current user: " + point1.getAppUser().getId()
                        + " " + appUser.getId());
                pointRepo.deleteById(point1.getId());

            }
        }
        return "redirect:/map";
    }
}
