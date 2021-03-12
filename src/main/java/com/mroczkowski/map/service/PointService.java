package com.mroczkowski.map.service;

import com.mroczkowski.map.db.AppUserRepo;
import com.mroczkowski.map.db.PointRepo;
import com.mroczkowski.map.exceptions.PointNotFoundException;
import com.mroczkowski.map.model.AppUser;
import com.mroczkowski.map.model.Point;
import com.mroczkowski.map.model.Role;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PointService {

    private PointRepo pointRepo;
    private PasswordEncoder passwordEncoder;

    public PointService(PointRepo pointRepo, PasswordEncoder passwordEncoder) {
        this.pointRepo = pointRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public Point findById(Long id) throws PointNotFoundException {
        Optional<Point> byId = pointRepo.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        } else {
            throw new PointNotFoundException("Couldn't find point in database with id: " + id);
        }
    }

    public void deletePoint(Long id) throws PointNotFoundException {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Point point = findById(id);
            if (appUser.getRole() == Role.ADMIN) {
                pointRepo.deleteById(point.getId());
            } else {
                if (appUser.getId().equals(point.getAppUser().getId())) {
                    System.out.println("Point belongs to the current user: " + point.getAppUser().getId()
                            + " " + appUser.getId());
                    pointRepo.deleteById(point.getId());
                }
            }
            System.out.println("Point with id: " + id + " deleted");
        }

    }
}
