package com.verizon.tsp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.verizon.tsp.models.Month;
import com.verizon.tsp.models.PlanDetails;
import com.verizon.tsp.models.Report;
import com.verizon.tsp.models.TelecomCircle;
import com.verizon.tsp.models.User;
import com.verizon.tsp.services.PlanDetailsService;
import com.verizon.tsp.services.TCircleService;
import com.verizon.tsp.services.UserService;

@RestController
@RequestMapping("/plan")
@CrossOrigin(origins = { "http://localhost:4200" })
public class PlanDetailsController {

	@Autowired
	PlanDetailsService pdservice;

	@Autowired
	TCircleService tcservice;

	@Autowired
	UserService uservice;

	double d;
	List<List<Double>> revList = new ArrayList<List<Double>>();
	List<List<Double>> revList1 = new ArrayList<List<Double>>();

	static List<String> planName = new ArrayList<String>();

	@GetMapping
	public ResponseEntity<List<PlanDetails>> getAllPlan() {
		return new ResponseEntity<>(pdservice.getAllPlan(), HttpStatus.OK);
	}

	@GetMapping("/{planId}")
	public ResponseEntity<PlanDetails> findByPlanId(@PathVariable("planId") long planId) {

		ResponseEntity<PlanDetails> resp;

		PlanDetails pd = pdservice.findByPlanId(planId);

		if (pd == null)
			resp = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			resp = new ResponseEntity<>(pd, HttpStatus.OK);

		return resp;
	}

	@PostMapping("/createplan/{tcid}")
	public ResponseEntity<PlanDetails> createPlan(@RequestBody PlanDetails pd, @PathVariable("tcid") int tcId) {
		ResponseEntity<PlanDetails> resp = null;

		List<TelecomCircle> tc=new ArrayList<TelecomCircle>();
		tc.add(tcservice.findByTelecomCircleId(tcId));
		System.out.println(tc.get(0));
		pd.setTc(tc);
		PlanDetails pd_return = pdservice.createPlan(pd);

		if (pd_return != null) {
			resp = new ResponseEntity<>(pd_return, HttpStatus.OK);
		} else
			resp = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		return resp;
	}

	@PutMapping
	public ResponseEntity<PlanDetails> updatePlan(@RequestBody PlanDetails pd) {
		ResponseEntity<PlanDetails> resp = null;

		PlanDetails pd_return = pdservice.updatePlan(pd);

		if (pd_return != null) {
			resp = new ResponseEntity<>(pd_return, HttpStatus.OK);
		} else
			resp = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		return resp;
	}

	@DeleteMapping("{planId}")
	public ResponseEntity<Void> deletePlan(@PathVariable("planId") int planId) {
		ResponseEntity<Void> resp = null;

		if (pdservice.deletePlan(planId))
			resp = new ResponseEntity<>(HttpStatus.OK);
		else
			resp = new ResponseEntity<>(HttpStatus.NOT_FOUND);

		return resp;
	}

	@GetMapping("/telecom/{tcId}")
	public ResponseEntity<List<PlanDetails>> findTicketByUser(@PathVariable("tcId") long tcId) {

		TelecomCircle tc = tcservice.findByTelecomCircleId(tcId);

		ResponseEntity<List<PlanDetails>> resp;

		List<PlanDetails> pd_return = pdservice.findByTc(tc);

		if (pd_return == null)
			resp = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			resp = new ResponseEntity<>(pd_return, HttpStatus.OK);

		return resp;
	}

	@GetMapping("/revenue")
	public ResponseEntity<List<List<Double>>> getRevenue() {

		List<PlanDetails> plans = pdservice.getAllPlan();
		ResponseEntity<List<List<Double>>> resp;

		this.revList.clear();
		this.planName.clear();
		List<PlanDetails> pdname = pdservice.getAllPlan();

		for (PlanDetails pdn : pdname) {
			planName.add(pdn.getPlanName());
		}

		for (PlanDetails pl : plans) {
			List<Double> revenueList = new ArrayList<Double>();
			for (Month month : Month.values()) {
				long planId = pl.getPlanId();
				int userCount = 0;

				PlanDetails pd = pdservice.findByPlanId(planId);

				// planName.add(pd.getPlanName());
				List<User> lUser = uservice.findUserByActivationMonthAndPd(month, pd);

				userCount = lUser.size();
				double price = pd.getPlanPrice();

				d = new Double(userCount * price);
				revenueList.add(d);

			}
			revList.add(revenueList);

		}
		resp = new ResponseEntity<>(revList, HttpStatus.OK);
		return resp;
	}

	public void printRevenue() {

	}

	@GetMapping("/prediction")
	public ResponseEntity<List<Double>> predict() {
		SimpleRegression regression = new SimpleRegression();
		//List<PlanDetails> plans = pdservice.getAllPlan();
		revList1 = getRevenue().getBody();
		List<Double> predictionList = new ArrayList<Double>();
		for (List<Double> revl : revList1) {
			int monthforML=1;
			for (Double revl2: revl) {
				
				regression.addData(monthforML++, revl2);
			}
			predictionList.add(Math.round(regression.predict(monthforML) * 100D) / 100D);
		}

		//SimpleRegression regression = new SimpleRegression();

		// regression.addData(revList1<<>> , 20);

//		regression.addData(15, 30);
//
//		regression.addData(20, 40);
//
//		System.out.println(regression.getIntercept());
//
//		System.out.println(regression.getSlope());

//		System.out.println(regression.getSlopeStdErr());
//		System.out.println(regression.predict(40));

		ResponseEntity<List<Double>> resp2 = new ResponseEntity<>(predictionList, HttpStatus.OK);
		return resp2;
	}

	@GetMapping("/download")
	public ModelAndView download() {

		// ModelAndView mv=new ModelAndView("pdfview");
		// mv.addObject("planNames",this.planName);

		List<Report> report = new ArrayList<Report>();
		for (List<Double> rl : this.revList) {
			System.out.println();
			report.add(new Report(rl.get(0), rl.get(1), rl.get(2), rl.get(3), rl.get(4), rl.get(5), rl.get(6),
					rl.get(7), rl.get(8), rl.get(9), rl.get(10), rl.get(11)));
		}

		// mv.addObject("planNames",this.planName);
		return new ModelAndView("pdfView", "reports", report);

	}

	public static List<String> getPlanNames() {
		System.out.println("Name:" + planName.get(8) + " " + planName.size());
		return planName;
	}

}
