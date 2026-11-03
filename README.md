# A2ThuanTran_SE1910 - FU News Management System

Assignment 02 - SBA301 - Implement CRUD Application with RESTful Web Service and ReactJS.

Du an gom 2 phan:

- `backend/` : Spring Boot 3 + Spring Data JPA + MS SQL Server (RESTful API, 3-layer architecture)
- `frontend/` : ReactJS + axios (giao dien web)

## 1. Yeu cau moi truong

- Java 21 tro len
- Node.js 18 tro len (da test voi Node 24)
- MS SQL Server dang chay (local hoac Docker), co quyen tao database moi
- Khong can cai Maven rieng, du an co san Maven Wrapper (`mvnw` / `mvnw.cmd`)

## 2. Tao database

Mo SQL Server Management Studio hoac sqlcmd, chay:

```sql
CREATE DATABASE SBA301_Ass2;
```

(Hibernate se tu dong tao cac bang khi backend khoi dong lan dau, nho cau hinh
`spring.jpa.hibernate.ddl-auto=update`)

## 3. Cau hinh ket noi database

File cau hinh: `backend/src/main/resources/application.properties`.

Mac dinh backend se ket noi toi:

- host: `localhost`, port: `1433`
- database: `SBA301_Ass2`
- username: `sa`, password: `12`

Neu may ban dung thong tin khac, co the sua truc tiep file tren, hoac set bien moi
truong truoc khi chay (khong can sua code):

```bash
export DB_HOST=localhost
export DB_PORT=1433
export DB_NAME=SBA301_Ass2
export DB_USERNAME=sa
export DB_PASSWORD=12
```

## 4. Chay Backend (Spring Boot)

```bash
cd backend
./mvnw spring-boot:run
```

Tren Windows (PowerShell):

```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

Backend chay o: `http://localhost:8080`

Lan dau chay, ung dung se tu dong tao san 2 tai khoan mau va vai danh muc/tin tuc
mau (xem `DataSeeder.java`):

| Vai tro | Email | Mat khau |
| --- | --- | --- |
| Admin | admin@fu.edu.vn | Admin@123 |
| Staff | staff@fu.edu.vn | Staff@123 |

## 5. Chay Frontend (ReactJS)

```bash
cd frontend
npm install
npm run dev
```

Frontend chay o: `http://localhost:5173`

## 6. Chuc nang chinh

- Xem tin tuc cong khai (khong can dang nhap), tim kiem, loc theo danh muc
- Dang nhap bang Email/Mat khau (phan quyen Admin / Staff bang JWT)
- **Admin**: quan ly tai khoan (CRUD, khong the xoa tai khoan da tao tin)
- **Staff**:
  - Quan ly danh muc (CRUD, khong the xoa danh muc da co tin su dung)
  - Quan ly tin tuc (CRUD, gan tag)
  - Xem lich su tin da dang
  - Quan ly ho so ca nhan

## 7. Kien truc Backend (3-Layer)

```
controller/   -> nhan request HTTP, goi service, tra ve response
service/      -> xu ly nghiep vu (business logic)
repository/   -> thao tac voi database qua Spring Data JPA
entity/       -> cac bang trong database
dto/          -> du lieu trao doi giua client va server
security/     -> JWT, kiem tra dang nhap / phan quyen
exception/    -> xu ly loi chung
```

## 8. Ghi chu

Theo yeu cau de bai, ten database/project chuan la `A2StudentName_ClassCode`. Du an
nay dat ten project la `A2ThuanTran_SE1910` (sinh vien Tran Cong Thuan, lop SE1910),
rieng ten database duoc dat la `SBA301_Ass2` theo yeu cau cua sinh vien khi thiet lap.
Ban co the doi lai ten database bang cach sua `DB_NAME` trong buoc 3.
