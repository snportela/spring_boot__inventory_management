package vo.project.inventory.services.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import vo.project.inventory.domain.Receipt;
import vo.project.inventory.domain.ReceiptItem;
import vo.project.inventory.domain.ReceiptItemId;
import vo.project.inventory.domain.Resource;
import vo.project.inventory.dtos.ReceiptItemCreateDto;
import vo.project.inventory.dtos.ReceiptItemResponseDto;
import vo.project.inventory.dtos.ReceiptItemUpdateDto;
import vo.project.inventory.exceptions.AlreadyExistsException;
import vo.project.inventory.exceptions.NotFoundException;
import vo.project.inventory.mappers.ReceiptItemMapper;
import vo.project.inventory.repositories.ReceiptItemRepository;
import vo.project.inventory.repositories.ReceiptRepository;
import vo.project.inventory.repositories.ResourceRepository;
import vo.project.inventory.services.ReceiptItemService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ReceiptItemServiceImpl implements ReceiptItemService {

    private final ReceiptItemRepository receiptItemRepository;
    private final ReceiptRepository receiptRepository;
    private final ResourceRepository resourceRepository;
    private final ReceiptItemMapper receiptItemMapper;

    public ReceiptItemServiceImpl(ReceiptItemRepository receiptItemRepository, ReceiptRepository receiptRepository, ResourceRepository resourceRepository, ReceiptItemMapper receiptItemMapper) {
        this.receiptItemRepository = receiptItemRepository;
        this.receiptRepository = receiptRepository;
        this.resourceRepository = resourceRepository;
        this.receiptItemMapper = receiptItemMapper;
    }

    @Override
    public ReceiptItemResponseDto addItemToReceipt(ReceiptItemCreateDto createDto) {
        Receipt foundReceipt = receiptRepository
                .findById(createDto.receiptId())
                .orElseThrow(() -> new NotFoundException("Receipt not found with ID: " + createDto.receiptId()));

        Resource foundResource = resourceRepository
                .findById(createDto.resourceId())
                .orElseThrow(() -> new NotFoundException("Resource not found with ID: " + createDto.resourceId()));

        if (receiptItemRepository.existsByReceiptIdAndResourceId(createDto.receiptId(), createDto.resourceId())) {
            throw new AlreadyExistsException("Resource already exists in this receipt");
        }

        ReceiptItem saved = receiptItemRepository.save(receiptItemMapper.toEntity(createDto));

        return receiptItemMapper.toResponseDto(saved, foundReceipt, foundResource);

    }

    @Override
    public List<ReceiptItemResponseDto> getItemsByReceiptId(UUID receiptId) {
        receiptRepository
                .findById(receiptId)
                .orElseThrow(() -> new NotFoundException("Receipt not found with ID: " + receiptId));

        List<Object[]> results = receiptItemRepository.findByReceiptId(receiptId);
        return receiptItemMapper.mapQueryResultList(results);
    }

    @Override
    public List<ReceiptItemResponseDto> getItemsByResourceId(UUID resourceId) {
        resourceRepository
                .findById(resourceId)
                .orElseThrow(() -> new NotFoundException("Resource not found with ID: " + resourceId));

        List<Object[]> results = receiptItemRepository.findByResourceId(resourceId);
        return receiptItemMapper.mapQueryResultList(results);
    }

    @Override
    public ReceiptItemResponseDto getReceiptItem(UUID receiptId, UUID resourceId) {
        ReceiptItemId id = new ReceiptItemId(receiptId, resourceId);
        ReceiptItem item = receiptItemRepository.findById(id)
                .filter(ri -> ri.getDeletedAt() == null)
                .orElseThrow(() -> new NotFoundException("Receipt item not found."));

        Receipt receipt = receiptRepository.findById(receiptId).orElseThrow(() -> new NotFoundException("Receipt not found with id:" + receiptId));
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(() -> new NotFoundException("Resource not found with id:" + resourceId));

        return receiptItemMapper.toResponseDto(item, receipt, resource);
    }

    @Override
    public ReceiptItemResponseDto updateReceiptItem(UUID receiptId, UUID resourceId, ReceiptItemUpdateDto updateDto) {
        ReceiptItemId id = new ReceiptItemId(receiptId, resourceId);
        ReceiptItem item = receiptItemRepository.findById(id)
                .filter(ri -> ri.getDeletedAt() == null)
                .orElseThrow(() -> new NotFoundException("Receipt item not found."));

        receiptItemMapper.updateEntityFromDTO(updateDto, item);

        ReceiptItem updated = receiptItemRepository.save(item);

        Receipt receipt = receiptRepository.findById(receiptId).orElseThrow(() -> new NotFoundException("Receipt not found with ID: " + receiptId));
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(() -> new NotFoundException("Resource not found with ID: " + resourceId));

        return receiptItemMapper.toResponseDto(updated, receipt, resource);
    }

    @Override
    public void removeItemFromReceipt(UUID receiptId, UUID resourceId) {
        if (!receiptItemRepository.existsByReceiptIdAndResourceId(receiptId, resourceId)) {
            throw new NotFoundException("Receipt item not found.");
        }
        receiptItemRepository.deleteByReceiptIdAndResourceId(receiptId, resourceId);
    }

    @Override
    public void removeAllItemsFromReceipt(UUID receiptId) {
        receiptRepository.findById(receiptId).orElseThrow(() -> new NotFoundException("Receipt not found with ID: " + receiptId));
        receiptItemRepository.deleteByReceiptId(receiptId);
    }
}
