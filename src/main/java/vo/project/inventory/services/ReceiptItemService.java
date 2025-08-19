package vo.project.inventory.services;

import vo.project.inventory.dtos.ReceiptItemCreateDto;
import vo.project.inventory.dtos.ReceiptItemResponseDto;
import vo.project.inventory.dtos.ReceiptItemUpdateDto;

import java.util.List;
import java.util.UUID;

public interface ReceiptItemService {

    ReceiptItemResponseDto addItemToReceipt(ReceiptItemCreateDto requestDTO);

    List<ReceiptItemResponseDto> getItemsByReceiptId(UUID receiptId);

    List<ReceiptItemResponseDto> getItemsByResourceId(UUID resourceId);

    ReceiptItemResponseDto getReceiptItem(UUID receiptId, UUID resourceId);

    ReceiptItemResponseDto updateReceiptItem(UUID receiptId, UUID resourceId, ReceiptItemUpdateDto updateDto);

    void removeItemFromReceipt(UUID receiptId, UUID resourceId);

    void removeAllItemsFromReceipt(UUID receiptId);



}
