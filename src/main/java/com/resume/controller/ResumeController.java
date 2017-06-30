package com.resume.controller;

import com.resume.model.Education;
import com.resume.model.Experience;
import com.resume.model.Person;
import com.resume.model.User;
import com.resume.repository.EducationRepository;
import com.resume.repository.ExperienceRepository;
import com.resume.repository.PersonRepository;
import com.resume.repository.SkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by student on 6/29/17.
 */
@Controller
public class ResumeController {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private ExperienceRepository experienceRepository;
    @Autowired
    private SkillsRepository skillsRepository;


    @RequestMapping("/displayProfile")
    public String displayProfile(Model model){
        model.addAttribute("persons", personRepository.findAll());
        return "displayProfile";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(Model model) {
        model.addAttribute("person", new Person());
        return "signup";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String processRegistrationForm(@ModelAttribute Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        User user = new User();
        String email = person.getEmail();
        personRepository.save(person);
        return "redirect:/displayProfile";
    }

    @RequestMapping(value = "/education", method = RequestMethod.GET)
    public String getEducationForm(Model model) {

        model.addAttribute("education", new Education());
        return "education";
    }

    @RequestMapping(value = "/education", method = RequestMethod.POST)
    public String processEducationForm(@ModelAttribute Education education, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "education";
        }
        educationRepository.save(education);
        return "redirect:/displayProfile";
    }


    @RequestMapping(value = "/experience", method = RequestMethod.GET)
    public String getExperienceForm(Model model) {

        model.addAttribute("experience", new Experience());
        return "experience";
    }

    @RequestMapping(value = "/experience", method = RequestMethod.POST)
    public String processExperienceForm(@ModelAttribute Experience experience, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "experience";
        }
        experienceRepository.save(experience);
        return "experience";
    }


    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String SearchByName(Model model){
        model.addAttribute(new Person());
        return "search";
    }
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String processSearch(@ModelAttribute Person person, Model model){
        String firstName = person.getFirstName();
        Iterable<Person> personIterable = personRepository.findByFirstName(firstName);
        model.addAttribute("persons", personIterable);
        return "searchResult";
    }
    @RequestMapping(value = "/searchByCompany", method = RequestMethod.GET)
    public String processSearch(@ModelAttribute Experience experience, Model model){
        String companyName = experience.getCompanyName();
        Iterable<Experience> experienceIterable = experienceRepository.findByCompanyName(companyName);
        model.addAttribute("experience", experienceIterable);
        return "displayProfile";
    }
}
