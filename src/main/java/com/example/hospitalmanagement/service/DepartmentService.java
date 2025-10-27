package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.model.Department;
import com.example.hospitalmanagement.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    /* ====== DANH SÁCH + SORT TOÀN BỘ RỒI MỚI PHÂN TRANG ====== */
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
    public Page<Department> searchDepartments(String keyword, int page, int size, String sortDir) {
        List<Department> all = (keyword == null || keyword.trim().isEmpty())
                ? departmentRepository.findAll()
                : departmentRepository.searchDepartments(keyword.trim());

        // Sắp xếp theo từ cuối cùng của departmentName (bỏ dấu, không phân biệt hoa/thường)
        Comparator<Department> cmp = Comparator.comparing(d -> lastTokenKey(d.getDepartmentName()));
        if ("desc".equalsIgnoreCase(sortDir)) cmp = cmp.reversed();
        all.sort(cmp);

        return toPage(all, page, size);
    }

    private String lastTokenKey(String s) {
        if (s == null) return "";
        String trimmed = s.trim();
        if (trimmed.isEmpty()) return "";
        String[] parts = trimmed.split("\\s+");
        String last = parts[parts.length - 1];
        // Bỏ dấu + lower để so sánh ổn định với tiếng Việt
        String noAccent = Normalizer.normalize(last, Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "");
        return noAccent.toLowerCase(Locale.ROOT);
    }

    private Page<Department> toPage(List<Department> src, int page, int size) {
        int fromIndex = Math.min(page * size, src.size());
        int toIndex = Math.min(fromIndex + size, src.size());
        List<Department> content = src.subList(fromIndex, toIndex);
        return new PageImpl<>(content, PageRequest.of(page, size), src.size());
    }

    /* ====== CRUD ====== */
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khoa với ID: " + id));
    }

    public boolean deleteDepartmentById(Long id) {
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Department addDepartment(Department department) {
        if (department.getDepartmentName() == null || department.getDepartmentName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khoa không được để trống");
        }
        if (departmentRepository.existsByDepartmentName(department.getDepartmentName())) {
            throw new IllegalArgumentException("Tên khoa đã tồn tại");
        }
        validateBedCount(department.getBedCount());
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department updatedDepartment) {
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khoa cần cập nhật"));

        if (updatedDepartment.getDepartmentName() == null || updatedDepartment.getDepartmentName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khoa không được để trống");
        }
        if (!existing.getDepartmentName().equals(updatedDepartment.getDepartmentName()) &&
                departmentRepository.existsByDepartmentName(updatedDepartment.getDepartmentName())) {
            throw new IllegalArgumentException("Tên khoa đã tồn tại");
        }
        validateBedCount(updatedDepartment.getBedCount());

        existing.setDepartmentName(updatedDepartment.getDepartmentName());
        existing.setDescription(updatedDepartment.getDescription());
        existing.setHeadOfDepartment(updatedDepartment.getHeadOfDepartment());
        existing.setLocation(updatedDepartment.getLocation());
        existing.setBedCount(updatedDepartment.getBedCount());
        return departmentRepository.save(existing);
    }

    private void validateBedCount(String bedCountStr) {
        try {
            int beds = Integer.parseInt(bedCountStr);
            if (beds < 0) throw new IllegalArgumentException("Số giường phải >= 0");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Số giường phải là số hợp lệ");
        }
    }

    /* ====== KPI ====== */
    public int sumAllBeds() {
        return departmentRepository.findAll()
                .stream()
                .mapToInt(d -> safeParseInt(d.getBedCount()))
                .sum();
    }

    public long countActiveBedDepartments() {
        return departmentRepository.findAll()
                .stream()
                .filter(d -> safeParseInt(d.getBedCount()) > 0)
                .count();
    }

    private int safeParseInt(String s) {
        if (s == null) return 0;
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return 0;
        }
    }

}