package com.example.demo.bydexporter.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bydexporter.boundries.OutgoingDeliveryProposal;
import com.example.demo.bydexporter.logic.OutgoingDeliveryProposalInterface;


@RestController
public class SendDeliveryProporsalController {

	private OutgoingDeliveryProposalInterface outgoingDeliveryProposalInterface;
	//private final ObjectMapper objectMapper = new ObjectMapper();


	@Autowired
	public void setOutgoingDeliveryProposalInterface(OutgoingDeliveryProposalInterface outgoingDeliveryProposalInterface){
		this.outgoingDeliveryProposalInterface = outgoingDeliveryProposalInterface;
	}

   @PostMapping(path = "/SendDeliveryProporsal", 
               produces = MediaType.APPLICATION_JSON_VALUE,
               consumes = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Map<String, Object>> createDeliveryProporsal(@RequestBody OutgoingDeliveryProposal outgoingDeliveryProposal) {
		return this.outgoingDeliveryProposalInterface.CreateAndUploadDeliveryProposal(outgoingDeliveryProposal);
   }
   
}