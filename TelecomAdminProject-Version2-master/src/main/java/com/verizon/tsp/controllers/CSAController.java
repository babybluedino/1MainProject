package com.verizon.tsp.controllers;

import java.util.List;

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

import com.verizon.tsp.models.CSA;
import com.verizon.tsp.models.CSApost;
import com.verizon.tsp.models.TelecomCircle;
import com.verizon.tsp.models.TicketStatus;
import com.verizon.tsp.models.Tickets;
import com.verizon.tsp.services.CSAService;
import com.verizon.tsp.services.TCircleService;

@RestController
@RequestMapping("/csa")
@CrossOrigin(origins= {"http://localhost:4200"})
public class CSAController {

	@Autowired
	CSAService csaservice;
	
	@Autowired
	TCircleService tcservice;
	
	
	@GetMapping
	public ResponseEntity<List<CSA>> getAllCSA(){
		return new ResponseEntity<>(csaservice.getAllCSA(),HttpStatus.OK);
	}
	
	@GetMapping("/{csaId}")
	public ResponseEntity<CSA> findByCSAId(@PathVariable("csaId") long csaId) {
		
		ResponseEntity<CSA> resp;
		
		CSA csa = csaservice.findByCSAId(csaId);
		
		if (csa == null)
			resp = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			resp = new ResponseEntity<>(csa, HttpStatus.OK);
		
		return resp;
	}
	
	@PostMapping
	public ResponseEntity<CSA> createCSA(@RequestBody CSApost csapost) {
		ResponseEntity<CSA> resp = null;
		
		CSA csa=new CSA();
		csa.setCsaEmailId(csapost.getCsaEmailId());
		csa.setCsaFname(csapost.getCsaFname());
		csa.setCsaLname(csapost.getCsaLname());
		csa.setCsaMobileNo(csapost.getCsaMobileNo());
		csa.setCsaPrimaryLang(csapost.getCsaPrimaryLang());
		
		TelecomCircle tc=new TelecomCircle();
		tc=tcservice.findByTelecomCircleId(csapost.getTcId());
		csa.setTc(tc);
		
		
		CSA csa_return = csaservice.createCSA(csa);
		
		if(csa_return != null) {
			resp = new ResponseEntity<>(csa_return, HttpStatus.OK);
		} else
			resp = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		return resp;
	}
			
	@PutMapping
	public ResponseEntity<CSA> updateCSA(@RequestBody CSA csa) {
		ResponseEntity<CSA> resp = null;
		
		CSA csa_return = csaservice.updateCSA(csa);
		
		if(csa_return != null) {
			System.out.println("CSAAA:" + csa_return);
			resp = new ResponseEntity<>(csa_return, HttpStatus.OK);
		} else
			resp = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		return resp;
	}
	
	@DeleteMapping("/{csaId}")
	public ResponseEntity<Void> deleteCSA(@PathVariable("csaId") int csaId) {
		ResponseEntity<Void> resp = null;

		if (csaservice.deleteCSA(csaId))
			resp = new ResponseEntity<>(HttpStatus.OK);
		else
			resp = new ResponseEntity<>(HttpStatus.NOT_FOUND);

		return resp;
	}
	
/*	@GetMapping("/telecom/{tcId}")
	public ResponseEntity<List<CSA>> findTicketByUser(@PathVariable("tcId") long tcId) {
		
		TelecomCircle tc = tcservice.findByTelecomCircleId(tcId);
		
		ResponseEntity<List<CSA>> resp;
		
		List<CSA> csa_return = csaservice.findByTc(tc);
		
		if (csa_return == null)
			resp = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			resp = new ResponseEntity<>(csa_return, HttpStatus.OK);
		
		return resp;
	}*/
	
	@GetMapping("/telecom/{city}")
	public ResponseEntity<List<CSA>> findTicketByUser(@PathVariable("city") String city) {
		
		TelecomCircle tc = tcservice.findTCircleByCity(city);
		
		ResponseEntity<List<CSA>> resp;
		
		List<CSA> csa_return = csaservice.findByTc(tc);
		
		if (csa_return == null)
			resp = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			resp = new ResponseEntity<>(csa_return, HttpStatus.OK);
		
		return resp;
	}
	
	 @GetMapping("/download")
	    public ModelAndView download() {
	        return new ModelAndView("pdfView","users", csaservice.getAllCSA());
	        
		}
	 @GetMapping("/count")
		public ResponseEntity<Integer> getOpenTicketcount(){
			
			ResponseEntity<Integer> resp = null;
			
			List<CSA> csa= csaservice.getAllCSA();
			int c=csa.size();
			Integer it=new Integer(c);
			return new ResponseEntity<>(it,HttpStatus.OK);		
			
		}


}
