package enviro.assessment.grad001.MpumeleloNgozo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
    public class UserProfileController {

        @GetMapping("/user-profile")
        public String userProfilePage() {
            return "user-profile";
        }
    }



