package enviro.assessment.grad001.MpumeleloNgozo.controller;

import enviro.assessment.grad001.MpumeleloNgozo.entity.Investor;
import enviro.assessment.grad001.MpumeleloNgozo.repository.InvestorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private InvestorRepository investorRepository;

    @GetMapping("/login")
    public String loginForm() {
        return "login"; // Display the login form
    }

    @PostMapping("/login")
    public String login(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String id, Model model) {
        // Find the investor in the database based on provided credentials
        Investor investor = investorRepository.findByFirstNameAndLastNameAndId(firstName, lastName, id);

        if (investor != null) {
            // Authentication successful, redirect to a user profile page or another destination
            model.addAttribute("investor", investor); // Pass the investor details to the profile page
            return "redirect:/user-profile";
        } else {
            // Authentication failed, return to the login page with an error message
            return "redirect:/login?error=true";
        }
    }
}

