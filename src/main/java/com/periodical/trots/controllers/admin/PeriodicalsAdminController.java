package com.periodical.trots.controllers.admin;

import com.periodical.trots.entities.*;
import com.periodical.trots.services.PeriodicalHasSubjectService;
import com.periodical.trots.services.PeriodicalService;
import com.periodical.trots.services.PublisherService;
import com.periodical.trots.services.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Controller
public class PeriodicalsAdminController {

    private static final Logger logger = LoggerFactory.getLogger(PeriodicalsAdminController.class);

    public static final String IMAGE_PATH = System.getenv("IMAGE_PATH");

    private final PeriodicalService periodicalService;

    private final PublisherService publisherService;

    private final SubjectService subjectService;

    private final PeriodicalHasSubjectService periodicalHasSubjectService;

    public PeriodicalsAdminController(PeriodicalService periodicalService, PublisherService publisherService, SubjectService subjectService, PeriodicalHasSubjectService periodicalHasSubjectService) {
        this.periodicalService = periodicalService;
        this.publisherService = publisherService;
        this.subjectService = subjectService;
        this.periodicalHasSubjectService = periodicalHasSubjectService;
    }

    @GetMapping("/periodicals")
    public String periodicalsPageForAdmin(Model model) {
        model.addAttribute("periodicals", periodicalService.getAllPeriodicals());
        model.addAttribute("publisherList", publisherService.findAll());
        model.addAttribute("subjectList", subjectService.findAll());
        model.addAttribute("periodicalForm", new PeriodicalEntity());
        return "PeriodicalPageForAdmin";
    }

    @GetMapping("/update-periodical")
    public String updatePeriodicalForAdmin(Model model, @RequestParam("id") Integer id){
        model.addAttribute("publisherList", publisherService.findAll());
        model.addAttribute("subjectList", subjectService.findAll());
        model.addAttribute("periodicalForm", periodicalService.getPeriodicalById(id));
        return "UpdatePeriodicalPageForAdmin";
    }

