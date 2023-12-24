package com.eitanmedical.app.bydexporter.logic;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.eitanmedical.app.bydexporter.boundries.GoodsReceiptProposal;

public interface GoodsReceiptProposalInterface{
    
    public ResponseEntity<Map<String, Object>> CreateAndUploadGoodsReceiptProposal(GoodsReceiptProposal goodsReceiptProposal);
}