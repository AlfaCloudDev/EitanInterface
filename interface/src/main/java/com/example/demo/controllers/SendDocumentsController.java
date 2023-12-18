package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.boundries.OutgoingDeliveryProposal;
import com.example.demo.logic.OutgoingDeliveryProposalInterface;


@RestController
public class SendDocumentsController {

	private OutgoingDeliveryProposalInterface outgoingDeliveryProposalInterface;
	//private final ObjectMapper objectMapper = new ObjectMapper();


	@Autowired
	public void setOutgoingDeliveryProposalInterface(OutgoingDeliveryProposalInterface outgoingDeliveryProposalInterface){
		this.outgoingDeliveryProposalInterface = outgoingDeliveryProposalInterface;
	}

   @PostMapping(path = "/SendDeliveryProporsal", 
               produces = MediaType.APPLICATION_JSON_VALUE,
               consumes = MediaType.APPLICATION_JSON_VALUE)
   public String createDeliveryProporsal(@RequestBody OutgoingDeliveryProposal outgoingDeliveryProposal) {
		System.out.println(outgoingDeliveryProposal);
		//return outgoingDeliveryProposal.toString();
		return this.outgoingDeliveryProposalInterface.CreateAndUploadDeliveryProposal(outgoingDeliveryProposal);
    	
   }
   
}