// ---------------------- CẤU HÌNH ----------------------
const PAGE_SIZE = 10;

// ---------------------- DỮ LIỆU GIẢ (mock data) ----------------------
const patients = [
  { patientId: 1,  name: "Nguyễn Văn An", citizenId: "012345678901", phoneNumber: "0912345678", address: "Hà Nội",      birthDate: "1985-03-10", medicalHistory: "Tăng huyết áp" },
  { patientId: 2,  name: "Trần Thị Bình", citizenId: "012345678902", phoneNumber: "0987654321", address: "Đà Nẵng",      birthDate: "1990-07-22", medicalHistory: "Dị ứng phấn hoa" },
  { patientId: 3,  name: "Lê Văn Cường", citizenId: "012345678903", phoneNumber: "0934567890", address: "TP.HCM",       birthDate: "1982-11-01", medicalHistory: "Hen suyễn" },
  { patientId: 4,  name: "Phạm Thị Dung", citizenId: "012345678904", phoneNumber: "0909123123", address: "Hải Phòng",    birthDate: "1995-05-16", medicalHistory: "Không rõ" },
  { patientId: 5,  name: "Hoàng Văn Đạt", citizenId: "012345678905", phoneNumber: "0977123456", address: "Nghệ An",      birthDate: "1988-09-08", medicalHistory: "Viêm dạ dày" },
  { patientId: 6,  name: "Võ Thị Hạnh", citizenId: "012345678906", phoneNumber: "0911222333", address: "Cần Thơ",       birthDate: "1993-12-30", medicalHistory: "Viêm xoang" },
  { patientId: 7,  name: "Đinh Văn Huy", citizenId: "012345678907", phoneNumber: "0909555666", address: "Thanh Hóa",     birthDate: "1987-04-18", medicalHistory: "Tiểu đường tuýp 2" },
  { patientId: 8,  name: "Bùi Thị Lan", citizenId: "012345678908", phoneNumber: "0966888777", address: "Bắc Ninh",      birthDate: "1992-02-14", medicalHistory: "Không rõ" },
  { patientId: 9,  name: "Ngô Văn Lộc", citizenId: "012345678909", phoneNumber: "0933555777", address: "Quảng Nam",     birthDate: "1984-10-25", medicalHistory: "Viêm gan B" },
  { patientId: 10, name: "Huỳnh Thị Mai", citizenId: "012345678910", phoneNumber: "0901444555", address: "Bình Dương",   birthDate: "1996-06-09", medicalHistory: "Đau nửa đầu" },
  { patientId: 11, name: "Phan Văn Minh", citizenId: "012345678911", phoneNumber: "0913666999", address: "Hà Tĩnh",     birthDate: "1989-01-19", medicalHistory: "Viêm phế quản" },
  { patientId: 12, name: "Trương Thị Nga", citizenId: "012345678912", phoneNumber: "0905999888", address: "Nha Trang",   birthDate: "1991-09-03", medicalHistory: "Không rõ" },
  { patientId: 13, name: "Trịnh Văn Nam", citizenId: "012345678913", phoneNumber: "0966555444", address: "Quảng Ninh",  birthDate: "1983-07-28", medicalHistory: "Cao huyết áp" },
  { patientId: 14, name: "Mai Thị Oanh", citizenId: "012345678914", phoneNumber: "0988333111", address: "Huế",          birthDate: "1997-03-12", medicalHistory: "Viêm da cơ địa" },
  { patientId: 15, name: "Tạ Văn Phú", citizenId: "012345678915", phoneNumber: "0904777888", address: "Vũng Tàu",      birthDate: "1986-08-05", medicalHistory: "Sỏi thận" },
  { patientId: 16, name: "Dương Thị Quỳnh", citizenId: "012345678916", phoneNumber: "0915111222", address: "Lâm Đồng",   birthDate: "1994-11-23", medicalHistory: "Không rõ" },
  { patientId: 17, name: "Nguyễn Văn Sơn", citizenId: "012345678917", phoneNumber: "0907000111", address: "Long An",    birthDate: "1981-01-07", medicalHistory: "Gout" },
  { patientId: 18, name: "Hồ Thị Trang", citizenId: "012345678918", phoneNumber: "0977888999", address: "Kiên Giang",    birthDate: "1993-04-27", medicalHistory: "Viêm họng mãn tính" },
  { patientId: 19, name: "Cao Văn Tuấn", citizenId: "012345678919", phoneNumber: "0933444666", address: "Phú Thọ",      birthDate: "1985-12-15", medicalHistory: "Viêm đại tràng" },
  { patientId: 20, name: "Châu Thị Vân", citizenId: "012345678920", phoneNumber: "0987666444", address: "Sóc Trăng",     birthDate: "1998-05-21", medicalHistory: "Chưa có tiền sử" }
];

