package org.greengin.nquireit.controllers.activities.senseit;

import javax.servlet.http.HttpServletRequest;

import com.mangofactory.jsonview.ResponseView;
import org.greengin.nquireit.json.Views;
import org.greengin.nquireit.logic.project.senseit.JoinedProfilesActions;
import org.greengin.nquireit.logic.project.senseit.JoinedProfilesResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/senseit/profiles")
public class SenseItProfilesController extends AbstractSenseItController {

    @RequestMapping(method = RequestMethod.GET)
	@ResponseView(Views.VotableCount.class)
    @ResponseBody
	public JoinedProfilesResponse get(HttpServletRequest request) {
		JoinedProfilesActions member = new JoinedProfilesActions(context, request);
		return member.joinedProfiles();
	}
}
