package com.eitanmedical.app.bydexporter.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;


import com.eitanmedical.app.bydexporter.boundries.GoodsReceiptProposal;
import com.eitanmedical.app.bydexporter.logic.SendDocumentInterface;



@RestController
public class SendGoodReceiptProposalController {

	//private GoodsReceiptProposalInterface goodsReceiptProposalInterface;
	


	//@Autowired
	//public void setOutgoingDeliveryProposalInterface(GoodsReceiptProposalInterface goodsReceiptProposalInterface){
	//	this.goodsReceiptProposalInterface = goodsReceiptProposalInterface;
	//}

   private SendDocumentInterface sendDocumentInterface;

    @Autowired
	public void setSendDocumentInterface(SendDocumentInterface sendDocumentInterface){
		this.sendDocumentInterface = sendDocumentInterface;
	}

   @PostMapping(path = "/SendGoodsReceiptProposal", 
               produces = MediaType.APPLICATION_JSON_VALUE,
               consumes = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Map<String, Object>> createGoodsReceiptProposal(Authentication authentication, @RequestBody GoodsReceiptProposal goodsReceiptProposal) {
      return this.sendDocumentInterface.createAndUploadGoodsReceiptProposal(goodsReceiptProposal);
   }
   
}