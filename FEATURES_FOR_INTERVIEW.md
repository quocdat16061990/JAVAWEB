# 🏆 Tính năng cực khó để luyện phỏng vấn Java Spring Boot

> Project hiện tại: **Quản lý bất động sản (Real Estate Agent)**
> Spring Boot 4.0.6 + Java 25 + JWT + REST API
> Database: MySQL (building, users, customer, transaction, assignment...)

---

## 📋 Mục lục

| # | Tính năng | Độ khó | Kỹ thuật chính |
|---|-----------|--------|----------------|
| 1 | [Search nâng cao + Elasticsearch](#1-search-nâng-cao--elasticsearch) | 🔥🔥🔥🔥🔥 | Elasticsearch, Full-text search |
| 2 | [Đa luồng xử lý báo cáo](#2-đa-luồng-xử-lý-báo-cáo) | 🔥🔥🔥🔥🔥 | ThreadPool, CompletableFuture |
| 3 | [Caching Redis cho API](#3-caching-redis-cho-api) | 🔥🔥🔥🔥 | Redis, Cache Aside Pattern |
| 4 | [Rate Limiting + Chống Brute Force](#4-rate-limiting--chống-brute-force) | 🔥🔥🔥🔥 | Bucket4j, Redis |
| 5 | [Import/Export Excel + PDF](#5-importexport-excel--pdf) | 🔥🔥🔥 | Apache POI, JasperReports |
| 6 | [Phân quyền chi tiết (Method Security)](#6-phân-quyền-chi-tiết-method-security) | 🔥🔥🔥🔥🔥 | @PreAuthorize, Custom PermissionEvaluator |
| 7 | [Refresh Token + Remember Me](#7-refresh-token--remember-me) | 🔥🔥🔥🔥 | JWT Refresh, Redis blacklist |
| 8 | [WebSocket + Thông báo real-time](#8-websocket--thông-báo-real-time) | 🔥🔥🔥🔥🔥 | WebSocket, STOMP, SSE |
| 9 | [Batch Processing + Scheduled Tasks](#9-batch-processing--scheduled-tasks) | 🔥🔥🔥🔥 | @Scheduled, Spring Batch, Quartz |
| 10 | [Dynamic Query with Specification](#10-dynamic-query-with-specification) | 🔥🔥🔥🔥🔥 | JPA Specifications, Criteria API |
| 11 | [Multi-tenant (Phân tách dữ liệu khách hàng)](#11-multi-tenant-phân-tách-dữ-liệu-khách-hàng) | 🔥🔥🔥🔥🔥🔥 | Multi-tenant, Database per tenant |
| 12 | [CQRS Pattern (Tách đọc/ghi)](#12-cqrs-pattern-tách-đọcghi) | 🔥🔥🔥🔥🔥🔥🔥 | CQRS, Event Sourcing, RabbitMQ |
| 13 | [Microservice Split (Tách thành services nhỏ)](#13-microservice-split-tách-thành-services-nhỏ) | 🔥🔥🔥🔥🔥🔥🔥 | Spring Cloud, Eureka, Gateway |
| 14 | [Unit Test + Integration Test + TDD](#14-unit-test--integration-test--tdd) | 🔥🔥🔥🔥🔥🔥 | JUnit 5, Mockito, Testcontainers |
| 15 | [Hóa đơn điện tử (PDF + QR Code)](#15-hóa-đơn-điện-tử-pdf--qr-code) | 🔥🔥🔥🔥🔥🔥 | iText, ZXing, QR Code |
| 16 | [Chat Real-time giữa Staff và Admin](#16-chat-real-time-giữa-staff-và-admin) | 🔥🔥🔥🔥🔥🔥 | WebSocket + MongoDB |
| 17 | [Automated Deployment CI/CD](#17-automated-deployment-cicd) | 🔥🔥🔥🔥🔥🔥🔥 | Docker, Jenkins, GitHub Actions |
| 18 | [OAuth2 Login (Google/Facebook)](#18-oauth2-login-googlefacebook) | 🔥🔥🔥🔥🔥🔥 | Spring Security OAuth2 Client |
| 19 | [Internationalization (i18n)](#19-internationalization-i18n) | 🔥🔥🔥 | MessageSource, LocaleResolver |
| 20 | [Tính năng "Thông minh" với AI/ML](#20-tính-năng-thông-minh-với-aiml) | 🔥🔥🔥🔥🔥🔥🔥🔥🔥 | AI, ML, Recommendation System |

---

## 🔥 1. Search nâng cao + Elasticsearch

**Ý tưởng**: Thay vì search bằng SQL LIKE %...% (hiện tại), đưa dữ liệu lên Elasticsearch để search full-text cực nhanh, có phân tích từ khóa tiếng Việt.

### Kỹ thuật học được:
- Elasticsearch client trong Java
- Logstash đồng bộ MySQL → ES
- Cấu hình analyzer tiếng Việt (ICU)
- Highlight kết quả search

### Luồng:
```
User gõ "nhà gò vấp giá rẻ"
    ↓
REST API → Elasticsearch query
    ↓
ES phân tích: "nhà" (synonym: tòa nhà, building)
               "gò vấp" (district)
               "giá rẻ" (rentprice < 5tr)
    ↓
Trả về kết quả + highlight "Gò Vấp", "999,000đ"
```

### File cần tạo:
- `config/ElasticsearchConfig.java`
- `service/ElasticsearchBuildingService.java`
- `repository/BuildingSearchRepository.java`
- `dto/SearchSuggestionDTO.java`

### Câu hỏi phỏng vấn hay gặp:
> "Tại sao không dùng LIKE '%...%' mà phải dùng Elasticsearch?"
> "Phân tích full-text search tiếng Việt khó thế nào?"

---

## 🔥 2. Đa luồng xử lý báo cáo

**Ý tưởng**: Khi admin cần báo cáo tổng hợp (danh sách building, customer, transaction, doanh thu), mỗi phần được xử lý song song trên một thread riêng, sau đó gộp kết quả.

### Code mẫu:
```java
public DashboardDTO getDashboard() {
    CompletableFuture<List<BuildingDTO>> buildings = 
        CompletableFuture.supplyAsync(() -> buildingService.findAll());
    CompletableFuture<List<CustomerDTO>> customers = 
        CompletableFuture.supplyAsync(() -> customerService.findAll());
    CompletableFuture<Long> revenue = 
        CompletableFuture.supplyAsync(() => transactionService.getTotalRevenue());
    
    // Đợi tất cả hoàn thành
    CompletableFuture.allOf(buildings, customers, revenue).join();
    
    // Gộp kết quả
    return new DashboardDTO(buildings.get(), customers.get(), revenue.get());
}
```

### Kỹ thuật học được:
- `CompletableFuture` — async programming
- `ExecutorService` — quản lý thread pool
- `ForkJoinPool` — parallel processing
- Timeout handling, exception handling đa luồng

### Câu hỏi phỏng vấn:
> "CompletableFuture khác gì với Thread/Runnable?"
> "Xử lý exception trong đa luồng thế nào?"
> "Làm sao tránh deadlock?"

---

## 🔥 3. Caching Redis cho API

**Ý tưởng**: Cache kết quả search building và danh sách staff để khỏi query database liên tục. Khi có thay đổi thì invalidate cache.

### Pattern:
```java
@GetMapping("/api/building")
public List<BuildingDTO> search(BuildingDTO dto) {
    String cacheKey = "buildings:" + buildCacheKey(dto);
    
    // 1. Kiểm tra Redis cache trước
    List<BuildingDTO> cached = redisTemplate.opsForValue().get(cacheKey);
    if (cached != null) return cached;
    
    // 2. Cache miss → query database
    List<BuildingDTO> result = buildingService.findAll(dto);
    
    // 3. Lưu vào Redis với TTL 5 phút
    redisTemplate.opsForValue().set(cacheKey, result, 5, TimeUnit.MINUTES);
    
    return result;
}

// Khi update building → xóa cache
@PutMapping("/api/building/{id}")
public BuildingDTO update(@PathVariable Long id, @RequestBody BuildingDTO dto) {
    BuildingDTO result = buildingService.update(id, dto);
    redisTemplate.delete("buildings:*"); // Invalidate cache
    return result;
}
```

### Kỹ thuật học được:
- Redis client (Lettuce/Jedis)
- Spring Cache (`@Cacheable`, `@CacheEvict`, `@CachePut`)
- Cache Aside Pattern, Write-Through
- Cache TTL, Eviction Policy
- Cache stampede prevention

### Câu hỏi phỏng vấn:
> "Cache stampede là gì? Tránh thế nào?"
> "Khi nào nên cache, khi nào không?"
> "Redis khác gì với in-memory cache (Caffeine, Guava)?"

---

## 🔥 4. Rate Limiting + Chống Brute Force

**Ý tưởng**: Giới hạn số lần gọi API login — nếu sai quá 5 lần trong 1 phút thì khóa IP 15 phút. Dùng Bucket4j.

### Code mẫu:
```java
@Component
public class RateLimitingFilter extends OncePerRequestFilter {
    
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
            HttpServletResponse response, FilterChain chain) {
        
        String ip = request.getRemoteAddr();
        Bucket bucket = buckets.computeIfAbsent(ip, k -> createBucket());
        
        if (bucket.tryConsume(1)) {
            chain.doFilter(request, response); // Cho phép
        } else {
            response.setStatus(429); // Too Many Requests
            response.getWriter().write("Vượt quá giới hạn, thử lại sau 15 phút");
        }
    }
    
    private Bucket createBucket() {
        return Bucket.builder()
            .addLimit(limit -> limit.capacity(5).refillGreedy(5, Duration.ofMinutes(1)))
            .build();
    }
}
```

### Kỹ thuật học được:
- Token Bucket / Leaky Bucket algorithm
- Bucket4j library
- Distributed rate limiting với Redis
- HTTP 429 status code

### Câu hỏi phỏng vấn:
> "Làm sao rate limit hoạt động với nhiều server (cluster)?"
> "Token bucket vs Leaky bucket khác gì nhau?"
> "Tại sao cần rate limit?"

---

## 🔥 5. Import/Export Excel + PDF

**Ý tưởng**: Export danh sách building ra Excel/PDF + Import danh sách từ Excel.

### Code mẫu Export:
```java
@GetMapping("/api/building/export/excel")
public void exportExcel(HttpServletResponse response) {
    List<BuildingDTO> buildings = buildingService.findAll();
    
    // Tạo workbook
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet("Buildings");
    
    // Style header
    XSSFCellStyle headerStyle = workbook.createCellStyle();
    headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    
    // Tạo header
    String[] columns = {"ID", "Tên", "Quận", "Giá thuê", "Diện tích"};
    Row headerRow = sheet.createRow(0);
    for (int i = 0; i < columns.length; i++) {
        Cell cell = headerRow.createCell(i);
        cell.setCellValue(columns[i]);
        cell.setCellStyle(headerStyle);
    }
    
    // Tạo dữ liệu
    int rowNum = 1;
    for (BuildingDTO b : buildings) {
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(b.getId());
        row.createCell(1).setCellValue(b.getName());
        row.createCell(2).setCellValue(b.getDistrict());
        row.createCell(3).setCellValue(b.getRentPrice());
    }
    
    // Response
    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    response.setHeader("Content-Disposition", "attachment; filename=buildings.xlsx");
    workbook.write(response.getOutputStream());
}
```

### Kỹ thuật học được:
- Apache POI (Excel), iText/JasperReports (PDF)
- Streaming response, `OutputStream`
- Xử lý file lớn (multi-sheet, 100k+ records)
- Validation khi import (dòng nào lỗi, dòng nào OK)

### Câu hỏi phỏng vấn:
> "Xử lý file Excel 1 triệu dòng thế nào? Không bị OOM?"
> "Làm sao validate dữ liệu import và trả về lỗi chi tiết?"

---

## 🔥 6. Phân quyền chi tiết (Method Security)

**Ý tưởng**: Không chỉ phân quyền theo role (ADMIN/STAFF/USER) mà còn theo chức năng cụ thể, theo dữ liệu.

### Ví dụ:
```java
@RestController
@PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
public class BuildingAPI {
    
    @GetMapping("/api/building")
    @PreAuthorize("hasPermission(#buildingDTO, 'BUILDING_VIEW')")
    public List<BuildingDTO> search(BuildingDTO buildingDTO) { ... }
    
    @PostMapping("/api/building")
    @PreAuthorize("hasPermission(#buildingDTO, 'BUILDING_CREATE')")
    public BuildingDTO create(@RequestBody BuildingDTO dto) { ... }
    
    @PutMapping("/api/building/{id}")
    @PreAuthorize("hasPermission(#id, 'BUILDING', 'BUILDING_UPDATE')")
    public BuildingDTO update(@PathVariable Long id, @RequestBody BuildingDTO dto) { ... }
    
    // Chỉ ADMIN mới xóa được
    @DeleteMapping("/api/building/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) { ... }
}
```

### Custom PermissionEvaluator:
```java
@Component
public class BuildingPermissionEvaluator implements PermissionEvaluator {
    
    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
        MyUserDetail user = (MyUserDetail) auth.getPrincipal();
        
        // Staff chỉ xem được building được gán
        if (permission.equals("BUILDING_VIEW") && user.hasRole("STAFF")) {
            return assignmentBuildingRepository
                .existsByBuildingIdAndStaffId(
                    ((BuildingDTO)targetDomainObject).getId(), 
                    user.getId());
        }
        
        return true; // Admin thì OK
    }
}
```

### Kỹ thuật học được:
- `@PreAuthorize`, `@PostAuthorize`, `@Secured`
- Custom `PermissionEvaluator`
- `@PreFilter`, `@PostFilter` — filter danh sách tự động
- AOP interception

### Câu hỏi phỏng vấn:
> "Phân biệt @PreAuthorize và @Secured?"
> "Làm sao để nhân viên chỉ xem được building của mình?"

---

## 🔥 7. Refresh Token + Remember Me

**Ý tưởng**: Access token ngắn hạn (15 phút) + Refresh token dài hạn (7 ngày) + Blacklist khi logout.

### Luồng:
```
Login thành công
    ↓
Server trả về:
  - accessToken (15 phút) → gửi trong Authorization header
  - refreshToken (7 ngày) → gửi trong HttpOnly cookie

Khi accessToken hết hạn:
  Client gọi POST /api/auth/refresh với refreshToken
    ↓
  Server kiểm tra refreshToken còn hợp lệ không
    ↓
  Server cấp accessToken mới + refreshToken mới (nếu còn > 1 ngày)

Khi logout:
  POST /api/auth/logout
    ↓
  Server add refreshToken vào Redis blacklist
```

### Code mẫu:
```java
public class JwtTokenProvider {
    
    public TokenPair generateTokenPair(String username) {
        String accessToken = Jwts.builder()
            .subject(username)
            .issuedAt(now)
            .expiration(new Date(now.getTime() + 15 * 60 * 1000))   // 15 phút
            .signWith(accessKey)
            .compact();
            
        String refreshToken = Jwts.builder()
            .subject(username)
            .issuedAt(now)
            .expiration(new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000)) // 7 ngày
            .signWith(refreshKey)  // Key riêng
            .compact();
            
        return new TokenPair(accessToken, refreshToken);
    }
}

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
            HttpServletResponse response, FilterChain chain) {
        
        String accessToken = extractToken(request);
        
        // Kiểm tra blacklist
        if (redisTemplate.hasKey("blacklist:" + accessToken)) {
            response.setStatus(401);
            return;
        }
        
        // ... validate token như cũ
    }
}
```

### Kỹ thuật học được:
- JWT nâng cao (2 loại token riêng biệt)
- Token rotation (xoay vòng refresh token)
- HttpOnly Cookies vs Authorization header
- Blacklist với Redis
- Remember Me pattern (refresh token lưu trong DB)

### Câu hỏi phỏng vấn:
> "Tại sao phải có refresh token? Không thể access token dài hạn luôn?"
> "Token bị đánh cắp thì xử lý thế nào?"
> "Rotation refresh token để làm gì?"

---

## 🔥 8. WebSocket + Thông báo real-time

**Ý tưởng**: Khi admin gán building cho staff, staff nhận được thông báo real-time ngay lập tức. Hoặc khi có khách hàng mới, admin được notify.

### Kiến trúc:
```
Staff gán building cho staff khác
    ↓
BuildingAPI.assignBuilding()
    ↓
SimpMessagingTemplate.convertAndSendToUser(staffId, "/queue/notification", data)
    ↓
Staff nhận được thông báo pop-up real-time
```

### Code mẫu:
```java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
    }
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
            .setAllowedOrigins("*")
            .withSockJS();
    }
}

// Khi gán building → gửi thông báo
@Service
public class NotificationService {
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    public void notifyStaffAssigned(Long staffId, Long buildingId) {
        NotificationDTO notification = NotificationDTO.builder()
            .type("ASSIGN_BUILDING")
            .title("Bạn được giao quản lý tòa nhà mới")
            .content("Tòa nhà #" + buildingId)
            .timestamp(new Date())
            .build();
            
        messagingTemplate.convertAndSendToUser(
            staffId.toString(), 
            "/queue/notification", 
            notification);
    }
}
```

### Kỹ thuật học được:
- WebSocket + STOMP protocol
- SockJS fallback (khi WebSocket không khả dụng)
- User destination (gửi riêng cho từng user)
- SSE (Server-Sent Events) — alternative nhẹ hơn

### Câu hỏi phỏng vấn:
> "WebSocket khác gì với HTTP polling? Khi nào dùng cái nào?"
> "Phân biệt WebSocket vs SSE vs Polling?"
> "Làm sao scale WebSocket lên nhiều server?"

---

## 🔥 9. Batch Processing + Scheduled Tasks

**Ý tưởng**: 
- Mỗi đêm 12h: chạy job tổng hợp báo cáo doanh thu
- Mỗi sáng 8h: gửi email nhắc staff về building sắp hết hạn
- Mỗi giờ: đồng bộ dữ liệu sang Elasticsearch

### Code mẫu:
```java
@Component
public class ScheduledJobs {
    
    @Autowired
    private BuildingService buildingService;
    
    @Autowired
    private EmailService emailService;
    
    // Job 1: Tổng hợp báo cáo — 12h đêm hằng ngày
    @Scheduled(cron = "0 0 0 * * ?")
    public void generateDailyReport() {
        List<BuildingDTO> buildings = buildingService.findAll();
        ReportDTO report = calculateRevenue(buildings);
        reportService.save(report);
        emailService.sendReportToAdmin(report);
    }
    
    // Job 2: Nhắc nhở — 8h sáng mỗi ngày
    @Scheduled(cron = "0 0 8 * * ?")
    public void remindExpiringContracts() {
        List<BuildingDTO> expiringBuildings = 
            buildingService.findExpiringInDays(7);
        
        for (BuildingDTO b : expiringBuildings) {
            List<UserDTO> managers = 
                assignmentBuildingService.getStaffByBuilding(b.getId());
            for (UserDTO staff : managers) {
                emailService.sendReminder(staff.getEmail(), b.getName());
            }
        }
    }
    
    // Job 3: Đồng bộ Elasticsearch — mỗi 30 phút
    @Scheduled(fixedRate = 30 * 60 * 1000)
    public void syncToElasticsearch() {
        List<BuildingDTO> buildings = buildingService.findAll();
        elasticsearchService.bulkIndex(buildings);
    }
}
```

### Nâng cao: Spring Batch
```java
@Configuration
@EnableBatchProcessing
public class BatchConfig {
    
    @Bean
    public Job importBuildingJob(JobRepository jobRepository,
            Step importStep, JobCompletionNotificationListener listener) {
        return new JobBuilder("importBuildingJob", jobRepository)
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .start(importStep)
            .build();
    }
    
    @Bean
    public Step importStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<BuildingDTO> reader,
            ItemProcessor<BuildingDTO, BuildingEntity> processor,
            ItemWriter<BuildingEntity> writer) {
        
        return new StepBuilder("importStep", jobRepository)
            .<BuildingDTO, BuildingEntity>chunk(100, transactionManager)
            .reader(reader)     // Đọc từ file Excel
            .processor(processor) // Validate + Convert
            .writer(writer)       // Ghi vào DB
            .build();
    }
}
```

### Kỹ thuật học được:
- `@Scheduled` (cron, fixedRate, fixedDelay)
- Spring Batch (ItemReader, ItemProcessor, ItemWriter)
- Quartz Scheduler (lịch phức tạp, cluster)
- Chunk processing (xử lý 100 records/lần, tránh OOM)

### Câu hỏi phỏng vấn:
> "Scheduled task trong cluster chạy thế nào? Tránh chạy trùng?"
> "Spring Batch khác gì với tự viết for-loop?"
> "Cron expression "0 0 0 * * ?" có nghĩa gì?"

---

## 🔥 10. Dynamic Query with Specification

**Ý tưởng**: Thay vì native query ghép chuỗi SQL (đang bị SQL injection), dùng JPA Specifications — type-safe, clean, không lo SQL injection.

### Code mẫu:
```java
// Specification pattern
public class BuildingSpecification {
    
    public static Specification<BuildingEntity> hasName(String name) {
        return (root, query, cb) -> 
            cb.like(root.get("name"), "%" + name + "%");
    }
    
    public static Specification<BuildingEntity> hasDistrict(String district) {
        return (root, query, cb) -> 
            cb.equal(root.get("district"), district);
    }
    
    public static Specification<BuildingEntity> hasRentPriceBetween(Integer from, Integer to) {
        return (root, query, cb) -> {
            if (from == null && to == null) return null;
            if (from == null) return cb.lessThanOrEqualTo(root.get("rentPrice"), to);
            if (to == null) return cb.greaterThanOrEqualTo(root.get("rentPrice"), from);
            return cb.between(root.get("rentPrice"), from, to);
        };
    }
    
    public static Specification<BuildingEntity> hasRentAreaBetween(Integer from, Integer to) {
        return (root, query, cb) -> {
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<RentAreaEntity> subRoot = subquery.from(RentAreaEntity.class);
            subquery.select(subRoot.get("value"))
                    .where(cb.equal(subRoot.get("buildingEntity"), root));
            
            if (from != null && to != null) {
                return cb.between(subquery, from, to);
            }
            return cb.conjunction(); // EXISTS
        };
    }
}
```

### Service dùng Specification:
```java
@Service
public class BuildingServiceImpl {
    
    @Autowired
    private BuildingRepository buildingRepository;
    
    public List<BuildingDTO> search(BuildingDTO model) {
        Specification<BuildingEntity> spec = Specification.where(null);
        
        if (model.getName() != null) 
            spec = spec.and(BuildingSpecification.hasName(model.getName()));
        if (model.getDistrict() != null)
            spec = spec.and(BuildingSpecification.hasDistrict(model.getDistrict()));
        if (model.getRentPriceFrom() != null || model.getRentPriceTo() != null)
            spec = spec.and(BuildingSpecification.hasRentPriceBetween(
                model.getRentPriceFrom(), model.getRentPriceTo()));
        
        // Phân trang + Sort
        Pageable pageable = PageRequest.of(
            model.getPage() - 1, 
            model.getMaxPageItems(),
            Sort.by("rentPrice").descending());
        
        Page<BuildingEntity> page = buildingRepository.findAll(spec, pageable);
        
        // Trả về kết quả + thông tin phân trang
        return new PageDTO<>(page.getContent(), page.getTotalPages(), page.getTotalElements());
    }
}
```

### Kỹ thuật học được:
- JPA Metamodel (type-safe queries)
- `Specification<T>` + `CriteriaBuilder` + `CriteriaQuery`
- `Pageable` + `Page<T>` — phân trang chuẩn
- Subquery với Criteria API
- **Không còn SQL injection**

### Câu hỏi phỏng vấn:
> "Specification vs Native Query — khi nào dùng cái nào?"
> "Lợi ích của việc dùng Criteria API?"
> "Phân trang trong JPA hoạt động thế nào?"

---

## 🔥🔥🔥 11. Multi-tenant (Phân tách dữ liệu khách hàng)

**Ý tưởng**: Một ứng dụng chạy nhưng phục vụ nhiều công ty môi giới khác nhau. Mỗi công ty chỉ thấy dữ liệu của mình. Có thể dùng:
- **Schema per tenant**: mỗi công ty 1 schema (database) riêng
- **Discriminator column**: 1 database, thêm cột `tenant_id`

### Kiến trúc cao nhất (Schema per tenant):
```
App chạy 1 instance nhưng phục vụ nhiều công ty
       ↓
  Interceptor đọc tenant từ header (X-Tenant-ID)
       ↓
  Dynamic DataSource Routing → chọn đúng schema
       ↓
  Schema A (Công ty A: building, users...)
  Schema B (Công ty B: building, users...)
  Schema C (Công ty C: building, users...)
```

### Code mẫu:
```java
// 1. Lưu tenant hiện tại vào ThreadLocal
@Component
public class TenantContext {
    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();
    
    public static void setTenant(String tenant) { CURRENT_TENANT.set(tenant); }
    public static String getTenant() { return CURRENT_TENANT.get(); }
    public static void clear() { CURRENT_TENANT.remove(); }
}

// 2. Filter đọc tenant từ header
@Component
public class TenantFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
            HttpServletResponse response, FilterChain chain) {
        String tenant = request.getHeader("X-Tenant-ID");
        TenantContext.setTenant(tenant != null ? tenant : "default");
        chain.doFilter(request, response);
        TenantContext.clear();
    }
}

// 3. Dynamic DataSource chọn schema theo tenant
@Component
public class TenantRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContext.getTenant();
    }
}
```

### Kỹ thuật học được:
- `ThreadLocal` — cách lưu dữ liệu theo từng request
- `AbstractRoutingDataSource` — dynamic datasource
- Cấu trúc multi-tenant hoàn chỉnh
- Phân tách dữ liệu vật lý
- **Tính năng này đưa lên CV là ghi "Senior" luôn**

### Câu hỏi phỏng vấn:
> "Multi-tenant là gì? Khi nào cần dùng?"
> "Schema per tenant vs Discriminator column — ưu nhược điểm?"
> "ThreadLocal là gì? Rủi ro khi dùng?"

---

## 🔥🔥🔥🔥 12. CQRS Pattern (Tách đọc/ghi)

**Ý tưởng**: Tách biệt hoàn toàn việc Đọc (Query) và Ghi (Command). Mỗi bên có model, database riêng. Dùng message queue (RabbitMQ) để đồng bộ.

### Kiến trúc:
```
                      ┌──────────────────┐
                      │   Command Side    │ (Write DB - MySQL)
                      │  POST/PUT/DELETE  │
                      └────────┬─────────┘
                               │ Event (BuildingCreated, Assigned...)
                               ↓
                        ┌──────────────┐
                        │   RabbitMQ    │
                        └──────────────┘
                               │
                               ↓
                      ┌──────────────────┐
                      │   Query Side      │ (Read DB - Elasticsearch)
                      │  GET/Search       │
                      └──────────────────┘
```

### Code mẫu:
```java
// COMMAND SIDE — Ghi
@Service
public class BuildingCommandService {
    
    @Autowired
    private BuildingRepository buildingRepository;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Transactional
    public void createBuilding(BuildingDTO dto) {
        // 1. Validate business rules
        if (buildingRepository.findByName(dto.getName()).isPresent()) {
            throw new DuplicateBuildingException(dto.getName());
        }
        
        // 2. Save to Command DB (MySQL)
        BuildingEntity entity = converter.convertToEntity(dto);
        BuildingEntity saved = buildingRepository.save(entity);
        
        // 3. Publish event to RabbitMQ
        BuildingCreatedEvent event = new BuildingCreatedEvent(
            saved.getId(), saved.getName(), saved.getDistrict()
        );
        rabbitTemplate.convertAndSend("building.exchange", "building.created", event);
    }
}

// QUERY SIDE — Đọc (có thể ở module/service khác)
@Service
public class BuildingQueryService {
    
    @Autowired
    private BuildingReadRepository readRepository; // Elasticsearch
    
    @RabbitListener(queues = "building.query.queue")
    public void handleBuildingCreated(BuildingCreatedEvent event) {
        // Cập nhật Read DB (Elasticsearch/Redis)
        readRepository.save(new BuildingReadModel(
            event.getId(), event.getName(), event.getDistrict()
        ));
    }
    
    public List<BuildingDTO> search(String keyword) {
        // Search trên Read DB, không đụng vào Write DB
        return readRepository.search(keyword);
    }
}
```

### Kỹ thuật học được:
- CQRS pattern (Command Query Responsibility Segregation)
- Event Sourcing (lưu trạng thái qua các sự kiện)
- Message Queue (RabbitMQ)
- Eventual Consistency
- **Đây là pattern của Uber, Netflix, Amazon**

### Câu hỏi phỏng vấn:
> "CQRS giải quyết vấn đề gì?"
> "Eventual Consistency là gì? Làm sao đảm bảo?"
> "Khi nào KHÔNG nên dùng CQRS?"

---

## 🔥🔥🔥🔥 13. Microservice Split

**Ý tưởng**: Tách monolithic app thành các microservice riêng biệt.

### Kiến trúc:
```
                     ┌──────────┐
                     │  Gateway  │ (Spring Cloud Gateway)
                     └─────┬────┘
               ┌───────────┼───────────┐
               ↓           ↓           ↓
         ┌──────────┐ ┌──────────┐ ┌──────────┐
         │ Building │ │  User   │ │Customer  │
         │ Service  │ │ Service │ │ Service  │
         └──────────┘ └──────────┘ └──────────┘
               │           │           │
               ↓           ↓           ↓
         ┌──────────────────────────────────┐
         │           Eureka Server           │ (Service Discovery)
         └──────────────────────────────────┘
```

### File cần tạo cho mỗi service:
```
building-service/
  ├── pom.xml (spring-boot + eureka-client)
  ├── BuildingApplication.java
  ├── controller/BuildingController.java
  ├── service/BuildingService.java
  └── repository/BuildingRepository.java

user-service/
  ├── (giống cấu trúc)
  └── ...

api-gateway/
  ├── pom.xml (spring-cloud-gateway + eureka-client)
  └── GatewayApplication.java

service-registry/
  ├── pom.xml (eureka-server)
  └── EurekaApplication.java
```

### Kỹ thuật học được:
- **Spring Cloud**: Eureka, Gateway, Config Server
- **Service Discovery** (các service tự động tìm nhau)
- **API Gateway** (1 endpoint duy nhất cho client)
- **Load Balancing** (Ribbon / Spring Cloud LoadBalancer)
- **Circuit Breaker** (Resilience4j) — khi 1 service chết, không kéo chết toàn bộ
- **Distributed Tracing** (Zipkin/Sleuth)
- **Containerization** (Docker + Docker Compose)
- **Inter-service Communication** (gọi service A từ service B)

### Câu hỏi phỏng vấn:
> "Microservice khác gì Monolithic?"
> "Làm sao gọi API giữa các service với nhau?"
> "Circuit Breaker dùng để làm gì?"
> "Vấn đề của microservice là gì? (distributed transaction, network latency...)"

---

## 🔥🔥🔥🔥🔥 14. Unit Test + Integration Test + TDD

**Ý tưởng**: Viết test cho mọi thứ!

### Unit Test (test từng class riêng lẻ):
```java
@ExtendWith(MockitoExtension.class)
class BuildingServiceImplTest {
    
    @Mock
    private BuildingRepository buildingRepository;
    
    @Mock
    private BuildingConverter buildingConverter;
    
    @InjectMocks
    private BuildingServiceImpl buildingService;
    
    @Test
    void testFindByName_WhenNameExists_ShouldReturnBuilding() {
        // Given
        String name = "Building A";
        BuildingEntity entity = new BuildingEntity();
        entity.setName(name);
        
        when(buildingRepository.findByName(name))
            .thenReturn(Optional.of(entity));
        
        // When
        assertThrows(Exception.class, () -> {
            BuildingDTO dto = new BuildingDTO();
            dto.setName(name);
            buildingService.save(dto);
        });
        
        // Then - throw Exception vì name đã tồn tại
        verify(buildingRepository, never()).save(any());
    }
}
```

### Integration Test (test với DB thật):
```java
@SpringBootTest
@Testcontainers
class BuildingAPIIntegrationTest {
    
    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("test");
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void testCreateBuilding_ShouldReturn201() {
        // Given
        BuildingDTO dto = new BuildingDTO();
        dto.setName("Test Building");
        dto.setDistrict("QUAN_1");
        dto.setRentPrice(1000);
        
        // When
        ResponseEntity<BuildingDTO> response = restTemplate.postForEntity(
            "/api/building", dto, BuildingDTO.class);
        
        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getName()).isEqualTo("Test Building");
    }
    
    @Test
    void testSearchBuilding_ShouldFilterByDistrict() {
        // When
        ResponseEntity<List> response = restTemplate.exchange(
            "/api/building?district=QUAN_1", 
            HttpMethod.GET, null, List.class);
        
        // Then
        assertThat(response.getBody()).hasSize(2); // Chỉ có 2 building ở QUAN_1
    }
}
```

### Kỹ thuật học được:
- **JUnit 5**: `@Test`, `@ParameterizedTest`, `@Nested`
- **Mockito**: `@Mock`, `@InjectMocks`, `verify()`, `when()`
- **Testcontainers**: MySQL container trong test
- **TDD**: Test trước, code sau
- **Code Coverage**: JaCoCo (mục tiêu 80%+)

### Câu hỏi phỏng vấn:
> "Unit test vs Integration test khác nhau thế nào?"
> "Làm sao test controller mà không cần start cả ứng dụng?"
> "TDD là gì? Lợi ích?"
> "Code coverage 100% có tốt không?"

---

## 🔥🔥🔥🔥 15. Hóa đơn điện tử (PDF + QR Code)

**Ý tưởng**: Khi khách thuê building → tạo hóa đơn PDF có QR Code để thanh toán.

### Code mẫu:
```java
@Service
public class InvoiceService {
    
    public ByteArrayOutputStream generateInvoice(InvoiceDTO invoice) {
        // 1. Tạo QR Code chứa thông tin thanh toán
        String paymentInfo = String.format(
            "Số tài khoản: 0123456789\n" +
            "Ngân hàng: Vietcombank\n" +
            "Số tiền: %d\n" +
            "Nội dung: HD%s", 
            invoice.getAmount(), invoice.getCode());
        
        QRCodeWriter qrWriter = new QRCodeWriter();
        BitMatrix matrix = qrWriter.encode(paymentInfo, QR_CODE, 200, 200);
        
        // 2. Tạo PDF với iText
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        
        // Header
        document.add(new Paragraph("HÓA ĐƠN THUÊ TÒA NHÀ")
            .setFontSize(20).setBold().setTextAlignment(CENTER));
        
        // Thông tin
        document.add(new Paragraph(
            "Mã hóa đơn: " + invoice.getCode()));
        document.add(new Paragraph(
            "Tòa nhà: " + invoice.getBuildingName()));
        document.add(new Paragraph(
            "Số tiền: " + formatCurrency(invoice.getAmount())));
        document.add(new Paragraph(
            "Ngày: " + invoice.getCreatedDate()));
        
        // QR Code
        Image qrImage = new Image(ImageDataFactory.create(toImage(matrix)));
        qrImage.setWidth(100).setHeight(100);
        document.add(qrImage);
        
        document.close();
        return outputStream;
    }
}
```

### Kỹ thuật học được:
- iText 7 (tạo PDF chuyên nghiệp)
- ZXing (tạo QR Code)
- Template engine (Thymeleaf + Flying Saucer → PDF từ HTML)
- Signed PDF (chống giả mạo)

---

## 🔥🔥🔥🔥🔥 16. Chat Real-time Staff ↔ Admin

**Ý tưởng**: Staff chat trực tiếp với admin về building được giao.

### Kiến trúc:
```
Staff gửi tin nhắn về building #123
    ↓
WebSocket → /app/chat/123
    ↓
Server lưu vào MongoDB
    ↓
Server push đến tất cả người trong phòng chat #123
    ↓
Admin nhận được real-time, trả lời
```

### Code mẫu:
```java
@Controller
public class ChatController {
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    private ChatMessageRepository chatRepository; // MongoDB
    
    @MessageMapping("/chat/{buildingId}")
    @SendTo("/topic/chat/{buildingId}")
    public ChatMessage handleChat(
            @DestinationVariable Long buildingId,
            @Payload ChatMessage message) {
        
        // Lưu vào MongoDB
        message.setBuildingId(buildingId);
        message.setTimestamp(new Date());
        chatRepository.save(message);
        
        // Broadcast đến mọi người trong phòng
        messagingTemplate.convertAndSend(
            "/topic/chat/" + buildingId, message);
        
        return message;
    }
}
```

### Kỹ thuật học được:
- WebSocket chat real-time
- MongoDB (lưu tin nhắn, schema-less)
- Message history (lấy lịch sử chat)
- Online/Offline status
- Typing indicator ("đang nhập...")

---

## 🔥🔥🔥🔥🔥 17. CI/CD Pipeline

**Ý tưởng**: Tự động build, test, deploy khi push code lên GitHub.

### File `.github/workflows/deploy.yml`:
```yaml
name: Build, Test & Deploy

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    
    services:
      mysql:
        image: mysql:8.0
        env:
          MYSQL_ROOT_PASSWORD: test
          MYSQL_DATABASE: realestateagent
        
    steps:
      - uses: actions/checkout@v4
      
      - name: Set up JDK 25
        uses: actions/setup-java@v4
        with:
          java-version: '25'
          distribution: 'temurin'
      
      - name: Build with Maven
        run: mvn clean package
      
      - name: Run tests
        run: mvn test
      
      - name: Build Docker image
        run: docker build -t realestate-api .
      
      - name: Deploy to Server
        uses: appleboy/ssh-action@v1.0.3
        with:
          host: ${{ secrets.SERVER_HOST }}
          username: ${{ secrets.SERVER_USER }}
          key: ${{ secrets.SSH_KEY }}
          script: |
            docker pull realestate-api
            docker stop realestate || true
            docker run -d --name realestate -p 8080:8080 realestate-api
```

### File `Dockerfile`:
```dockerfile
FROM eclipse-temurin:25-jdk
WORKDIR /app
COPY target/spring-boot-1.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Kỹ thuật học được:
- Docker container hóa ứng dụng
- GitHub Actions / Jenkins
- CI: Build + Test tự động
- CD: Deploy tự động lên server
- Infrastructure as Code

### Câu hỏi phỏng vấn:
> "CI/CD là gì? Lợi ích?"
> "Phân biệt Continuous Integration vs Continuous Delivery vs Continuous Deployment?"
> "Làm sao rollback nếu bản deploy mới bị lỗi?"

---

## 🔥🔥🔥🔥 18. OAuth2 Login (Google/Facebook)

**Ý tưởng**: Cho phép staff đăng nhập bằng tài khoản Google hoặc Facebook.

### Code mẫu:
```yaml
# application.yml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: YOUR_GOOGLE_CLIENT_ID
            client-secret: YOUR_GOOGLE_CLIENT_SECRET
            scope: profile, email
          facebook:
            client-id: YOUR_FACEBOOK_CLIENT_ID
            client-secret: YOUR_FACEBOOK_CLIENT_SECRET
            scope: email, public_profile
```

```java
@Configuration
public class OAuth2LoginConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/api/auth/login")
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(customOAuth2UserService))
                .successHandler((request, response, authentication) -> {
                    // Login thành công → tạo JWT token
                    CustomOAuth2User oauthUser = 
                        (CustomOAuth2User) authentication.getPrincipal();
                    String token = jwtTokenProvider.generateToken(
                        oauthUser.getEmail());
                    response.sendRedirect("/api/auth/callback?token=" + token);
                })
            );
        return http.build();
    }
}
```

### Kỹ thuật học được:
- OAuth2 Authorization Code Flow
- Spring Security OAuth2 Client
- Google/Facebook Developer Console
- Liên kết tài khoản OAuth với user trong DB

---

## 🔥🔥 19. Internationalization (i18n)

**Ý tưởng**: API trả về message bằng nhiều ngôn ngữ (VN, EN, KR...) dựa vào header `Accept-Language`.

### Code mẫu:
```java
@Configuration
public class I18nConfig implements WebMvcConfigurer {
    
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("messages");
        source.setDefaultEncoding("UTF-8");
        return source;
    }
    
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(new Locale("vi"));
        return resolver;
    }
}

// messages_vi.properties
building.notfound=Tòa nhà không tồn tại
building.duplicate=Tên tòa nhà đã tồn tại
auth.login.success=Đăng nhập thành công

// messages_en.properties
building.notfound=Building not found
building.duplicate=Building name already exists
auth.login.success=Login successfully

// Sử dụng trong code:
@Service
public class BuildingService {
    
    @Autowired
    private MessageSource messageSource;
    
    public BuildingDTO findById(Long id) {
        return buildingRepository.findById(id)
            .map(converter::convertToDTO)
            .orElseThrow(() -> new ResourceNotFoundException(
                messageSource.getMessage(
                    "building.notfound", 
                    null, 
                    LocaleContextHolder.getLocale())));
    }
}
```

### Kỹ thuật học được:
- `MessageSource` + properties files
- `LocaleResolver` + `LocaleContextHolder`
- Tách message ra khỏi code

---

## 🔥🔥🔥🔥🔥🔥🔥🔥🔥 20. AI/ML — Tính năng "Thông minh"

> **Lưu ý**: Đây là tính năng KHỦNG nhất, dành cho các công ty product, fintech, AI.

### a) Gợi ý giá thuê thông minh
```java
@RestController
public class PricePredictionAPI {
    
    @GetMapping("/api/building/{id}/suggest-price")
    public PriceSuggestion suggestPrice(@PathVariable Long id) {
        BuildingDTO building = buildingService.findById(id);
        
        // Dùng thuật toán: 
        // Giá = avg(giá các building cùng quận + cùng diện tích) * các hệ số
        // + location_score (gần chợ, trường học, bệnh viện)
        // + infrastructure_score (thang máy, hầm xe...)
        
        double suggestedPrice = aiService.predictPrice(building);
        
        return new PriceSuggestion(suggestedPrice, "Dựa trên 50 building tương tự");
    }
}
```

### b) Chatbot hỗ trợ khách hàng (AI)
```java
@RestController
public class ChatbotAPI {
    
    @PostMapping("/api/chatbot/query")
    public ChatResponse handleQuery(@RequestBody String question) {
        // User hỏi: "Có building nào ở quận 1 giá dưới 10tr không?"
        // AI parse:
        //   - intent: SEARCH_BUILDING
        //   - district: QUAN_1
        //   - max_price: 10_000_000
        
        Intent intent = aiService.parseIntent(question);
        List<BuildingDTO> results = buildingService.search(
            BuildingDTO.builder()
                .district(intent.getDistrict())
                .rentPriceTo(intent.getMaxPrice())
                .build());
        
        return ChatResponse.builder()
            .message(String.format("Có %d building phù hợp!", results.size()))
            .data(results)
            .build();
    }
}
```

### c) Xu hướng thị trường
```java
@RestController
public class MarketTrendAPI {
    
    // Phân tích xu hướng giá theo thời gian
    @GetMapping("/api/analytics/price-trend")
    public TrendReport getPriceTrend(
            @RequestParam String district, 
            @RequestParam String fromDate,
            @RequestParam String toDate) {
        
        // Output: biểu đồ giá theo tháng
        // - "Giá thuê QUAN_1 tăng 15% trong 6 tháng qua"
        // - "Tháng 6 là tháng cao điểm thuê nhà"
        // - "Building diện tích 100-200m2 được thuê nhiều nhất"
        
        return analyticsService.analyzeTrend(district, fromDate, toDate);
    }
}
```

### d) Phát hiện bất thường (Anomaly Detection)
```java
// Phát hiện building có dấu hiệu bất thường:
// - Giá thuê quá rẻ so với mặt bằng chung (có thể lừa đảo)
// - Khách hàng đặt cọc rồi hủy nhiều lần
// - Staff không hoàn thành giao dịch nào trong 3 tháng

@Scheduled(cron = "0 0 6 * * ?") // Mỗi sáng 6h
public void detectAnomalies() {
    List<BuildingDTO> suspiciousBuildings = analyticsService
        .findAnomalies();
    
    for (BuildingDTO b : suspiciousBuildings) {
        String alert = String.format(
            "[CẢNH BÁO] Building %s có giá %.2f — thấp hơn %.0f%% so với mặt bằng",
            b.getName(), b.getRentPrice(), 
            b.getAnomalyScore());
        
        notificationService.notifyAdmin(alert);
    }
}
```

### Kỹ thuật học được:
- Xử lý ngôn ngữ tự nhiên (NLP) cơ bản
- Thuật toán gợi ý (Collaborative Filtering)
- Phân tích chuỗi thời gian (Time Series)
- Phát hiện bất thường (Anomaly Detection)
- **Cực kỳ ấn tượng khi đi phỏng vấn**

---

## 🎯 Tổng kết: Lộ trình học

```
Dễ ───────────────────────────────────────────────────────────────→ Khó

Rate Limiting (#4)       →   Import/Export (#5)    
Caching Redis (#3)       →   WebSocket (#8)    
i18n (#19)               →   Batch Processing (#9)    
                          →   Phân trang Specification (#10)    

                              OAuth2 (#18)        
                              Elasticsearch (#1)      
                              Multi-thread (#2)      
                              Refresh Token (#7)      

                                                       ┌─────────────────────┐
                                                       │ Method Security (#6) │
                                                       │ Multi-tenant (#11)   │
                                                       │ CQRS Pattern (#12)   │
                                                       │ Microservice (#13)   │
                                                       │ CI/CD (#17)          │
                                                       │ Unit Test (#14)      │
                                                       │ AI/ML (#20)          │
                                                       └─────────────────────┘
                                                       Senior Level
```

---

## 📌 Lời khuyên phỏng vấn

1. **Chọn 2-3 tính năng khác nhau** để làm:
   - 1 tính năng bảo mật (JWT, phân quyền)
   - 1 tính năng performance (Caching, đa luồng)
   - 1 tính năng kiến trúc (Microservice, CQRS)

2. **Kể theo STAR** khi phỏng vấn:
   - **S**ituation: Project quản lý building, 5 loại user
   - **T**ask: Cần gán nhân viên quản lý building, mỗi staff chỉ thấy building của mình
   - **A**ction: Thiết kế bảng assignmentbuilding + JWT filter + permission evaluator
   - **R**esult: Staff chỉ thấy building được gán, giảm 70% nhầm lẫn

3. **Nói được "tại sao"** quan trọng hơn "làm được gì":
   - ❌ "Em có dùng Redis cache" → basic
   - ✅ "Em dùng Redis cache vì building ít thay đổi, request search nhiều. Cache giảm 90% query database. Em dùng Cache Aside Pattern, TTL 5 phút, tránh cache stampede bằng locking" → **đậu**

> **"Code là chuyện nhỏ, tư duy kiến trúc mới là thứ phân biệt Junior và Senior"**
🚀 Tính năng "khủng" hơn nữa ngoài list cũ
1. 🔥 GraphQL thay vì REST

query {
  building(id: 1) {
    name
    district
    staffs { fullName }
    rentAreas { value }
    transactions { code note }
  }
}
Client tự chọn field muốn lấy, ko bị over-fetching
Dùng Spring GraphQL (chính thức từ Spring)
PV hỏi: "REST vs GraphQL? Khi nào chọn cái nào?"
2. 🔥 Apache Kafka — Real-time Event Streaming
Thay vì RabbitMQ ở CQRS, dùng Kafka:


BuildingCreated → Kafka topic "building-events"
     ↓
  - Email service gửi mail
  - Notification service push WebSocket
  - Analytics service cập nhật thống kê
  - Search service đồng bộ Elasticsearch
  - Audit service ghi log
Tất cả xảy ra cùng lúc, không block API chính
PV hỏi: "Kafka vs RabbitMQ? Partition? Consumer Group?"
3. 🔥 Saga Pattern — Giao dịch phân tán
Khi tạo hợp đồng thuê building, cần:


1. Trừ tiền trong ví khách hàng
2. Cập nhật trạng thái building → "Đã thuê"
3. Tạo hóa đơn
4. Gửi email xác nhận

Nếu bước 2 fail → phải rollback bước 1 (hoàn tiền)
Dùng ** choreography saga** (mỗi service tự emit event):


Wallet Service          Building Service        Invoice Service
   ↓                         ↓                       ↓
Xóa tiền khách  →  Cập nhật building  →  Tạo hóa đơn
   ↑                         ↑                       ↑
Hoàn tiền nếu lỗi ←  Báo lỗi nếu lỗi ←  Báo lỗi nếu lỗi
PV hỏi: "Distributed transaction làm sao? 2PC vs Saga?"
4. 🔥 CDC (Change Data Capture) — Đồng bộ dữ liệu real-time
Dùng Debezium + Kafka:


MySQL binlog → Debezium → Kafka → Elasticsearch/Redis
Mỗi khi có INSERT/UPDATE/DELETE trong MySQL → tự động cập nhật Elasticsearch

Zero code đồng bộ, chỉ config
PV hỏi: "CDC là gì? Sao ko tự viết job đồng bộ?"
5. 🔥 Rule Engine — Drools
Thay vì code cứng logic nghiệp vụ, viết rule riêng:


rule "Ưu tiên building gần trường học"
when
  $b: Building(district == "QUAN_THU_DUC", rentPrice < 5000000)
  $s: Staff(role == "STAFF")
then
  insert(new PriorityAssignment($s, $b, 5));
end

rule "Tự động tăng giá thuê"
when
  $b: Building(rentPrice < avgRentPriceOfDistrict)
then
  modify($b) { setRentPrice($b.rentPrice * 1.1) }
end
Nghiệp vụ thay đổi → chỉ sửa file rule, ko cần code lại
PV hỏi: "Tại sao cần Rule Engine? Khi nào ko nên dùng?"
6. 🔥 Tự build mini framework: @QueryBuilder
Tạo annotation tự chế:


@QueryBuilder(
  select = "b",
  from = "building b",
  join = "LEFT JOIN rentarea ra ON ra.buildingid = b.id",
  where = {
    @Condition(field = "b.name", operator = LIKE),
    @Condition(field = "b.district", operator = EQUAL),
    @Condition(field = "b.rentprice", operator = BETWEEN),
    @Condition(field = "ra.value", operator = SUBQUERY)
  },
  groupBy = "b.id",
  orderBy = "b.rentprice DESC",
  pagination = true
)
public List<BuildingEntity> search(BuildingDTO dto) {
    // Annotation tự generate câu SQL
    // Ko cần viết 1 dòng SQL nào
}
Dùng AOP + Annotation Processing tự build
PV hỏi: "Annotation trong Java hoạt động thế nào? Runtime vs Compile time?"
7. 🔥 Distributed ID Generator (Snowflake)
Hiện tại dùng auto-increment ID. Nhưng microservice cần ID không trùng trên toàn hệ thống:


public class SnowflakeIdGenerator {
    private final long datacenterId;   // 5 bit
    private final long machineId;      // 5 bit
    private long sequence = 0;         // 12 bit
    private long lastTimestamp = -1;
    
    /*
     * ID 64 bit:
     * | 1 bit sign | 41 bit timestamp | 10 bit machine | 12 bit sequence |
     * 
     * Ví dụ: 1798237468234678274
     *         → sinh ra lúc 14:30:22 02/06/2026
     *         → từ datacenter 1, machine 3
     *         → sequence number 42
     */
    
    public synchronized long nextId() {
        // ... implementation
    }
}
ID tăng dần, phân tán, ko trùng
PV hỏi: "Tại sao không dùng UUID? Snowflake vs UUID vs auto-increment?"
8. 🔥 Database Sharding (Chia nhỏ DB)
Horizontal sharding: building có ID chẵn ở DB1, lẻ ở DB2
Shard key: theo quận (QUAN_1 → shard 0, QUAN_2 → shard 1...)
Dùng ShardingSphere hoặc tự code routing
PV hỏi: "Sharding vs Partition? Cách chọn shard key?"
9. 🔥 ETL Pipeline + Data Warehouse
Dựng data warehouse riêng cho báo cáo:


Mỗi tối 12h:
  - Lấy dữ liệu từ MySQL (OLTP)
  - Transform: tính doanh thu, số building đã thuê, top staff
  - Load vào PostgreSQL (OLAP) — tối ưu cho đọc

Sáng hôm sau:
  - Admin xem báo cáo → query trên PostgreSQL
  - Không ảnh hưởng đến database chính
PV hỏi: "OLTP vs OLAP? ETL là gì?"
10. 🔥 Chaos Engineering — Tự phá hệ thống
Viết script tự động:


- Tắt database → app vẫn chạy nhờ cache?
- Kill 1 instance → request có vào instance khác ko?
- Tăng latency lên 5s → circuit breaker có hoạt động?
- Gửi request lỗi liên tục → rate limiter có chặn?
Dùng Chaos Monkey (Netflix) hoặc Litmus:


@ChaosExperiment(attack = "LATENCY", target = "user-service", delayMs = 5000)
public class UserChaosTest {
    // Mỗi lần test xong đều có report
}
PV hỏi: "Chaos Engineering là gì? Tại sao Netflix tạo ra Chaos Monkey?"
11. 🔥 Feature Flag / A/B Testing
Triển khai tính năng mới nhưng chỉ bật cho 10% user:


if (featureFlag.isEnabled("new-search-algorithm", userId)) {
    return newSearchService.search(dto);  // Thuật toán mới
} else {
    return oldSearchService.search(dto);  // Thuật toán cũ
}
Toggle: bật/tắt ko cần deploy lại
Canary release: bật cho 10% → 50% → 100%
Dùng LaunchDarkly hoặc tự build với Redis
PV hỏi: "Feature flag để làm gì? Canary release là gì?"
12. 🔥 Text-to-SQL — Gõ tiếng Việt ra SQL

// Staff gõ: "cho tôi xem mấy cái building ở quận 1 giá dưới 10 triệu"
//     ↓
// AI parse → SQL → kết quả
//     ↓
// SELECT * FROM building WHERE district = 'QUAN_1' AND rentprice < 10000000

@PostMapping("/api/ai/query")
public List<BuildingDTO> naturalLanguageQuery(@RequestBody String question) {
    String sql = aiService.textToSQL(question); // Gọi LLM/GPT
    return entityManager.createNativeQuery(sql, BuildingEntity.class)
                        .getResultList();
}
Dùng LLM (GPT-4o, Claude) API để convert text → SQL
PV hỏi: "Rủi ro bảo mật khi cho người dùng gõ tiếng Việt ra SQL?"
💾 Fake data khủng để học
Để học được nhiều, bạn cần data to, thật, đa dạng. Dưới đây là cách tạo:

Bảng User (1 triệu user)

-- Dùng store procedure sinh 1 triệu user
DELIMITER $$
CREATE PROCEDURE generate_users()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE first_names VARCHAR(200) = 'Nguyễn,Trần,Lê,Phạm,Hoàng,Huỳnh,Phan,Vũ,Võ,Đặng,Bùi,Đỗ,Hồ,Ngô,Đinh,Dương';
    DECLARE last_names VARCHAR(500) = 'Văn An,Thị Bích,Minh Hoàng,Quốc Tuấn,Đức Huy,Thanh Hằng,Mạnh Cường,Thị Hoa,Công Minh,Hoàng Long';
    
    WHILE i <= 1000000 DO
        INSERT INTO users(username, fullname, password, status)
        VALUES (
            CONCAT('user', i),
            CONCAT(
                ELT(1 + FLOOR(RAND() * 15), split_str(first_names, ',')),
                ' ',
                ELT(1 + FLOOR(RAND() * 10), split_str(last_names, ','))
            ),
            '$2a$10$/RUbuT9KIqk6f8enaTQiLOXzhnUkiwEJRdtzdrMXXwU7dgnLKTCYG',
            1
        );
        SET i = i + 1;
    END WHILE;
END$$
Bảng Building (100k building)

// Java code sinh fake data
public class FakeDataGenerator {
    
    private static final String[] DISTRICTS = {"QUAN_1","QUAN_2","QUAN_3","QUAN_4","QUAN_5","QUAN_6","QUAN_7","QUAN_8","QUAN_9","QUAN_10","QUAN_11","QUAN_12","QUAN_BINH_THANH","QUAN_GO_VAP","QUAN_THU_DUC","QUAN_BINH_CHANH"};
    
    private static final String[] STREETS = {"Nguyễn Huệ","Lê Lợi","Trần Hưng Đạo","Phạm Ngũ Lão","Hai Bà Trưng","Lý Thường Kiệt","Nguyễn Trãi","Cách Mạng Tháng 8","Xô Viết Nghệ Tĩnh","Điện Biên Phủ"};
    
    public static List<BuildingDTO> generateBuildings(int count) {
        Random rand = new Random();
        List<BuildingDTO> buildings = new ArrayList<>();
        
        for (int i = 0; i < count; i++) {
            BuildingDTO dto = new BuildingDTO();
            dto.setName("Tòa nhà " + generateName());
            dto.setDistrict(DISTRICTS[rand.nextInt(DISTRICTS.length)]);
            dto.setStreet(STREETS[rand.nextInt(STREETS.length)] + " " + (rand.nextInt(500) + 1));
            dto.setRentPrice(rand.nextInt(50000000) + 5000000); // 5tr - 55tr
            dto.setFloorArea(rand.nextInt(1000) + 50); // 50 - 1050 m2
            dto.setNumberOfBasement(rand.nextInt(5));
            dto.setDirection(new String[]{"Đông","Tây","Nam","Bắc","Đông Nam","Đông Bắc","Tây Nam","Tây Bắc"}[rand.nextInt(8)]);
            dto.setLevel(String.valueOf(rand.nextInt(30) + 1)); // 1-30 tầng
            dto.setManagerName(generateVietnameseName());
            dto.setManagerPhone("0" + (rand.nextInt(900000000) + 100000000));
            dto.setType(new String[]{rand.nextBoolean() ? "NOI_THAT" : "", rand.nextBoolean() ? "TANG_TRET" : "", rand.nextBoolean() ? "NGUYEN_CAN" : ""});
            
            buildings.add(dto);
        }
        return buildings;
    }
}
Bảng Transaction (10 triệu giao dịch)
Mỗi customer có trung bình 15 giao dịch, kéo dài 2 năm:


-- Sinh transaction theo pattern thời gian thực
INSERT INTO transaction (createddate, code, note, customerid)
SELECT 
    DATE_ADD('2024-01-01', INTERVAL FLOOR(RAND() * 730) DAY) as createddate,
    ELT(1 + FLOOR(RAND() * 2), 'Danh Sách CSKH', 'Dẫn Đi Xem') as code,
    ELT(1 + FLOOR(RAND() * 5), 
        'Gọi điện tư vấn', 'Xem nhà lần 1', 'Đặt cọc', 'Ký hợp đồng', 'Bàn giao nhà') as note,
    id as customerid
FROM customer
CROSS JOIN (SELECT 1 UNION SELECT 2 UNION SELECT 3) t
WHERE RAND() > 0.3;
Lịch sử giá — Tracking sự thay đổi giá (10k records)

CREATE TABLE building_price_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    building_id BIGINT NOT NULL,
    old_price INT,
    new_price INT NOT NULL,
    changed_by VARCHAR(255),
    changed_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    reason VARCHAR(500),
    FOREIGN KEY (building_id) REFERENCES building(id)
);

-- Sinh lịch sử thay đổi giá: mỗi building thay đổi giá 5-10 lần trong 2 năm
INSERT INTO building_price_history (building_id, old_price, new_price, changed_by, changed_at, reason)
SELECT 
    b.id,
    b.rentprice - FLOOR(RAND() * 2000000),
    b.rentprice,
    ELT(1 + FLOOR(RAND() * 5), 'admin', 'nhanvien1', 'nhanvien2', 'nhanvien3', 'nhanvien4'),
    DATE_ADD('2024-01-01', INTERVAL FLOOR(RAND() * 730) DAY),
    ELT(1 + FLOOR(RAND() * 4), 'Tăng giá theo thị trường', 'Giảm giá khuyến mãi', 'Cập nhật định kỳ', 'Theo yêu cầu chủ nhà')
FROM building b
CROSS JOIN (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5) t;
Bảng Review/Feedback — Đánh giá building (500k reviews)

CREATE TABLE building_reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    building_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    rating TINYINT CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (building_id) REFERENCES building(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
Log truy cập API (50 triệu logs)

// Mỗi request ghi log vào bảng api_logs
// Dùng AOP + async writing + Kafka queue
@Aspect
@Component
public class ApiLogAspect {
    
    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object logApiCall(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        
        // Gọi API
        Object result = joinPoint.proceed();
        
        long duration = System.currentTimeMillis() - start;
        
        // Ghi log bất đồng bộ (ko block response)
        kafkaTemplate.send("api-logs", new ApiLog(
            LocalDateTime.now(),
            joinPoint.getSignature().getName(),
            duration,
            RequestContextHolder.currentRequestAttributes().getSessionId()
        ));
        
        return result;
    }
}
🏗️ Data khủng cho thống kê (Tổng quan)
Bảng	Số records	Dung lượng ~
users	1,000,000	200MB
building	100,000	150MB
rentarea	500,000	100MB
customer	500,000	150MB
transaction	10,000,000	2GB
assignmentbuilding	5,000,000	500MB
building_price_history	1,000,000	200MB
building_reviews	500,000	100MB
api_logs	50,000,000	5GB
Tổng	~68 triệu	~8GB
Với data này bạn sẽ học được:

Indexing: chọn index thế nào cho 10 triệu transaction
Query optimization: EXPLAIN, covering index, query plan
Pagination: cursor-based (ko dùng OFFSET) cho 50 triệu logs
Partitioning: partition theo tháng cho api_logs
Sharding: chia 1 triệu user ra 4 DB
Full-text search: tìm kiếm trên 100k building
Time series: phân tích xu hướng giá theo lịch sử
Report: thống kê doanh thu 10 triệu giao dịch
🎯 Kết luận
Tính năng khó nhất đỉnh nhất cho PV:

Tier God: CDC (Debezium + Kafka) + CQRS + Event Sourcing + Saga Pattern + GraphQL API

→ Nói được cái này là Lead/Senior ngay

Muốn gây ấn tượng cực mạnh với PV:

Build cái mini-framework @QueryBuilder tự động generate SQL từ annotation
Cài Debezium tự động đồng bộ MySQL → Elasticsearch
Viết Chaos Experiment tự động test system resilience
Text-to-SQL dùng AI
Kèm data 8GB thật để chứng minh hệ thống chạy ngon
Bạn muốn bắt đầu từ cái nào? 😄