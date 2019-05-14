package com.apap.tu09.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tu09.model.FlightModel;
import com.apap.tu09.model.PilotModel;
import com.apap.tu09.service.FlightService;
import com.apap.tu09.service.PilotService;

/**
 * FlightController
 */
@Controller
public class FlightController {
    @Autowired
    private FlightService flightService;
    
    @Autowired
    private PilotService pilotService;

    @RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
    private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
        PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber).get();
        pilot.setListFlight(new ArrayList<FlightModel>(){
            private ArrayList<FlightModel> init(){
                this.add(new FlightModel());
                return this;
            }
        }.init());

        model.addAttribute("pilot", pilot);
        return "add-flight";
    }

    @RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.POST, params={"addRow"})
    private String addRow(@ModelAttribute PilotModel pilot, Model model) {
        pilot.getListFlight().add(new FlightModel());
        model.addAttribute("pilot", pilot);
        return "add-flight";
    }

    @RequestMapping(value="/flight/add/{licenseNumber}", method = RequestMethod.POST, params={"removeRow"})
    public String removeRow(@ModelAttribute PilotModel pilot, Model model, HttpServletRequest req) {
        Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        pilot.getListFlight().remove(rowId.intValue());
        
        model.addAttribute("pilot", pilot);
        return "add-flight";
    }

    @RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.POST, params={"save"})
    private String addFlightSubmit(@ModelAttribute PilotModel pilot) {
        PilotModel archive = pilotService.getPilotDetailByLicenseNumber(pilot.getLicenseNumber()).get();
        for (FlightModel flight : pilot.getListFlight()) {
            if (flight != null) {
                flight.setPilot(archive);
                flightService.addFlight(flight);
            }
        }
        return "add";
    }


    @RequestMapping(value = "/flight/view", method = RequestMethod.GET)
    private @ResponseBody FlightModel view(@RequestParam(value = "flightNumber") String flightNumber, Model model) {
        FlightModel archive = flightService.getFlightDetailByFlightNumber(flightNumber).get();
        return archive;
    }

    @RequestMapping(value = "/flight/delete", method = RequestMethod.POST)
    private String delete(@ModelAttribute PilotModel pilot, Model model) {
        for (FlightModel flight : pilot.getListFlight()) {
            flightService.deleteByFlightNumber(flight.getFlightNumber());
        }
        return "delete";
    }

    @RequestMapping(value = "/flight/update", method = RequestMethod.GET)
    private String update(@RequestParam(value = "flightNumber") String flightNumber, Model model) {
        FlightModel archive = flightService.getFlightDetailByFlightNumber(flightNumber).get();
        model.addAttribute("flight", archive);
        return "update-flight";
    }

    @RequestMapping(value = "/flight/update", method = RequestMethod.POST)
    private @ResponseBody FlightModel updateFlightSubmit(@ModelAttribute FlightModel flight, Model model) {
        flightService.addFlight(flight);
        return flight;
    }
}