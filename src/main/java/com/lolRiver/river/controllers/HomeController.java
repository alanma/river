package com.lolRiver.river.controllers;

import com.lolRiver.river.frontend.Constants;
import com.lolRiver.river.models.Clip;
import com.lolRiver.river.persistence.interfaces.ClipDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * @author mxia (mxia@lolRiver.com)
 *         10/9/13
 */

@Controller
public class HomeController {
    @Autowired
    ClipDao clipDao;

    private List<String> skinFileNames = new ArrayList<String>(getSkinFileNames());

    private List<String> getSkinFileNames() {
        List<String> list = new ArrayList<String>();

        File folder = new File("src/main/webapp/static/images/skins");
        if (folder.exists()) {
            for (File fileEntry : folder.listFiles()) {
                String filePath = fileEntry.getAbsolutePath();
                list.add(filePath.substring(filePath.indexOf("/static")));
            }
        }
        return list;
    }

    private String getRandomSkinFile() {
        Random random = new Random();
        return skinFileNames.get(random.nextInt(skinFileNames.size()));
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String getClips(ModelMap modelMap,
                           @RequestParam(value = "p", defaultValue = "1") int offset,
                           @RequestParam(value = "orderBy", defaultValue = "start_time") String orderBy,
                           @RequestParam(value = "desc", defaultValue = "false") boolean descending) throws Exception {
        List<Clip> clips = clipDao.getClips((offset - 1) * Constants.MAX_CLIPS_PER_TABLE,
                                           Constants.MAX_CLIPS_PER_TABLE,
                                           orderBy, descending);
        modelMap.addAttribute("clips", clips);

        int numClipPages = 45;//1 + clipDao.getNumTotalClips() / Constants.MAX_CLIPS_PER_TABLE;
        modelMap.addAttribute("numClipPages", numClipPages);

        modelMap.addAttribute("randomSkinFile", getRandomSkinFile());
        return "index";
    }

    @RequestMapping(value = {"/searchClips"}, method = RequestMethod.POST)
    public String searchClips(ModelMap modelMap, @ModelAttribute Clip clip, HttpSession session,
                              @RequestParam(value = "prevStreamerName") String prevStreamerName,
                              @RequestParam(value = "prevChampionPlayedString") String prevChampionPlayedString,
                              @RequestParam(value = "prevChampionFacedString") String prevChampionFacedString,
                              @RequestParam(value = "prevMinLength") String prevMinLength,
                              @RequestParam(value = "prevMaxLength") String prevMaxLength) {
        List<Clip> clips = clipDao.getClipsFromClip(0, Constants.MAX_CLIPS_PER_TABLE, "start_time", false, clip);
        modelMap.addAttribute("clips", clips);

        session.setAttribute("prevStreamerName", prevStreamerName);
        session.setAttribute("prevChampionPlayedString", prevChampionPlayedString);
        session.setAttribute("prevChampionFacedString", prevChampionFacedString);
        session.setAttribute("prevMinLength", prevMinLength);
        session.setAttribute("prevMaxLength", prevMaxLength);

        // pass back in the posted checkboxes to leave them checked
        if (clip != null) {
            List<String> roleCriteria = new ArrayList<String>();
            List<String> eloCriteria = new ArrayList<String>();
            if (clip.getRoleCriteria() != null) {
                roleCriteria = clip.getRoleCriteria();
            }
            if (clip.getEloCriteria() != null) {
                eloCriteria = clip.getEloCriteria();
            }
            modelMap.addAttribute("roleCriteria", new ArrayList<String>(roleCriteria));
            modelMap.addAttribute("eloCriteria", new ArrayList<String>(eloCriteria));
        }

        modelMap.addAttribute("randomSkinFile", getRandomSkinFile());
        return "index";
    }

    public void dummyMethodForTesting() {
        return;
    }
}
