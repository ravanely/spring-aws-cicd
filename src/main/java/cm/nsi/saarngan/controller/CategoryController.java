package cm.nsi.saarngan.controller;

import cm.nsi.saarngan.entity.Category;
import cm.nsi.saarngan.exceptions.CategoryNotFoundException;
import cm.nsi.saarngan.services.CategoryService;
import cm.nsi.saarngan.utils.FileUploadUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * created by : Ravanely
 * create at : 06/04/2022, 14:03, mer.
 * saar-ngan
 **/
@Controller
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/categories")
    public String listFirstPage(Model model) {
        return listByPage(1, model, "name", "asc", null);
    }

    @GetMapping("/categories/page/{pageNum}")
    public String listByPage(@PathVariable("pageNum") int pageNum, Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword) {

        Page<Category> page = service.listByPage(pageNum, sortField, sortDir, keyword);
        List<Category> listCategories = page.getContent();

        long startCount = (long) (pageNum - 1) * CategoryService.CATEGORIES_PER_PAGE + 1;
        long endCount = startCount + CategoryService.CATEGORIES_PER_PAGE - 1;

        if (endCount > page.getTotalElements())
            endCount = page.getTotalElements();

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("startCount", startCount);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);

        return "categories/categories";
    }

    @GetMapping("/categories/new")
    public String newCategory(Model model) {
        List<Category> listCategories = service.listCategoriesUsedInForm();

        model.addAttribute("category", new Category());
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("pageTitle", "Create New Category");
        return "categories/category_form";
    }

    @PostMapping("/categories/save")
    public String saveCategory(Category category, @RequestParam("fileImage") MultipartFile multipartFile, RedirectAttributes redirectAttributes) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            category.setImage(fileName);

            service.save(category);
            String uploadDir = "/category-images/" + category.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else
            service.save(category);


        redirectAttributes.addFlashAttribute("message", "The category has been saved successfully");
        return "redirect:/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String editCategory(Model model, @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            Category category = service.get(id);
            List<Category> listCategories = service.listCategoriesUsedInForm();

            model.addAttribute("category", category);
            model.addAttribute("listCategories", listCategories);
            model.addAttribute("pageTitle", "Edit Category (ID: " + id + ")");

            return "categories/category_form";
        } catch (CategoryNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/categories";
        }
    }
}
