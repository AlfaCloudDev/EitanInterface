package com.eitanmedical.app.bydexporter.logic;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.eitanmedical.app.bydexporter.boundries.GoodsReceiptProposal;
import com.eitanmedical.app.bydexporter.boundries.InboundDeliveryNote;
import com.eitanmedical.app.bydexporter.boundries.OutgoingDeliveryProposal;

public interface SendDocumentInterface {
    
    public ResponseEntity<Map<String, Object>> createAndUploadDeliveryProposal(OutgoingDeliveryProposal outgoingDeliveryProposal);
    public ResponseEntity<Map<String, Object>> createAndUploadGoodsReceiptProposal(GoodsReceiptProposal goodsReceiptProposal);
    public ResponseEntity<Map<String, Object>> createAndUploadInboundDeliveryNote(InboundDeliveryNote inboundDeliveryNote);
}
