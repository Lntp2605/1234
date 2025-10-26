document.addEventListener("DOMContentLoaded", function () {
    const doctorTableBody = document.querySelector("tbody");
    const deleteModal = new bootstrap.Modal(document.getElementById("deleteModal"));
    const confirmDeleteBtn = document.getElementById("confirmDeleteBtn");
    const patientNameToDelete = document.getElementById("patientNameToDelete");


    // ========== HÀM HIỂN THỊ DỮ LIỆU ==========
    function renderDoctors(doctors) {
        doctorTableBody.innerHTML = ""; // xóa hàng "Không có dữ liệu"
        doctors.forEach((doc) => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td class="text-center"><input type="checkbox" class="form-check-input" value="${doc.id}"></td>
                <td>${doc.doctorCode}</td>
                <td>${doc.fullName}</td>
                <td>${doc.department}</td>
                <td>${doc.gender === "Nam"
                ? '<span class="text-primary"><i class="fas fa-mars me-1"></i>Nam</span>'
                : '<span class="text-danger"><i class="fas fa-venus me-1"></i>Nữ</span>'}</td>
                <td>${doc.phoneNumber}</td>
                <td>${doc.email}</td>
                <td>${getStatusBadge(doc.status)}</td>
                <td class="text-center">
                    <div class="btn-group" role="group">
                        <a href="#" class="btn btn-sm btn-outline-info" title="Xem chi tiết"><i class="fas fa-eye"></i></a>
                        <a href="#" class="btn btn-sm btn-outline-warning" title="Chỉnh sửa"><i class="fas fa-edit"></i></a>
                        <button type="button" class="btn btn-sm btn-outline-danger" title="Xóa"
                            onclick="confirmDelete(${doc.id}, '${doc.fullName}')">
                            <i class="fas fa-trash"></i>
                        </button>
                    </div>
                </td>
            `;
            doctorTableBody.appendChild(row);
        });
    }

    // ========== HÀM STATUS BADGE ==========
    function getStatusBadge(status) {
        switch (status) {
            case "Đang làm việc":
                return `<span class="badge bg-success">${status}</span>`;
            case "Nghỉ phép":
                return `<span class="badge bg-secondary">${status}</span>`;
            case "Đang trực":
                return `<span class="badge bg-warning text-dark">${status}</span>`;
            default:
                return `<span class="badge bg-info">${status}</span>`;
        }
    }

    // ========== HÀM XÁC NHẬN XÓA ==========
    window.confirmDelete = function (id, name) {
        patientNameToDelete.textContent = name;
        confirmDeleteBtn.onclick = function () {
            deleteModal.hide();
            alert(`Đã xóa bác sĩ: ${name} (ID: ${id})`);
        };
        deleteModal.show();
    };

    // ========== TỰ ĐỘNG HIỂN THỊ DỮ LIỆU MẪU ==========
    const hasServerData = doctorTableBody.querySelectorAll("tr").length > 1;
    if (!hasServerData) {
        console.log("Không có dữ liệu backend → hiển thị dữ liệu mẫu");
        renderDoctors(sampleDoctors);
    } else {
        console.log("Đã có dữ liệu từ backend → không cần hiển thị mẫu");
    }
});