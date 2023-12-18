package com.example.demo.logic;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.example.demo.boundries.GoodsReceiptProposal;

public interface GoodsReceiptProposalInterface{
    
    public ResponseEntity<Map<String, Object>> CreateAndUploadGoodsReceiptProposal(GoodsReceiptProposal goodsReceiptProposal);
}