package com.lolRiver.river.controllers.testControllers;

import com.lolRiver.river.controllers.LolUserUpdaterController;
import com.lolRiver.river.controllers.fetchers.rawDataFetchers.KassadinRawDataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
/**
 * @author mxia (mxia@lolRiver.com)
 *         10/3/13
 */

@Controller
public class LolUserRawDataFetchController {
    @Autowired
    LolUserUpdaterController lolUserUpdaterController;

    @Autowired
    KassadinRawDataFetcher kassadinRawDataFetcher;

    //    @RequestMapping(value = {"/fetchRawData"}, method = RequestMethod.GET, produces = "text/plain")
    //    @ResponseBody
    //    public ResponseEntity<String> start(@RequestParam(value = "username") String username,
    //                                        @RequestParam(value = "region") String region,
    //                                        @RequestParam(value = "streamer_name") String streamerName) throws Exception {
    //        LolUser lolUser = new LolUser().setUserName(username).setRegion(region).setStreamerName(streamerName);
    //        lolUserUpdaterController.updateUser(lolUser);
    //
    //        List<LolUser> lolUsers = new ArrayList<LolUser>();
    //        lolUsers.add(lolUser);
    //        kassadinRawDataFetcher.fetchAndStoreRawData(lolUsers);
    //        return new ResponseEntity<String>("Fetched raw data for user: " + username + " region: " + region, HttpStatus
    //                                                                                                           .OK);
    //    }
}
