package com.eitanmedical.app.bydexporter.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eitanmedical.app.bydexporter.boundries.OutgoingDeliveryProposal;
import com.eitanmedical.app.bydexporter.logic.SendDocumentInterface;


@RestController
public class SendDeliveryProporsalController {

	//private OutgoingDeliveryProposalInterface outgoingDeliveryProposalInterface;


	//@Autowired
	//public void setOutgoingDeliveryProposalInterface(OutgoingDeliveryProposalInterface outgoingDeliveryProposalInterface){
	//	this.outgoingDeliveryProposalInterface = outgoingDeliveryProposalInterface;
	//}

	private SendDocumentInterface sendDocumentInterface;

    @Autowired
	public void setSendDocumentInterface(SendDocumentInterface sendDocumentInterface){
		this.sendDocumentInterface = sendDocumentInterface;
	}

	

   @PostMapping(path = "/SendDeliveryProporsal", 
               produces = MediaType.APPLICATION_JSON_VALUE,
               consumes = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Map<String, Object>> createDeliveryProporsal(Authentication authentication, @RequestBody OutgoingDeliveryProposal outgoingDeliveryProposal) {
		return this.sendDocumentInterface.createAndUploadDeliveryProposal(outgoingDeliveryProposal);
   }
   
}