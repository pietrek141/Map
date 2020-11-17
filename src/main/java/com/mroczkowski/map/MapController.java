package com.mroczkowski.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
public class MapController {

    private PointRepo pointRepo;
    private AppUser appUser;
    private String deleteMessage;
    private String addMessage;


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
        //model.addAttribute("oldPoint", new Point());
        model.addAttribute("point", new Point());

        model.addAttribute("user", principal.getName());
        model.addAttribute("deleteMessage", deleteMessage);
        model.addAttribute("addMessage", addMessage);
        return "map";
    }

    @PostMapping("/add-point")
    public String addPoint(@Valid @ModelAttribute Point point,
                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            System.out.println("error" + bindingResult);
            point.setAppUser(appUser);
            return "map";

        } else {
            System.out.println("no errors" + bindingResult);
            System.out.println(point);
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            }
            point.setAppUser(appUser);
            //appUser.getPoints().add(point);
            pointRepo.save(point);
            addMessage = "Point successfully added";
        }
 //       if (!point.getName().isEmpty()) {


/*        } else {
            addMessage = "Please type the name of point";
        }*/
        deleteMessage = "";
        return "redirect:/map";
    }

    @GetMapping("/delete-point")
    public String deletePoint(@RequestParam("pointId") Long id) {

/*        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }*/
        //if (point.getId() != null){

            Optional<Point> byId = pointRepo.findById(id);
            if (byId.isPresent()) {
                Point point1 = byId.get();
/*                if (point.getId() < 1) {
                    System.out.println("Id is lower than 0: " + point.getId());
                } else {*/
                System.out.println("OK id: " + id);
                if (appUser.getId().equals(point1.getAppUser().getId())) {
                    System.out.println("Point belongs to the current user: " + point1.getAppUser().getId()
                            + " " + appUser.getId());
                    pointRepo.deleteById(point1.getId());
                    deleteMessage = "Point " + point1.getName() + " was successfully deleted";


                } else {
                    deleteMessage = "This point was created by another user";
                }

/*            } else {
                deleteMessage = "There is no point with id: " + point.getId();

            }*/
/*        } else {
            deleteMessage = "Please type the point id";

        }*/
        }
        System.out.println("delete point with id: " + id);
        addMessage = "";
        return "redirect:/map";
    }
}