// ---------------------- STATE ----------------------
// sortMode: 'id' (mặc định) | 'name'
let sortMode = 'id';
let nameAsc = true;     // chỉ áp dụng khi sortMode === 'name'
let currentPage = 1;

// ---------------------- HỖ TRỢ ----------------------
function formatDate(dateStr){
  if(!dateStr) return "";
  const d = new Date(dateStr);
  if(isNaN(d)) return dateStr;
  return d.toLocaleDateString('vi-VN');
}

function sortByIdAsc(a,b){ return (a.patientId||0) - (b.patientId||0); }
function sortByName(a,b){
  const opts = { sensitivity: "base" };
  return nameAsc
    ? a.name.localeCompare(b.name, "vi", opts)
    : b.name.localeCompare(a.name, "vi", opts);
}

// ---------------------- RENDER BẢNG ----------------------
function renderTable(){
  let dataSorted;
  if (sortMode === 'name') {
    dataSorted = [...patients].sort(sortByName);
  } else {
    // mặc định theo ID tăng dần
    dataSorted = [...patients].sort(sortByIdAsc);
  }

  const totalPages = Math.max(1, Math.ceil(dataSorted.length / PAGE_SIZE));
  currentPage = Math.min(currentPage, totalPages);

  const startIndex = (currentPage - 1) * PAGE_SIZE;
  const pageItems = dataSorted.slice(startIndex, startIndex + PAGE_SIZE);

  const tbody = document.getElementById("tableBody");
  tbody.innerHTML = pageItems.map(p => `
    <tr>
      <td>${p.patientId ?? ""}</td>
      <td>${p.name ?? ""}</td>
      <td>${p.citizenId ?? ""}</td>
      <td>${p.phoneNumber ?? ""}</td>
      <td>${p.address ?? ""}</td>
      <td>${formatDate(p.birthDate)}</td>
      <td>${p.medicalHistory ?? ""}</td>
    </tr>
  `).join("");

  renderPagination(totalPages);
  updateSortIcon();
}

function updateSortIcon(){
  const icon = document.getElementById("sortIcon");
  if (!icon) return;

  if (sortMode === 'name') {
    // tên: A→Z hoặc Z→A
    icon.className = nameAsc
      ? "fa-solid fa-arrow-up-wide-short"
      : "fa-solid fa-arrow-down-short-wide";
    icon.title = nameAsc ? "Đang sắp xếp theo Tên (A→Z)" : "Đang sắp xếp theo Tên (Z→A)";
  } else {
    // đang sort theo ID tăng dần (mặc định)
    icon.className = "fa-solid fa-arrow-up-wide-short";
    icon.title = "Hiện đang sắp xếp theo ID (1→…); bấm để sắp xếp theo Tên";
  }
}

function renderPagination(totalPages){
  const pag = document.getElementById("pagination");
  let html = "";

  html += `<button class="page-btn" ${currentPage === 1 ? "disabled" : ""} data-page="${currentPage - 1}">
            <i class="fa-solid fa-angle-left"></i>
          </button>`;

  for (let i = 1; i <= totalPages; i++){
    html += `<button class="page-btn ${i === currentPage ? "active" : ""}" data-page="${i}">
               ${i}
             </button>`;
  }

  html += `<button class="page-btn" ${currentPage === totalPages ? "disabled" : ""} data-page="${currentPage + 1}">
            <i class="fa-solid fa-angle-right"></i>
          </button>`;

  pag.innerHTML = html;

  pag.querySelectorAll(".page-btn").forEach(btn => {
    btn.addEventListener("click", () => {
      const p = parseInt(btn.getAttribute("data-page"), 10);
      if(!isNaN(p)){
        currentPage = Math.max(1, Math.min(p, totalPages));
        renderTable();
      }
    });
  });
}

// ---------------------- SỰ KIỆN ----------------------
document.addEventListener("DOMContentLoaded", () => {
  // Mặc định: sort theo ID tăng dần
  sortMode = 'id';
  nameAsc = true;
  currentPage = 1;

  const sortBtn = document.getElementById("sortNameBtn");
  if (sortBtn) {
    sortBtn.addEventListener("click", () => {
      // Chuyển sang sort theo Tên (nếu đang ở ID) hoặc đảo chiều (nếu đã ở Tên)
      if (sortMode !== 'name') {
        sortMode = 'name';
        nameAsc = true; // lần đầu vào Tên => A→Z
      } else {
        nameAsc = !nameAsc; // đang ở Tên thì đảo A/Z
      }
      currentPage = 1;
      renderTable();
    });
  }

  renderTable();
});
