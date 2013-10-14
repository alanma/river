package com.lolRiver.river.controllers;

import com.lolRiver.river.frontend.Constants;
import com.lolRiver.river.models.Champion;
import com.lolRiver.river.models.Clip;
import com.lolRiver.river.models.Streamer;
import com.lolRiver.river.persistence.DaoCollection;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
    DaoCollection daoCollection;

    private List<String> skinFileNames;
    private List<String> streamerList;
    private List<String> championList;

    private List<String> getSkinFileNames() {
        if (skinFileNames == null) {
            List<String> list = new ArrayList<String>();

            File folder = new File("src/main/webapp/static/images/skins");
            if (folder.exists()) {
                for (File fileEntry : folder.listFiles()) {
                    String filePath = fileEntry.getAbsolutePath();
                    list.add(filePath.substring(filePath.indexOf("/static")));
                }
            }
            skinFileNames = new ArrayList<String>(list);
        }
        return skinFileNames;
    }

    private List<String> getStreamerList() {
        if (streamerList == null) {
            List<String> list = new ArrayList<String>();
            List<Streamer> streamers = daoCollection.getStreamerDao().getStreamers();
            for (Streamer streamer : streamers) {
                list.add(streamer.getName());
            }
            streamerList = new ArrayList<String>(list);
        }
        return streamerList;
    }

    private List<String> getChampionList() {
        if (championList == null) {
            List<String> list = new ArrayList<String>();
            for (Champion.Name name : Champion.Name.values()) {
                list.add(WordUtils.capitalizeFully(name.name()));
            }
            championList = new ArrayList<String>(list);
        }
        return championList;
    }

    private String getRandomSkinFile() {
        Random random = new Random();
        return getSkinFileNames().get(random.nextInt(getSkinFileNames().size()));
    }

    @RequestMapping(value = {"/autocompleteStreamerList"}, method = RequestMethod.GET)
    @ResponseBody
    public List<String> autocompleteStreamerList(@RequestParam("term") String query) {
        List<String> list = new ArrayList<String>();
        for (String streamer : getStreamerList()) {
            if (streamer.toLowerCase().contains(query.toLowerCase())) {
                list.add(streamer);
            }
        }
        return list;
    }

    @RequestMapping(value = {"/autocompleteChampionList"}, method = RequestMethod.GET)
    @ResponseBody
    public List<String> autocompleteChampionList(@RequestParam("term") String query) {
        List<String> list = new ArrayList<String>();
        for (String champion : getChampionList()) {
            if (champion.toLowerCase().contains(query.toLowerCase())) {
                list.add(champion);
            }
        }
        return list;
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String getClips(ModelMap modelMap,
                           @RequestParam(value = "p", defaultValue = "1") int offset,
                           @RequestParam(value = "orderBy", defaultValue = "start_time") String orderBy,
                           @RequestParam(value = "desc", defaultValue = "false") boolean descending) throws Exception {
        List<Clip> clips = daoCollection.getClipDao().getClips((offset - 1) * Constants.MAX_CLIPS_PER_TABLE,
                                                              Constants.MAX_CLIPS_PER_TABLE,
                                                              orderBy, descending);
        modelMap.addAttribute("clips", clips);

        int numClipPages = 45;//1 + daoCollection.getClipDao().getNumTotalClips() / Constants.MAX_CLIPS_PER_TABLE;
        modelMap.addAttribute("numClipPages", numClipPages);

        modelMap.addAttribute("randomSkinFile", getRandomSkinFile());
        return "index";
    }

    @RequestMapping(value = {"/searchClips"}, method = RequestMethod.POST)
    public String searchClips(ModelMap modelMap, @ModelAttribute Clip clip) {
        List<Clip> clips = daoCollection.getClipDao().getClipsFromClip(0, Constants.MAX_CLIPS_PER_TABLE, "start_time", false, clip);
        modelMap.addAttribute("clips", clips);

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
