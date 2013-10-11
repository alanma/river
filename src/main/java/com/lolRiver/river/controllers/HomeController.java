package com.lolRiver.river.controllers;

import com.lolRiver.river.frontend.Constants;
import com.lolRiver.river.models.Clip;
import com.lolRiver.river.persistence.interfaces.ClipDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
/**
 * @author mxia (mxia@lolRiver.com)
 *         10/9/13
 */

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    ClipDao clipDao;

    @RequestMapping(method = RequestMethod.GET)
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

        return "index";
    }

    @RequestMapping(value = {"/searchClips"}, method = RequestMethod.POST)
    public String searchClips(@RequestBody Clip clip) {
        System.out.println(clip);
        return "index";
    }

}