    @PostMapping("/update-periodical")
    public String updatePeriodical(RedirectAttributes redirectAttributes, @Valid @ModelAttribute("periodicalForm") PeriodicalEntity periodicalForm,
                                   Errors errors,
                                   @RequestParam("publisherName") String publisherName,
                                   @RequestParam("publisherTelephone") String publisherTelephone,
                                   @RequestParam("subject") List<String> subjectListFromWeb,
                                   @RequestParam("file") MultipartFile file,
                                   @RequestParam("periodicalId") Integer periodicalId) {
        if (errors.hasErrors()){
            langEx(redirectAttributes, "Please, specify correct values", "Будь ласка, вказуйте коректні дані");
            return "redirect:/update-periodical?id="+periodicalId;
        }
        if (periodicalService.getPeriodicalByTitle(periodicalForm.getTitle()) !=null && !periodicalForm.getTitle().equals(periodicalService.getPeriodicalById(periodicalId).getTitle())){
            langEx(redirectAttributes, "Periodical with such title already exist", "Видання з такою назвою уже існує");
            return "redirect:/update-periodical?id="+periodicalId;
        }

        PublisherEntity publisherEntity;
        PeriodicalHasSubjectEntity periodicalHasSubject = new PeriodicalHasSubjectEntity();
        PeriodicalHasSubjectEntityId periodicalHasSubjectEntityId = new PeriodicalHasSubjectEntityId();

        List<PublisherEntity> publisherEntityList = publisherService.findAll();
        publisherEntity = publisherEntityList.stream().filter(publisherEntity1 ->
                publisherName.equals(publisherEntity1.getName())).findAny().orElse(null);

        if (publisherEntity == null) {
            publisherEntity = new PublisherEntity();
            publisherEntity.setName(publisherName);
            publisherEntity.setTelephoneNumber(publisherTelephone);
            publisherService.save(publisherEntity);
        }
        periodicalForm.setPublisher(publisherEntity);

        try {
            String filename = file.getOriginalFilename();
            periodicalForm.setImages(filename);
            File path = new File(IMAGE_PATH + filename);
            path.createNewFile();
            FileOutputStream output = new FileOutputStream(path);
            output.write(file.getBytes());
            output.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        periodicalService.updatePeriodical(periodicalId, periodicalForm);

        SubjectEntity subjectEntity;
        for (String s : subjectListFromWeb) {
            List<SubjectEntity> subjectEntities = subjectService.findAll();
            if (!s.equals("")){
                subjectEntity = subjectEntities.stream().filter(subjectEntity1 ->
                        s.equals(subjectEntity1.getThemesOfBooks())).findAny().orElse(null);
                if (subjectEntity == null){
                    subjectEntity = new SubjectEntity();
                    subjectEntity.setThemesOfBooks(s);
                    subjectService.save(subjectEntity);
                    periodicalHasSubjectEntityId.setPeriodicalId(periodicalId);
                    periodicalHasSubjectEntityId.setSubjectId(subjectEntity.getId());
                    periodicalHasSubject.setId(periodicalHasSubjectEntityId);
                    periodicalHasSubject.setPeriodical(periodicalService.getPeriodicalById(periodicalId));
                    periodicalHasSubject.setSubject(subjectEntity);
                    periodicalHasSubjectService.save(periodicalHasSubject);
                }
            }
        }

        langEx(redirectAttributes, "Periodical was updated", "Видання було оновлено");
        logger.info("Periodical updated --> " + periodicalId);

        return "redirect:/periodicals";
    }



    @PostMapping("/delete-periodical")
    public String deletePeriodicalById(RedirectAttributes redirectAttributes, @RequestParam("periodicalId") Integer id) {
        periodicalService.deletePeriodical(id);
        langEx(redirectAttributes, "Periodical was deleted", "Видання було видалено");
        logger.info("Periodical deleted --> " + id);
        return "redirect:/periodicals";
    }

    @PostMapping("/add-periodical")
    public String addPeriodical(RedirectAttributes redirectAttributes, @Valid @ModelAttribute("periodicalForm") PeriodicalEntity periodicalForm,
                                Errors errors,
                                @RequestParam("publisherName") String publisherName,
                                @RequestParam("publisherTelephone") String publisherTelephone,
                                @RequestParam("subject") List<String> subjectListFromWeb,
                                @RequestParam("file") MultipartFile file) {
        if (errors.hasErrors()){
            langEx(redirectAttributes, "Please, specify correct values", "Будь ласка, вказуйте коректні дані");
            return "redirect:/periodicals";
        }
        if (periodicalService.getPeriodicalByTitle(periodicalForm.getTitle()) !=null){
            langEx(redirectAttributes, "Periodical with such title already exist", "Видання з такою назвою уже існує");
            return "redirect:/periodicals";
        }
        PublisherEntity publisherEntity;
        PeriodicalHasSubjectEntity periodicalHasSubject = new PeriodicalHasSubjectEntity();
        PeriodicalHasSubjectEntityId periodicalHasSubjectEntityId = new PeriodicalHasSubjectEntityId();

        List<PublisherEntity> publisherEntityList = publisherService.findAll();
        publisherEntity = publisherEntityList.stream().filter(publisherEntity1 ->
                publisherName.equals(publisherEntity1.getName())).findAny().orElse(null);

        if (publisherEntity == null) {
            publisherEntity = new PublisherEntity();
            publisherEntity.setName(publisherName);
            publisherEntity.setTelephoneNumber(publisherTelephone);
            publisherService.save(publisherEntity);
        }
        periodicalForm.setPublisher(publisherEntity);

        try {
            String filename = file.getOriginalFilename();
            periodicalForm.setImages(filename);
            File path = new File(IMAGE_PATH + filename);
            path.createNewFile();
            FileOutputStream output = new FileOutputStream(path);
            output.write(file.getBytes());
            output.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        int periodicalId = periodicalService.addPeriodical(periodicalForm);
        SubjectEntity subjectEntity;
        for (String s : subjectListFromWeb) {
            List<SubjectEntity> subjectEntities = subjectService.findAll();
            if (!s.equals("")){
            subjectEntity = subjectEntities.stream().filter(subjectEntity1 ->
                    s.equals(subjectEntity1.getThemesOfBooks())).findAny().orElse(null);
            if (subjectEntity == null){
                subjectEntity = new SubjectEntity();
                subjectEntity.setThemesOfBooks(s);
                subjectService.save(subjectEntity);
            }
            periodicalHasSubjectEntityId.setPeriodicalId(periodicalId);
            periodicalHasSubjectEntityId.setSubjectId(subjectEntity.getId());
            periodicalHasSubject.setId(periodicalHasSubjectEntityId);
            periodicalHasSubject.setPeriodical(periodicalService.getPeriodicalById(periodicalId));
            periodicalHasSubject.setSubject(subjectEntity);
            periodicalHasSubjectService.save(periodicalHasSubject);
            }
        }

        langEx(redirectAttributes, "Periodical was added", "Видання було додано");
        logger.info("Periodical added --> " + periodicalForm.getTitle());

        return "redirect:/periodicals";
    }

    private void langEx(RedirectAttributes redirectAttributes, String s, String s2) {
        String lang = String.valueOf(LocaleContextHolder.getLocale());
        if (lang.equals("en_US") || lang.equals("en")) {
            redirectAttributes.addFlashAttribute("ex", s);
        } else {
            redirectAttributes.addFlashAttribute("ex", s2);
        }
    }

}
