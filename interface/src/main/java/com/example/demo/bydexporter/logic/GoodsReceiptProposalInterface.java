package com.example.demo.bydexporter.logic;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.example.demo.bydexporter.boundries.GoodsReceiptProposal;

public interface GoodsReceiptProposalInterface{
    
    public ResponseEntity<Map<String, Object>> CreateAndUploadGoodsReceiptProposal(GoodsReceiptProposal goodsReceiptProposal);
}