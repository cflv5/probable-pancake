package tr.edu.yildiz.yazilimkalite.librarymanagement.controller;

import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import tr.edu.yildiz.yazilimkalite.librarymanagement.util.ViewConstants;

@Controller
public class DashboardController {
    @GetMapping({ "/", "/dasboard" })
    public ModelAndView showDashboard(Model model) {
        model.addAttribute("js", Arrays.asList("https://www.chartjs.org/dist/2.9.4/Chart.min.js", "/js/dashboard.js"));
        model.addAttribute(ViewConstants.FRAGMENT, "dashboard/index");
        return new ModelAndView(ViewConstants.BOILERPLATE);
    }
}
