package com.eitanmedical.app.bydexporter.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.Authentication;

import com.eitanmedical.app.bydexporter.boundries.InboundDeliveryNote;
import com.eitanmedical.app.bydexporter.logic.SendDocumentInterface;

@RestController
public class SendInboundDeliveryNoteController {

    private SendDocumentInterface sendDocumentInterface;

    @Autowired
	public void setSendDocumentInterface(SendDocumentInterface sendDocumentInterface){
		this.sendDocumentInterface = sendDocumentInterface;
	}

    @PostMapping(path = "/SendInboundDeliveryNote", 
               produces = MediaType.APPLICATION_JSON_VALUE,
               consumes = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Map<String, Object>> createGoodsReceiptProposal(Authentication authentication, @RequestBody InboundDeliveryNote inboundDeliveryNote) {
      return this.sendDocumentInterface.createAndUploadInboundDeliveryNote(inboundDeliveryNote);
   }
}
