package com.example.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.boundries.GoodsReceiptProposal;
import com.example.demo.logic.GoodsReceiptProposalInterface;



@RestController
public class SendGoodReceiptProposalController {

	private GoodsReceiptProposalInterface goodsReceiptProposalInterface;
	


	@Autowired
	public void setOutgoingDeliveryProposalInterface(GoodsReceiptProposalInterface goodsReceiptProposalInterface){
		this.goodsReceiptProposalInterface = goodsReceiptProposalInterface;
	}

   @PostMapping(path = "/SendGoodsReceiptProposal", 
               produces = MediaType.APPLICATION_JSON_VALUE,
               consumes = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Map<String, Object>> createGoodsReceiptProposal(@RequestBody GoodsReceiptProposal goodsReceiptProposal) {
        return this.goodsReceiptProposalInterface.CreateAndUploadGoodsReceiptProposal(goodsReceiptProposal);
   }
   
}