package az.enjooy.service.implementation;

import az.enjooy.dto.ItemCategory.ItemCategorySaveDTO;
import az.enjooy.dto.ItemCategory.ItemCategoryTreeDTO;
import az.enjooy.model.entity.ItemCategory;
import az.enjooy.repository.ItemCategoryRepository;
import az.enjooy.service.abstraction.ItemCategoryService;
import az.enjooy.util.ListUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemCategoryServiceImpl implements ItemCategoryService {

    private final ItemCategoryRepository repository;

    @Override
    public Page<ItemCategoryTreeDTO> getAllParents(int page, int size) {
        List<ItemCategoryTreeDTO> parentCategories = new ArrayList<>();
        repository.findAllByParentIsNull().forEach(x->parentCategories.add(new ItemCategoryTreeDTO(x)));
        return ListUtil.convertToPage(parentCategories, size, page);
    }

    @Override
    public ItemCategory getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ItemCategory save(ItemCategorySaveDTO saveDTO) {
        ItemCategory category = new ItemCategory();
        if (saveDTO.getId() != null)
            category = repository.findById(saveDTO.getId()).orElse(new ItemCategory());
        ItemCategory parentCategory = null;
        if (saveDTO.getParentId() != null) {
            parentCategory = repository.findById(saveDTO.getParentId()).get();
            category.setParent(parentCategory);
        }

        if (saveDTO.getId() != null && !category.getName().equals(saveDTO.getName()))
            changePathRecursively(category, saveDTO.getName());

        category.setName(saveDTO.getName());
        category.setFullPath((saveDTO.getParentId() != null ? parentCategory.getFullPath() + " / " : "") + saveDTO.getName());
        repository.save(category);
        return category;
    }

    public void changePath(ItemCategory parent, String name) {
        parent.setFullPath((parent.getParent() != null ? parent.getParent().getFullPath() + " / " : "") + name);
    }

    public void changePathRecursively(ItemCategory parent, String name) {
        changePath(parent, name);
        List<ItemCategory> children = repository.findByParentId(parent.getId());
        children.forEach(child -> changePathRecursively(child, child.getName()));
    }

    @Override
    public List<ItemCategoryTreeDTO> getAll() {
        List<ItemCategoryTreeDTO> categoryTree = new ArrayList<>();
        List<ItemCategory> parentCategories = repository.findAllByParentIsNull();
        parentCategories.forEach(parent -> {
            ItemCategoryTreeDTO branchCategory = findLightChildren(parent);
            categoryTree.add(branchCategory);
        });
        return categoryTree;
    }

    public ItemCategoryTreeDTO findLightChildren(ItemCategory parent) {
        ItemCategoryTreeDTO light = new ItemCategoryTreeDTO(parent);
        List<ItemCategory> children = repository.findByParentId(parent.getId());
        List<ItemCategoryTreeDTO> subChildren = new ArrayList<>();
        children.forEach(child -> {
            ItemCategoryTreeDTO lightChildren = findLightChildren(child);
            lightChildren.setParentName(child.getParent().getName());
            lightChildren.setParentId(child.getParent().getId());
            subChildren.add(lightChildren);
        });
        light.setChildren(subChildren);
        return light;
    }
}
